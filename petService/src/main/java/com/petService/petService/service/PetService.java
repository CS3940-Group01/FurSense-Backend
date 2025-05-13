package com.petService.petService.service;

import com.petService.petService.model.Pet;
import com.petService.petService.repository.PetRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PetService {
    private final PetRepository petRepository;
    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public ResponseEntity<List<Pet>> getPetsByOwnerId(Integer ownerId) {
        List<Pet> pets = petRepository.findByOwnerId(ownerId);
        return ResponseEntity.ok(pets);
    }

    public ResponseEntity<Pet> addPet(Pet pet) {
        Pet savedPet = petRepository.save(pet);
        return ResponseEntity.ok(savedPet);
    }
}