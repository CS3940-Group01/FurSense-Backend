package com.petService.petService.external.repository;

import com.petService.petService.model.Pet;
import com.petService.petService.model.PetStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Integer> {
    List<Pet> findByOwnerId(Integer ownerId);
    List<Pet> findByStatus(PetStatus status);
    List<Pet> findByOwnerIdAndStatus(Integer ownerId, PetStatus status);
}