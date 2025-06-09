package com.petService.petService.external.repository;

import com.petService.petService.model.Vaccine;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VaccineRepository extends JpaRepository<Vaccine, String> {
    List<Vaccine> findBySpecies(String species);
}
