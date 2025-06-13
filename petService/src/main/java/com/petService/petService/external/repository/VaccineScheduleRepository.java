// package: com.petService.petService.external.repository
package com.petService.petService.external.repository;

import com.petService.petService.model.VaccineSchedule;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VaccineScheduleRepository extends JpaRepository<VaccineSchedule, Integer> {
     List<VaccineSchedule> getScheduleByPetId(Integer petId);
}
