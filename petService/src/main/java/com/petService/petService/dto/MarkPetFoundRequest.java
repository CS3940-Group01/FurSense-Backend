package com.petService.petService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarkPetFoundRequest {
    private String foundLocation;
    private String foundBy;
}
