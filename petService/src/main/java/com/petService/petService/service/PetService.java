package com.petService.petService.service;

import com.petService.petService.external.repository.PetRepository;
import com.petService.petService.model.Pet;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


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
}