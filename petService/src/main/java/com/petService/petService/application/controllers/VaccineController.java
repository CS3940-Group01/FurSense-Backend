package com.petService.petService.application.controllers;

import com.petService.petService.model.Vaccine;
import com.petService.petService.model.VaccineSchedule;
import com.petService.petService.model.VaccineLog;
import com.petService.petService.service.VaccineService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vaccine")
public class VaccineController {

    private final VaccineService vaccineService;

    public VaccineController(VaccineService vaccineService) {
        this.vaccineService = vaccineService;
    }

    @GetMapping("/getVaccinesBySpecies")
    public ResponseEntity<List<Vaccine>> getVaccinesBySpecies(@RequestParam String species) {
        return vaccineService.getVaccinesBySpecies(species);
    }

     @PostMapping("/addSchedules")
    public ResponseEntity<String> addVaccineSchedules(@RequestBody List<VaccineSchedule> schedules) {
        return vaccineService.addVaccineSchedules(schedules);
    }

    @GetMapping("/schedule/{petId}")
    public ResponseEntity<List<VaccineSchedule>> getScheduleByPetId(@PathVariable Integer petId) {
        return vaccineService.getScheduleByPetId(petId);
    }

    @PostMapping("/updateVaccineLog")
    public ResponseEntity<String> addVaccineLog(@RequestBody VaccineLog vaccineLog) {
        return vaccineService.updateVaccineLog(vaccineLog);
    }

    @GetMapping("/getVaccineLog/{petId}")
    public ResponseEntity<List<VaccineLog>> getVaccineLogbyPetId(@PathVariable Integer petId) {
        return vaccineService.getVaccineLogbyPetId(petId);
    }
    
}
