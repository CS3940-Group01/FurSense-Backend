package com.petService.petService.service;

import com.petService.petService.external.repository.VaccineRepository;
import com.petService.petService.external.repository.VaccineScheduleRepository;
import com.petService.petService.model.Vaccine;
import com.petService.petService.model.VaccineSchedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class VaccineService {

    private static final Logger log = LoggerFactory.getLogger(VaccineService.class);
    private final VaccineRepository vaccineRepository;
    private final VaccineScheduleRepository vaccineScheduleRepository;

    public VaccineService(VaccineRepository vaccineRepository, VaccineScheduleRepository vaccineScheduleRepository) {
        this.vaccineRepository = vaccineRepository;
        this.vaccineScheduleRepository = vaccineScheduleRepository;
    }

    public ResponseEntity<List<Vaccine>> getVaccinesBySpecies(String species) {
        List<Vaccine> vaccines = vaccineRepository.findBySpecies(species);
        return ResponseEntity.ok(vaccines);
    }


    public ResponseEntity<String> addVaccineSchedules(List<VaccineSchedule> schedules) {
        vaccineScheduleRepository.saveAll(schedules);
        return ResponseEntity.ok("Vaccine schedules added successfully");
    }
    
}
