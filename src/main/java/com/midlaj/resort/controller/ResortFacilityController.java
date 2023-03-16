package com.midlaj.resort.controller;

import com.midlaj.resort.dto.request.FacilityRequestDTO;
import com.midlaj.resort.entity.ResortFacility;
import com.midlaj.resort.repository.FacilityRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/resort/facility")
@Slf4j
public class ResortFacilityController {

    @Autowired
    private FacilityRepository facilityRepository;

    @GetMapping()
    public ResponseEntity<?> resortFacilityList() {
        log.info("Inside the resortFacilityList method");
        List<ResortFacility> facilityList = facilityRepository.findAll();

        return ResponseEntity.ok(facilityList);
    }

    @PostMapping()
    public ResponseEntity<?> addResortFacility(@Valid @RequestBody FacilityRequestDTO facilityRequestDTO) {
        log.info("Inside the addResortFacility method");
        Optional<ResortFacility> facility = facilityRepository.findByName(facilityRequestDTO.getName());

        if (facility.isPresent()){
            log.warn("Facility " + facilityRequestDTO.getName() + " already exist!");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Facility " + facilityRequestDTO.getName() + " already exist!");
        }

        ResortFacility resortFacility = ResortFacility.builder()
                .name(facilityRequestDTO.getName())
                .description(facilityRequestDTO.getDescription())
                .build();

        ResortFacility savedFacility = facilityRepository.save(resortFacility);
        return ResponseEntity.ok(savedFacility);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFacility(@PathVariable Long id) {
        log.info("Inside the deleteFacility method");

        Optional<ResortFacility> facilityOptional = facilityRepository.findById(id);
        if (facilityOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Cannot find Facility to delete");
        }
        facilityRepository.deleteById(id);
        return ResponseEntity.ok().body("Facility delete Successfully");
    }
}
