package com.petService.petService.repository;

import com.petService.petService.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Integer> {
    List<Pet> findByOwnerId(Integer ownerId);
}