package com.midlaj.resort.dao;

import com.midlaj.resort.entity.Resort;
import com.midlaj.resort.exception.EntityNotFoundException;
import com.midlaj.resort.repository.ResortRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Slf4j
public class ResortDOAImpl implements ResortDAO {

    @Autowired
    private ResortRepository resortRepository;

    @Override
    public Resort saveResort(Resort resort) {
        log.info("Inside saveResort method of ResortDOAImpl");
        return resortRepository.save(resort);
    }

    @Override
    public Resort findResortById(Long id) {
        log.info("Inside findResortById method of ResortDOAImpl");
        Optional<Resort> entity = resortRepository.findById(id);
        if (entity.isPresent()) {
            return entity.get();
        } else {
            throw new EntityNotFoundException("Resort");
        }

    }

    @Override
    public void changeEnabledStatus(Long id, Boolean enabledStatus) {
        log.info("Inside changeEnabledStatus method of ResortDOAImpl");
        resortRepository.changeEnabledStatus(id, enabledStatus);
    }
}
