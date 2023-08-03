package com.midlaj.resort.dao;

import com.midlaj.resort.repository.ResortLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ResortLocationDAO {

    @Autowired
    private ResortLocationRepository locationRepository;

    public List<String> getLocationSuggestion(String query) {

        Pageable pageable = PageRequest.of(0, 5);
        return locationRepository.searchByQuery(query, pageable);
    }
}
