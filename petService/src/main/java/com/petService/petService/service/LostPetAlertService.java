package com.petService.petService.service;

import com.petService.petService.dto.*;
import com.petService.petService.external.repository.LostPetAlertRepository;
import com.petService.petService.external.repository.PetRepository;
import com.petService.petService.model.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LostPetAlertService {
    
    private static final Logger log = LoggerFactory.getLogger(LostPetAlertService.class);
    
    private final LostPetAlertRepository alertRepository;
    private final PetRepository petRepository;
    private final NotificationService notificationService;

    @Transactional
    public ResponseEntity<LostPetAlertDto> createAlert(Integer ownerId, CreateLostPetAlertRequest request) {
        try {
            // Verify pet exists and belongs to the owner
            Optional<Pet> petOptional = petRepository.findById(request.getPetId());
            if (petOptional.isEmpty()) {
                log.warn("Pet with ID {} not found", request.getPetId());
                return ResponseEntity.badRequest().build();
            }
            
            Pet pet = petOptional.get();
            if (!pet.getOwnerId().equals(ownerId)) {
                log.warn("Pet {} does not belong to owner {}", request.getPetId(), ownerId);
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            
            // Check if there's already an active alert for this pet
            Optional<LostPetAlert> existingAlert = alertRepository.findByPetIdAndStatus(
                request.getPetId(), AlertStatus.ACTIVE);
            if (existingAlert.isPresent()) {
                log.warn("Active alert already exists for pet {}", request.getPetId());
                return ResponseEntity.badRequest().build();
            }
            
            // Create new alert
            LostPetAlert alert = new LostPetAlert();
            alert.setPetId(request.getPetId());
            alert.setOwnerId(ownerId);
            alert.setLastSeenLocation(request.getLastSeenLocation());
            alert.setDescription(request.getDescription());
            alert.setContactInfo(request.getContactInfo());
            alert.setRewardAmount(request.getRewardAmount());
            alert.setAlertCreated(LocalDateTime.now());
            alert.setAlertUpdated(LocalDateTime.now());
            alert.setStatus(AlertStatus.ACTIVE);
            
            LostPetAlert savedAlert = alertRepository.save(alert);
            
            // Update pet status to LOST
            pet.setStatus(PetStatus.LOST);
            petRepository.save(pet);
            
            // Send notification
            notificationService.sendLostPetAlert(savedAlert, pet);
            
            log.info("Created lost pet alert for pet {} by owner {}", request.getPetId(), ownerId);
            return ResponseEntity.ok(convertToDto(savedAlert, pet));
            
        } catch (Exception e) {
            log.error("Error creating lost pet alert", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @Transactional
    public ResponseEntity<LostPetAlertDto> updateAlert(Integer alertId, Integer ownerId, UpdateLostPetAlertRequest request) {
        try {
            Optional<LostPetAlert> alertOptional = alertRepository.findById(alertId);
            if (alertOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            
            LostPetAlert alert = alertOptional.get();
            if (!alert.getOwnerId().equals(ownerId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            
            if (alert.getStatus() != AlertStatus.ACTIVE) {
                log.warn("Cannot update non-active alert {}", alertId);
                return ResponseEntity.badRequest().build();
            }
            
            // Update alert details
            if (request.getLastSeenLocation() != null) {
                alert.setLastSeenLocation(request.getLastSeenLocation());
            }
            if (request.getDescription() != null) {
                alert.setDescription(request.getDescription());
            }
            if (request.getContactInfo() != null) {
                alert.setContactInfo(request.getContactInfo());
            }
            if (request.getRewardAmount() != null) {
                alert.setRewardAmount(request.getRewardAmount());
            }
            alert.setAlertUpdated(LocalDateTime.now());
            
            LostPetAlert updatedAlert = alertRepository.save(alert);
            
            // Get pet info for DTO
            Optional<Pet> petOptional = petRepository.findById(alert.getPetId());
            Pet pet = petOptional.orElse(null);
            
            log.info("Updated lost pet alert {} by owner {}", alertId, ownerId);
            return ResponseEntity.ok(convertToDto(updatedAlert, pet));
            
        } catch (Exception e) {
            log.error("Error updating lost pet alert", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @Transactional
    public ResponseEntity<LostPetAlertDto> markPetFound(Integer alertId, Integer ownerId, MarkPetFoundRequest request) {
        try {
            Optional<LostPetAlert> alertOptional = alertRepository.findById(alertId);
            if (alertOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            
            LostPetAlert alert = alertOptional.get();
            if (!alert.getOwnerId().equals(ownerId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            
            if (alert.getStatus() != AlertStatus.ACTIVE) {
                log.warn("Cannot mark non-active alert {} as found", alertId);
                return ResponseEntity.badRequest().build();
            }
            
            // Update alert status to resolved
            alert.setStatus(AlertStatus.RESOLVED);
            alert.setFoundLocation(request.getFoundLocation());
            alert.setFoundBy(request.getFoundBy());
            alert.setFoundDate(LocalDateTime.now());
            alert.setAlertUpdated(LocalDateTime.now());
            
            LostPetAlert updatedAlert = alertRepository.save(alert);
            
            // Update pet status to FOUND
            Optional<Pet> petOptional = petRepository.findById(alert.getPetId());
            if (petOptional.isPresent()) {
                Pet pet = petOptional.get();
                pet.setStatus(PetStatus.FOUND);
                petRepository.save(pet);
                
                // Send found notification
                notificationService.sendPetFoundNotification(updatedAlert, pet);
                
                log.info("Marked pet {} as found for alert {}", pet.getId(), alertId);
                return ResponseEntity.ok(convertToDto(updatedAlert, pet));
            }
            
            return ResponseEntity.ok(convertToDto(updatedAlert, null));
            
        } catch (Exception e) {
            log.error("Error marking pet as found", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<List<LostPetAlertDto>> getActiveAlerts() {
        try {
            List<LostPetAlert> alerts = alertRepository.findByStatus(AlertStatus.ACTIVE);
            List<LostPetAlertDto> alertDtos = alerts.stream()
                .map(alert -> {
                    Optional<Pet> pet = petRepository.findById(alert.getPetId());
                    return convertToDto(alert, pet.orElse(null));
                })
                .collect(Collectors.toList());
            
            return ResponseEntity.ok(alertDtos);
        } catch (Exception e) {
            log.error("Error fetching active alerts", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<List<LostPetAlertDto>> getAlertsByOwner(Integer ownerId) {
        try {
            List<LostPetAlert> alerts = alertRepository.findByOwnerId(ownerId);
            List<LostPetAlertDto> alertDtos = alerts.stream()
                .map(alert -> {
                    Optional<Pet> pet = petRepository.findById(alert.getPetId());
                    return convertToDto(alert, pet.orElse(null));
                })
                .collect(Collectors.toList());
            
            return ResponseEntity.ok(alertDtos);
        } catch (Exception e) {
            log.error("Error fetching alerts for owner {}", ownerId, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<List<LostPetAlertDto>> searchAlertsByLocation(String location) {
        try {
            List<LostPetAlert> alerts = alertRepository.findByLocationContainingIgnoreCase(location);
            List<LostPetAlertDto> alertDtos = alerts.stream()
                .filter(alert -> alert.getStatus() == AlertStatus.ACTIVE)
                .map(alert -> {
                    Optional<Pet> pet = petRepository.findById(alert.getPetId());
                    return convertToDto(alert, pet.orElse(null));
                })
                .collect(Collectors.toList());
            
            return ResponseEntity.ok(alertDtos);
        } catch (Exception e) {
            log.error("Error searching alerts by location {}", location, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<List<LostPetAlertDto>> getRecentActiveAlerts() {
        try {
            LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);
            List<LostPetAlert> alerts = alertRepository.findRecentActiveAlerts(AlertStatus.ACTIVE, thirtyDaysAgo);
            List<LostPetAlertDto> alertDtos = alerts.stream()
                .map(alert -> {
                    Optional<Pet> pet = petRepository.findById(alert.getPetId());
                    return convertToDto(alert, pet.orElse(null));
                })
                .collect(Collectors.toList());
            
            return ResponseEntity.ok(alertDtos);
        } catch (Exception e) {
            log.error("Error fetching recent active alerts", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @Transactional
    public ResponseEntity<String> cancelAlert(Integer alertId, Integer ownerId) {
        try {
            Optional<LostPetAlert> alertOptional = alertRepository.findById(alertId);
            if (alertOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            
            LostPetAlert alert = alertOptional.get();
            if (!alert.getOwnerId().equals(ownerId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            
            if (alert.getStatus() != AlertStatus.ACTIVE) {
                return ResponseEntity.badRequest().body("Alert is not active");
            }
            
            // Cancel the alert
            alert.setStatus(AlertStatus.CANCELLED);
            alert.setAlertUpdated(LocalDateTime.now());
            alertRepository.save(alert);
            
            // Update pet status back to SAFE
            Optional<Pet> petOptional = petRepository.findById(alert.getPetId());
            if (petOptional.isPresent()) {
                Pet pet = petOptional.get();
                pet.setStatus(PetStatus.SAFE);
                petRepository.save(pet);
            }
            
            log.info("Cancelled alert {} by owner {}", alertId, ownerId);
            return ResponseEntity.ok("Alert cancelled successfully");
            
        } catch (Exception e) {
            log.error("Error cancelling alert", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    private LostPetAlertDto convertToDto(LostPetAlert alert, Pet pet) {
        LostPetAlertDto dto = new LostPetAlertDto();
        dto.setId(alert.getId());
        dto.setPetId(alert.getPetId());
        dto.setOwnerId(alert.getOwnerId());
        dto.setLastSeenLocation(alert.getLastSeenLocation());
        dto.setDescription(alert.getDescription());
        dto.setContactInfo(alert.getContactInfo());
        dto.setRewardAmount(alert.getRewardAmount());
        dto.setAlertCreated(alert.getAlertCreated());
        dto.setAlertUpdated(alert.getAlertUpdated());
        dto.setStatus(alert.getStatus());
        dto.setFoundLocation(alert.getFoundLocation());
        dto.setFoundBy(alert.getFoundBy());
        dto.setFoundDate(alert.getFoundDate());
        
        if (pet != null) {
            dto.setPetName(pet.getName());
            dto.setPetType(pet.getType());
        }
        
        return dto;
    }
}
