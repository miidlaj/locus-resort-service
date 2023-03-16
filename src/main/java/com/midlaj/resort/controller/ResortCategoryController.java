package com.midlaj.resort.controller;

import com.midlaj.resort.dto.request.CategoryRequestDTO;
import com.midlaj.resort.entity.ResortCategory;
import com.midlaj.resort.entity.ResortFacility;
import com.midlaj.resort.repository.CategoryRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/resort/category")
@Slf4j
public class ResortCategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping()
    public ResponseEntity<?> resortCategoryList() {
        log.info("Inside the resortCategoryList method");
        List<ResortCategory> categoryList = categoryRepository.findAll();

        return ResponseEntity.ok(categoryList);
    }

    @PostMapping()
    public ResponseEntity<?> addResortCategory(@Valid @RequestBody CategoryRequestDTO categoryRequestDTO) {
        log.info("Inside the addResortCategory method");
        Optional<ResortCategory> category = categoryRepository.findByName(categoryRequestDTO.getName());

        if (category.isPresent()){
            log.warn("Category name " + categoryRequestDTO.getName() + " already exist!");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Category name " + categoryRequestDTO.getName() + " already exist!");
        }

        ResortCategory resortCategory = ResortCategory.builder()
                .name(categoryRequestDTO.getName())
                .description(categoryRequestDTO.getDescription())
                .build();

        ResortCategory savedCategory = categoryRepository.save(resortCategory);
        return ResponseEntity.ok(savedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        log.info("Inside the deleteCategory method");

        Optional<ResortCategory> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Cannot find Category to delete");
        }
        categoryRepository.deleteById(id);
        return ResponseEntity.ok().body("Category delete Successfully");
    }
}
