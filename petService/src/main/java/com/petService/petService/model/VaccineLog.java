package com.petService.petService.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Entity
@Table(name = "vaccinelog")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VaccineLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer petId;
    private String vaccineId;
    private Date administeredOn;
    private String vetName;
    
}
