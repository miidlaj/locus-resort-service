package com.midlaj.resort.service;

import com.midlaj.resort.dto.request.ImageUploadDTO;
import com.midlaj.resort.dto.request.ResortLocationDetailsDTO;
import com.midlaj.resort.dto.request.ResortRequestDTO;
import com.midlaj.resort.entity.Resort;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ResortService {

    ResponseEntity<?> saveResort(ResortRequestDTO resortRequestDTO);

    ResponseEntity<?> setDefaultImage(ImageUploadDTO imageUploadDTO);

    List<Resort> findAll();

    Resort findById(Long id);

    ResponseEntity<?> setExtraImage(ImageUploadDTO extraImageDTO);

    ResponseEntity<?> addLocationDetails(ResortLocationDetailsDTO locationDetailsDTO);

    ResponseEntity<?> getResortListForAdmin();

    ResponseEntity<?> getResortByUser(Long id);

    ResponseEntity<?> getResortById(Long id);

    ResponseEntity<?> setResortBan(Long id);

    ResponseEntity<?> approveResort(Long id);

    ResponseEntity<?> changeEnabledStatus(Long id);
}
