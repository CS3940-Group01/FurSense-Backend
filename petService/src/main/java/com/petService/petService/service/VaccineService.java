package com.petService.petService.service;

import com.petService.petService.external.repository.VaccineRepository;
import com.petService.petService.external.repository.VaccineScheduleRepository;

import com.petService.petService.external.repository.VaccineLogRepository;
import com.petService.petService.model.Pet;
import com.petService.petService.model.Vaccine;
import com.petService.petService.model.VaccineLog;
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
    private final VaccineLogRepository vaccineLogRepository;

    public VaccineService(VaccineRepository vaccineRepository, VaccineScheduleRepository vaccineScheduleRepository, VaccineLogRepository vaccineLogRepository) {
        this.vaccineRepository = vaccineRepository;
        this.vaccineScheduleRepository = vaccineScheduleRepository;
        this.vaccineLogRepository = vaccineLogRepository;
    }

    public ResponseEntity<List<Vaccine>> getVaccinesBySpecies(String species) {
        List<Vaccine> vaccines = vaccineRepository.findBySpecies(species);
        return ResponseEntity.ok(vaccines);
    }


    public ResponseEntity<String> addVaccineSchedules(List<VaccineSchedule> schedules) {
        vaccineScheduleRepository.saveAll(schedules);
        return ResponseEntity.ok("Vaccine schedules added successfully");
    }

    public ResponseEntity<String> updateVaccineLog(VaccineLog vaccineLog) {
        vaccineLogRepository.save(vaccineLog);
        return ResponseEntity.ok("Vaccine added successfully");
    }

    public ResponseEntity<List<VaccineLog>> getVaccineLogbyPetId(Integer petId) {
        List<VaccineLog> vaccineLogs = vaccineLogRepository.findByPetId(petId);
        return ResponseEntity.ok(vaccineLogs);
    }



    public ResponseEntity<List<VaccineSchedule>> getScheduleByPetId(Integer petId) {
        List<VaccineSchedule> vaccineSchedules = vaccineScheduleRepository.getScheduleByPetId(petId);
        return ResponseEntity.ok(vaccineSchedules);
    }
    
}
