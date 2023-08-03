package com.midlaj.resort.service;

import com.midlaj.resort.dao.ResortLocationDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResortLocationService {

    @Autowired
    private ResortLocationDAO resortLocationDAO;


    public ResponseEntity<?> getSuggestionList(String query) {


        List<String> suggestions =  resortLocationDAO.getLocationSuggestion(query.toLowerCase());

        return ResponseEntity.ok(suggestions);
    }
}
