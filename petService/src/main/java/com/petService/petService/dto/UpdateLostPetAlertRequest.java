package com.petService.petService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateLostPetAlertRequest {
    private String lastSeenLocation;
    private String description;
    private String contactInfo;
    private Double rewardAmount;
}
