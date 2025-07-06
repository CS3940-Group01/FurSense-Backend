package com.petService.petService.application.controllers;

import com.petService.petService.model.Pet;
import com.petService.petService.model.PetStatus;
import com.petService.petService.service.PetService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pet")

public class PetController {
    private static final Logger log = LoggerFactory.getLogger(PetController.class);
    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping("/getPetsByOwnerId")
    public ResponseEntity<List<Pet>> getPetsByOwnerId(@RequestHeader("userid") Integer userId) {
        return petService.getPetsByOwnerId(userId);
    }

    @PostMapping("/addPet")
    public ResponseEntity<Pet> addPet(@RequestHeader("userid") Integer userId, @RequestBody Pet pet) {
        return petService.addPet(pet,userId);
    }

    @GetMapping("/{petId}")
    public ResponseEntity<Pet> getPetById(@PathVariable Integer petId) {
        return petService.getPetById(petId);
    }

    @PutMapping("/{petId}")
    public ResponseEntity<Pet> updatePet(
            @PathVariable Integer petId,
            @RequestHeader("userId") String userIdHeader,
            @RequestBody Pet pet) {
        try {
            Integer ownerId = Integer.parseInt(userIdHeader);
            return petService.updatePet(petId, ownerId, pet);
        } catch (NumberFormatException e) {
            log.error("Invalid userId in header: {}", userIdHeader, e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{petId}/status")
    public ResponseEntity<Pet> updatePetStatus(
            @PathVariable Integer petId,
            @RequestHeader("userId") String userIdHeader,
            @RequestParam PetStatus status) {
        try {
            Integer ownerId = Integer.parseInt(userIdHeader);
            return petService.updatePetStatus(petId, ownerId, status);
        } catch (NumberFormatException e) {
            log.error("Invalid userId in header: {}", userIdHeader, e);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/lost")
    public ResponseEntity<List<Pet>> getLostPets() {
        return petService.getLostPets();
    }

    @GetMapping("/my-pets")
    public ResponseEntity<List<Pet>> getMyPets(@RequestHeader("userId") String userIdHeader) {
        try {
            Integer ownerId = Integer.parseInt(userIdHeader);
            return petService.getPetsByOwnerId(ownerId);
        } catch (NumberFormatException e) {
            log.error("Invalid userId in header: {}", userIdHeader, e);
            return ResponseEntity.badRequest().build();
        }
    }
}