package com.petService.petService.external.repository;

import com.petService.petService.model.VaccineLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VaccineLogRepository extends JpaRepository<VaccineLog, String> {
}
