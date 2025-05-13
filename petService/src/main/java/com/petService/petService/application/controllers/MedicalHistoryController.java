package com.petService.petService.application.controllers;

import com.petService.petService.model.MedicalHistory;
import com.petService.petService.service.MedicalHistoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicalHistory")
public class MedicalHistoryController {
    private final MedicalHistoryService medicalHistoryService;
    public MedicalHistoryController(MedicalHistoryService medicalHistoryService) {
        this.medicalHistoryService = medicalHistoryService;
    }

    @GetMapping("/getMedicalHistoryByPetId")
    public ResponseEntity<List<MedicalHistory>> getMedicalHistoryByPetId(@RequestParam Integer petId) {
        return medicalHistoryService.getMedicalHistoryByPetId(petId);
    }

    @PostMapping("/addMedicalHistory")
    public ResponseEntity<MedicalHistory> addMedicalHistory(@RequestBody MedicalHistory medicalHistory) {
        return medicalHistoryService.addMedicalHistory(medicalHistory);
    }
}