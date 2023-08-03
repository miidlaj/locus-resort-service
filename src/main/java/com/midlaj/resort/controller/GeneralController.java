package com.midlaj.resort.controller;

import com.midlaj.resort.dto.request.ResortSearchDTO;
import com.midlaj.resort.service.ResortLocationService;
import com.midlaj.resort.service.ResortService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resort")
@Slf4j
public class GeneralController {

    @Autowired
    private ResortLocationService resortLocationService;

    @Autowired
    private ResortService resortService;

    @GetMapping("/location")
    public ResponseEntity<?> getLocationSuggestions(@RequestParam(name = "query") String query) {
        log.info("Inside the getLocationSuggestions inside General Controller");

        return resortLocationService.getSuggestionList(query);
    }

    @PostMapping("/search")
    public ResponseEntity<?> getResortsWithSearchFilters(@RequestBody ResortSearchDTO resortSearchDTO) {
        log.info("Inside the getResortsWithSearchFilters inside General Controller");

        return resortService.getResortsWithSearchFilters(resortSearchDTO);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<?> getResortDetailsForUser(@PathVariable Long id) {
        log.info("Inside the getResortDetailsForUser inside General Controller");

        return resortService.getResortDetailsForUser(id);
    }

    @GetMapping("/userId/{resortId}")
    public ResponseEntity<?> getUserIdUsingResortId(@PathVariable Long resortId) {
        log.info("Inside the getUserIdUsingResortId inside General Controller");

        return resortService.getUserIdUsingResortId(resortId);
    }

}
