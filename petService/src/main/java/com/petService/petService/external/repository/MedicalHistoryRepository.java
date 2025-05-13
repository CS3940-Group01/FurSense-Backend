package com.petService.petService.repository;

import com.petService.petService.model.MedicalHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicalHistoryRepository extends JpaRepository<MedicalHistory, Integer> {
    List<MedicalHistory> findByPetId(Integer petId);
}