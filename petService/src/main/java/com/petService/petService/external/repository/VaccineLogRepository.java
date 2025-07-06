package com.petService.petService.external.repository;

import com.petService.petService.model.VaccineLog;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VaccineLogRepository extends JpaRepository<VaccineLog, String> {
    List<VaccineLog> findByPetId(Integer petId);
}
