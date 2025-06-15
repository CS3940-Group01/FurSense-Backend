package com.petService.petService.service;

import com.petService.petService.external.repository.PetRepository;
import com.petService.petService.model.Pet;
import com.petService.petService.model.PetStatus;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetService {
    private static final Logger log = LoggerFactory.getLogger(PetService.class);
    private final PetRepository petRepository;
    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public ResponseEntity<List<Pet>> getPetsByOwnerId(Integer ownerId) {
        List<Pet> pets = petRepository.findByOwnerId(ownerId);
        return ResponseEntity.ok(pets);
    }

    public ResponseEntity<Pet> addPet(Pet pet) {
        log.atInfo()
                .log("Adding pet with name: {} and ownerId: {}", pet.getName(), pet.getOwnerId());
        Pet savedPet = petRepository.save(pet);
        return ResponseEntity.ok(savedPet);
    }

    public ResponseEntity<Pet> updatePetStatus(Integer petId, Integer ownerId, PetStatus status) {
        try {
            Optional<Pet> petOptional = petRepository.findById(petId);
            if (petOptional.isEmpty()) {
                log.warn("Pet with ID {} not found", petId);
                return ResponseEntity.notFound().build();
            }
            
            Pet pet = petOptional.get();
            if (!pet.getOwnerId().equals(ownerId)) {
                log.warn("Pet {} does not belong to owner {}", petId, ownerId);
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            
            pet.setStatus(status);
            Pet updatedPet = petRepository.save(pet);
            
            log.info("Updated pet {} status to {}", petId, status);
            return ResponseEntity.ok(updatedPet);
            
        } catch (Exception e) {
            log.error("Error updating pet status", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<List<Pet>> getLostPets() {
        try {
            List<Pet> lostPets = petRepository.findByStatus(PetStatus.LOST);
            return ResponseEntity.ok(lostPets);
        } catch (Exception e) {
            log.error("Error fetching lost pets", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    public ResponseEntity<Pet> getPetById(Integer petId) {
        Optional<Pet> petOptional = petRepository.findById(petId);
        if (petOptional.isPresent()) {
            return ResponseEntity.ok(petOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<Pet> updatePet(Integer petId, Integer ownerId, Pet updatedPet) {
        try {
            Optional<Pet> petOptional = petRepository.findById(petId);
            if (petOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            
            Pet pet = petOptional.get();
            if (!pet.getOwnerId().equals(ownerId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            
            // Update pet details
            if (updatedPet.getName() != null) {
                pet.setName(updatedPet.getName());
            }
            if (updatedPet.getType() != null) {
                pet.setType(updatedPet.getType());
            }
            if (updatedPet.getAge() != null) {
                pet.setAge(updatedPet.getAge());
            }
            
            Pet savedPet = petRepository.save(pet);
            return ResponseEntity.ok(savedPet);
            
        } catch (Exception e) {
            log.error("Error updating pet", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}