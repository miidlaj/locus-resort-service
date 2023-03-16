package com.midlaj.resort.controller;

import com.midlaj.resort.dto.request.ImageUploadDTO;
import com.midlaj.resort.dto.request.ResortLocationDetailsDTO;
import com.midlaj.resort.dto.request.ResortRequestDTO;
import com.midlaj.resort.entity.Resort;
import com.midlaj.resort.service.ResortServiceImpl;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/resort")
@Slf4j
@CrossOrigin(origins = "http://localhost:3000, http://localhost:4000")
public class ResortController {

    @Autowired
    private ResortServiceImpl resortService;

    // For Creating Resort
    @PostMapping()
    public ResponseEntity<?> saveResort(@Valid @RequestBody ResortRequestDTO resortRequestDTO) {
        log.info("Inside saveResort method of Resort controller");
        return resortService.saveResort(resortRequestDTO);
    }

    // For Listing Resorts
    @GetMapping()
    public List<Resort> getAllResort() {
        log.info("Inside getAllResort method of Resort controller");
        return resortService.findAll();
    }

    @GetMapping("/roomService/{id}")
    public ResponseEntity checkResortForRoom(@PathVariable Long id) {
        resortService.findById(id);
        return ResponseEntity.ok("Resort is available");
    }

    // For Adding Default Image
    @PostMapping(value = "/defaultImage",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> saveDefaultImage(@RequestParam("image") MultipartFile image, @RequestParam("resortId") Long resortId) {
        log.info("Inside saveDefaultImage method of Resort controller");
        ImageUploadDTO defaultImageDTO = ImageUploadDTO.builder()
                .image(image)
                .resortId(resortId)
                .build();
        if (defaultImageDTO.getImage().getSize() <= 0) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Provide Valid Image");
        }
        return resortService.setDefaultImage(defaultImageDTO);
    }

    // For Adding Extra Images
    @PostMapping(value = "/extraImage" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> saveExtraImage(@RequestParam("image") MultipartFile image, @RequestParam("resortId") Long resortId) {
        log.info("Inside saveExtraImage method of Resort controller");
        ImageUploadDTO extraImageDTO = ImageUploadDTO.builder()
                .image(image)
                .resortId(resortId)
                .build();
        System.out.println(extraImageDTO.getImage().getSize());
        if (extraImageDTO.getImage().getSize() <= 0) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Provide Valid Image");
        }
        return resortService.setExtraImage(extraImageDTO);
    }

    // For Adding Location Details
    @PostMapping("/locationDetails")
    public ResponseEntity<?> setLocationDetails(@Valid @RequestBody ResortLocationDetailsDTO locationDetailsDTO) {
        log.info("Inside setLocationDetails method of Resort controller");

        return resortService.addLocationDetails(locationDetailsDTO);
    }

    // For Adding Location Details
    @GetMapping("/listResorts")
    public ResponseEntity<?> getResortList() {
        log.info("Inside getResortList method of Resort controller");

        return resortService.getResortListForAdmin();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getResortById(@PathVariable(name = "id") Long id) {
        log.info("Inside getResortById method of Resort controller");

        return resortService.getResortById(id);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getResortByUser(@PathVariable(name = "id") Long id) {
        log.info("Inside getResortByUser method of Resort controller");

        return resortService.getResortByUser(id);
    }

    @PutMapping("/ban/{id}")
    public ResponseEntity<?> setResortBan(@PathVariable(name = "id") Long id) {
        log.info("Inside setResortBan method of Resort controller");

        return resortService.setResortBan(id);
    }

    @PutMapping("/approve/{id}")
    public ResponseEntity<?> approveResort(@PathVariable(name = "id") Long id) {
        log.info("Inside approveResort method of Resort controller");

        return resortService.approveResort(id);
    }

    @PutMapping("/enable/{id}")
    public ResponseEntity<?> changeEnableStatus(@PathVariable(name = "id") Long id) {
        log.info("Inside changeEnableStatus method of Resort controller");

        return resortService.changeEnabledStatus(id);
    }
}
