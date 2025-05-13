package com.petService.petService.application.controllers;

import com.petService.petService.model.Pet;
import com.petService.petService.service.PetService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pet")

public class PetController {
    private final PetService petService;
    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping("/getPetsByOwnerId")
    public ResponseEntity<List<Pet>> getPetsByOwnerId(@RequestParam Integer ownerId) {
        return petService.getPetsByOwnerId(ownerId);
    }

    @PostMapping("/addPet")
    public ResponseEntity<Pet> addPet(@RequestBody Pet pet) {
        return petService.addPet(pet);
    }
}