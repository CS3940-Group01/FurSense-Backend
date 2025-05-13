package com.petService.petService.service;

import com.petService.petService.model.MedicalHistory;
import com.petService.petService.repository.MedicalHistoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MedicalHistoryService {
    private final MedicalHistoryRepository medicalHistoryRepository;
    public MedicalHistoryService(MedicalHistoryRepository medicalHistoryRepository) {
        this.medicalHistoryRepository = medicalHistoryRepository;
    }

    public ResponseEntity<List<MedicalHistory>> getMedicalHistoryByPetId(Integer petId) {
        List<MedicalHistory> medicalHistory = medicalHistoryRepository.findByPetId(petId);
        return ResponseEntity.ok(medicalHistory);
    }

    public ResponseEntity<MedicalHistory> addMedicalHistory(MedicalHistory medicalHistory) {
        MedicalHistory savedMedicalHistory = medicalHistoryRepository.save(medicalHistory);
        return ResponseEntity.ok(savedMedicalHistory);
    }
}