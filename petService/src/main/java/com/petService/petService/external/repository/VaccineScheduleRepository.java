// package: com.petService.petService.external.repository
package com.petService.petService.external.repository;

import com.petService.petService.model.VaccineSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VaccineScheduleRepository extends JpaRepository<VaccineSchedule, Integer> {
}
