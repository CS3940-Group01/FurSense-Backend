package com.petService.petService.dto;

import com.petService.petService.model.AlertStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LostPetAlertDto {
    private Integer id;
    private Integer petId;
    private Integer ownerId;
    private String petName;
    private String petType;
    private String lastSeenLocation;
    private String description;
    private String contactInfo;
    private Double rewardAmount;
    private LocalDateTime alertCreated;
    private LocalDateTime alertUpdated;
    private AlertStatus status;
    private String foundLocation;
    private String foundBy;
    private LocalDateTime foundDate;
}
