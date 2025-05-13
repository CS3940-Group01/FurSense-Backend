package com.petService.petService.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "medical_history")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MedicalHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer petId; // Links to the Pet entity

    private LocalDate visitDate;

    private String diagnosis;

    private String treatment;

    private String vetName;
}