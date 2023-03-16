package com.midlaj.resort.service;

import com.midlaj.resort.dao.ResortDAO;
import com.midlaj.resort.dto.request.ImageUploadDTO;
import com.midlaj.resort.dto.request.ResortLocationDetailsDTO;
import com.midlaj.resort.dto.request.ResortRequestDTO;
import com.midlaj.resort.dto.response.ResortDetailsDTO;
import com.midlaj.resort.dto.response.ResortListAllDTO;
import com.midlaj.resort.entity.*;
import com.midlaj.resort.repository.CategoryRepository;
import com.midlaj.resort.repository.FacilityRepository;
import com.midlaj.resort.repository.ResortRepository;
import com.midlaj.resort.util.AmazonS3Util;
import com.midlaj.resort.util.Constants;
import com.midlaj.resort.util.CustomMultipartFile;
import com.midlaj.resort.util.ImageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;
import java.util.*;

@Service
@Slf4j
@Transactional
public class ResortServiceImpl implements ResortService {

    @Autowired
    private ResortRepository resortRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private FacilityRepository facilityRepository;

    @Autowired
    private ResortDAO resortDAO;

    @Override
    public ResponseEntity<?> saveResort(ResortRequestDTO requestDTO) {
        log.info("Inside saveResort method of Resort Service");

        Resort resort = new Resort();

        //Resort Entity
        resort.setName(requestDTO.getResortName());
        resort.setDescription(requestDTO.getDescription());
        resort.setUserId(requestDTO.getUserId());
        resort.setEnabled(true);
        resort.setAdminApproved(false);
        resort.setBanned(false);

        // Resort Category
        Optional<ResortCategory> category = categoryRepository.findById(requestDTO.getCategoryId());
        if (category.isEmpty())
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Provided category is not valid");// Send Error
        else resort.setCategory(category.get());

        // Resort Facilities
        Set<ResortFacility> resortFacilities = getFacilitiesForResort(requestDTO.getFacilityIds());
        if (resortFacilities.isEmpty())
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Provided facility is not valid. Atleast one facility is required"); // Send error
        else resort.setFacilities(resortFacilities);

        // Resort Address
        ResortAddress resortAddress = new ResortAddress();
        resortAddress.setZipCode(requestDTO.getZipCode());
        resortAddress.setState(requestDTO.getState());
        resortAddress.setCountry(requestDTO.getCountry());
        resortAddress.setCity(requestDTO.getCity());
        resortAddress.setResort(resort);

        resort.setResortAddress(resortAddress);

        // Resort Status as FORM_COMPLETED
        resort.setResortCreateStatus(ResortCreateStatus.FORM_COMPLETED);
        resort.setCreatedTime(new Date());

        return ResponseEntity.ok(saveResortAndUpdateTime(resort));
    }

    private Set<ResortFacility> getFacilitiesForResort(Long[] facilityIds) {
        log.info("Inside getFacilitiesForResort method of Resort Service");

        Set<ResortFacility> resortFacilities = new HashSet<>();
        for (Long id : facilityIds) {
            Optional<ResortFacility> facility = facilityRepository.findById(id);
            if (facility.isPresent()) {
                ResortFacility facilityInDB = facility.get();
                resortFacilities.add(facilityInDB);
            }
        }

        return resortFacilities;
    }

    @Override
    public ResponseEntity<?> setDefaultImage(ImageUploadDTO imageUploadDTO) {
        log.info("Inside setDefaultImage method of Resort Service");

        Optional<Resort> resort = resortRepository.findById(imageUploadDTO.getResortId());

        if (resort.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot find resort with Id " + imageUploadDTO.getResortId());
        }

        MultipartFile defaultImageMultipart = imageUploadDTO.getImage();
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(defaultImageMultipart.getOriginalFilename()));
        String uuid = UUID.randomUUID().toString();
        fileName = uuid + "_" + fileName;
        String uploadDir = "resortImages/default/" + imageUploadDTO.getResortId();


        List<String> listObjectKeys = AmazonS3Util.listFolder(uploadDir + "/");
        for (String objectKey : listObjectKeys) {
            System.out.println(objectKey);
            AmazonS3Util.deleteFile(objectKey);
        }

        try {
            AmazonS3Util.uploadFile(uploadDir, fileName, defaultImageMultipart.getInputStream());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Cannot upload Image.");
        }

        String defaultImageLink = getImageLink(fileName, uploadDir);
        System.out.println(defaultImageLink);
        Resort resortInDb = resort.get();

        System.out.println("File Name is : " + fileName);
        resortInDb.setDefaultImage(fileName);
        resortInDb.setResortCreateStatus(ResortCreateStatus.IMAGES_ADDED);
        resortInDb.setUpdateTime(new Date());


        return ResponseEntity.ok().body(saveResortAndUpdateTime(resortInDb).getDefaultImage());
    }


    private String getImageLink(String fileName, String uploadDir) {
        String ImageLink = Constants.S3_BASE_URI + "/" + uploadDir + "/" + fileName;
        return ImageLink;
    }

    @Override
    public List<Resort> findAll() {
        log.info("Inside findAll method of Resort Service");

        return resortRepository.findAll();
    }

    @Override
    public Resort findById(Long id) {
        log.info("Inside findById method of Resort Service");

        return resortDAO.findResortById(id);
    }

    @Override
    public ResponseEntity<?> setExtraImage(ImageUploadDTO imageUploadDTO) {
        log.info("Inside setExtraImage method of Resort Service");

        Optional<Resort> resort = resortRepository.findById(imageUploadDTO.getResortId());

        if (resort.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot find resort with Id " + imageUploadDTO.getResortId());
        }

        MultipartFile extraImageMultipart = imageUploadDTO.getImage();
        String originalFileName = StringUtils.cleanPath(Objects.requireNonNull(extraImageMultipart.getOriginalFilename()));

        String uuid = UUID.randomUUID().toString();
        originalFileName = uuid + "_" + originalFileName;

        String uploadDir = "resortImages/extraImages/" + imageUploadDTO.getResortId();

        // Listing Images in a particular directory
//        List<String> listObjectKeys = AmazonS3Util.listFolder(uploadDir + "/");
//
//        for (String objectKey : listObjectKeys) {
//            System.out.println(objectKey);
//            AmazonS3Util.deleteFile(objectKey);
//        }

        //Compress the image for thumbnails
        CustomMultipartFile compressedMultipartFile;
        String compressedImageName = "compressed_" + originalFileName;
        try {

            String contentType = extraImageMultipart.getContentType();
            String[] parts = contentType.split("/");
            String formatName = parts[1];
            byte[] compressedFileByte = ImageUtils.compressImage(extraImageMultipart, formatName);
            compressedMultipartFile = new CustomMultipartFile(compressedFileByte, compressedImageName);
            System.out.println(compressedMultipartFile.getSize());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Cannot compress Provided Image.");
        }

        Resort resortInDb = resort.get();
        Set<ResortImages> resortImagesSet = resortInDb.getImages();
        if (resortImagesSet.size() >= 10) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("You have maximum extra Images.");
        }

        try {
            //Uploading original file
            AmazonS3Util.uploadFile(uploadDir, originalFileName, extraImageMultipart.getInputStream());

            //Uploading Compressed Image
            AmazonS3Util.uploadFile(uploadDir, compressedImageName, compressedMultipartFile.getInputStream());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Cannot upload Image.");
        } catch (S3Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Cannot upload Image into Cloud at this moment.");
        }


        //Links of uploaded Images
        String originalImageLink = getImageLink(originalFileName, uploadDir);
        String compressedImageLink = getImageLink(compressedImageName, uploadDir);

        System.out.println(originalImageLink);
        System.out.println(compressedImageLink);


        resortInDb.addExtraImage(originalFileName, compressedImageName);

        Resort savedResort = saveResortAndUpdateTime(resortInDb);
        return ResponseEntity.ok().body(savedResort.getImages());
    }

    @Override
    public ResponseEntity<?> addLocationDetails(ResortLocationDetailsDTO locationDetailsDTO) {

        Optional<Resort> resortOptional = resortRepository.findById(locationDetailsDTO.getResortId());

        if (resortOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot find Resort.");
        }

        Resort resort = resortOptional.get();

        // Creating new Location Details
        ResortLocationDetails locationDetails = new ResortLocationDetails();
        locationDetails.setLocation(locationDetailsDTO.getLocation());
        locationDetails.setLattitude(locationDetailsDTO.getLatitude());
        locationDetails.setLongitude(locationDetailsDTO.getLongitude());
        locationDetails.setResort(resort);

        resort.setLocationDetails(locationDetails);
        resort.setResortCreateStatus(ResortCreateStatus.REQUESTED_APPROVAL);

        Resort savedResort = saveResortAndUpdateTime(resort);
        return ResponseEntity.ok(savedResort.getLocationDetails());
    }


    synchronized private Resort saveResortAndUpdateTime(Resort resort){
        resort.setUpdateTime(new Date());

        return resortRepository.save(resort);
    }

    @Override
    public ResponseEntity<?> getResortListForAdmin() {
        log.info("Inside getResortList method of Resort Service");

        List<Resort> resorts = resortRepository.findAll();

        List<ResortListAllDTO> resortListDTO = buildResortDTO(resorts);

        return ResponseEntity.ok(resortListDTO);
    }

    @Override
    public ResponseEntity<?> getResortByUser(Long id) {
        log.info("Inside getResortByUser method of Resort Service");

        List<Resort> resorts = resortRepository.findByUserId(id);


        return ResponseEntity.ok().body(buildResortDTO(resorts));
    }

    @Override
    public ResponseEntity<?> getResortById(Long id) {
        Optional<Resort> optionalResort = resortRepository.findById(id);
        if (optionalResort.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Cannot find Resort.");
        }

        Resort resort = optionalResort.get();

        return ResponseEntity.ok(buildResortDetailsDTO(resort));
    }

    @Override
    public ResponseEntity<?> setResortBan(Long id) {
        Optional<Resort> optionalResort = resortRepository.findById(id);
        if (optionalResort.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Cannot find Resort.");
        }

        Resort resort = optionalResort.get();
        resortRepository.updateBanStatus(id, !resort.getBanned());

        return ResponseEntity.ok("Successfully changed ban status.");
    }

    @Override
    public ResponseEntity<?> approveResort(Long id) {
        Optional<Resort> optionalResort = resortRepository.findById(id);
        if (optionalResort.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Cannot find Resort.");
        }
        resortRepository.approveResort(id);

        return ResponseEntity.ok("Successfully approved Resort.");
    }

    private ResortDetailsDTO buildResortDetailsDTO(Resort resort) {

        ResortDetailsDTO resortDetailsDTO = ResortDetailsDTO.builder()
                .name(resort.getName())
                .description(resort.getDescription())
                .userId(resort.getUserId())
                .defaultImageLink(resort.getDefaultImageUrl())
                .resortCreateStatus(resort.getResortCreateStatus().toString())
                .enabled(resort.getEnabled())
                .category(resort.getCategory())
                .facilities(resort.getFacilities())
                .resortAddress(resort.getResortAddress())
                .createdTime(resort.getCreatedTime())
                .updatedTime(resort.getUpdateTime())
                .banned(resort.getBanned())
                .adminApproved(resort.getAdminApproved())
                .locationDetails(resort.getLocationDetails())
                .build();
        resortDetailsDTO.setDtoOfResortImages(resort.getImages());

        return resortDetailsDTO;
    }

    @Override
    public ResponseEntity<?> changeEnabledStatus(Long id) {
        Resort resort = resortDAO.findResortById(id);
        resortDAO.changeEnabledStatus(resort.getId(), !resort.getEnabled());
        return ResponseEntity.ok("Successfully changed the Enable status");
    }

    private List<ResortListAllDTO> buildResortDTO(List<Resort> resorts) {
        List<ResortListAllDTO> resortListDTO = new ArrayList<>();
        resorts.forEach(resort -> {
            ResortListAllDTO resortDTO = ResortListAllDTO.builder()
                    .resortId(resort.getId())
                    .resortName(resort.getName())
                    .defaultImageUrl(resort.getDefaultImageUrl())
                    .approved(resort.getAdminApproved())
                    .createdStatus(resort.getResortCreateStatus().toString())
                    .createdTime(resort.getCreatedTime())
                    .updatedTime(resort.getUpdateTime())
                    .userId(resort.getUserId())
                    .build();

            resortListDTO.add(resortDTO);
        });

        return resortListDTO;
    }
}
