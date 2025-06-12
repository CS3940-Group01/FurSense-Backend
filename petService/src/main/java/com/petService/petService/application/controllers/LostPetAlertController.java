package com.petService.petService.application.controllers;

import com.petService.petService.dto.*;
import com.petService.petService.service.LostPetAlertService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lost-pet-alerts")
@RequiredArgsConstructor
public class LostPetAlertController {
    
    private static final Logger log = LoggerFactory.getLogger(LostPetAlertController.class);
    
    private final LostPetAlertService alertService;

    @PostMapping("/create")
    public ResponseEntity<LostPetAlertDto> createAlert(
            @RequestHeader("userId") String userIdHeader,
            @RequestBody CreateLostPetAlertRequest request) {
        try {
            Integer ownerId = Integer.parseInt(userIdHeader);
            log.info("Creating lost pet alert for pet {} by owner {}", request.getPetId(), ownerId);
            return alertService.createAlert(ownerId, request);
        } catch (NumberFormatException e) {
            log.error("Invalid userId in header: {}", userIdHeader, e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{alertId}")
    public ResponseEntity<LostPetAlertDto> updateAlert(
            @PathVariable Integer alertId,
            @RequestHeader("userId") String userIdHeader,
            @RequestBody UpdateLostPetAlertRequest request) {
        try {
            Integer ownerId = Integer.parseInt(userIdHeader);
            log.info("Updating alert {} by owner {}", alertId, ownerId);
            return alertService.updateAlert(alertId, ownerId, request);
        } catch (NumberFormatException e) {
            log.error("Invalid userId in header: {}", userIdHeader, e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{alertId}/mark-found")
    public ResponseEntity<LostPetAlertDto> markPetFound(
            @PathVariable Integer alertId,
            @RequestHeader("userId") String userIdHeader,
            @RequestBody MarkPetFoundRequest request) {
        try {
            Integer ownerId = Integer.parseInt(userIdHeader);
            log.info("Marking pet found for alert {} by owner {}", alertId, ownerId);
            return alertService.markPetFound(alertId, ownerId, request);
        } catch (NumberFormatException e) {
            log.error("Invalid userId in header: {}", userIdHeader, e);
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{alertId}/cancel")
    public ResponseEntity<String> cancelAlert(
            @PathVariable Integer alertId,
            @RequestHeader("userId") String userIdHeader) {
        try {
            Integer ownerId = Integer.parseInt(userIdHeader);
            log.info("Cancelling alert {} by owner {}", alertId, ownerId);
            return alertService.cancelAlert(alertId, ownerId);
        } catch (NumberFormatException e) {
            log.error("Invalid userId in header: {}", userIdHeader, e);
            return ResponseEntity.badRequest().body("Invalid userId format.");
        }
    }

    @GetMapping("/active")
    public ResponseEntity<List<LostPetAlertDto>> getActiveAlerts() {
        log.info("Fetching all active lost pet alerts");
        return alertService.getActiveAlerts();
    }

    @GetMapping("/my-alerts")
    public ResponseEntity<List<LostPetAlertDto>> getMyAlerts(
            @RequestHeader("userId") String userIdHeader) {
        try {
            Integer ownerId = Integer.parseInt(userIdHeader);
            log.info("Fetching alerts for owner {}", ownerId);
            return alertService.getAlertsByOwner(ownerId);
        } catch (NumberFormatException e) {
            log.error("Invalid userId in header: {}", userIdHeader, e);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<LostPetAlertDto>> searchAlertsByLocation(
            @RequestParam String location) {
        log.info("Searching alerts by location: {}", location);
        return alertService.searchAlertsByLocation(location);
    }

    @GetMapping("/recent")
    public ResponseEntity<List<LostPetAlertDto>> getRecentActiveAlerts() {
        log.info("Fetching recent active alerts (last 30 days)");
        return alertService.getRecentActiveAlerts();
    }
}
