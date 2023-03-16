package com.midlaj.resort.repository;

import com.midlaj.resort.entity.ResortFacility;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FacilityRepository extends JpaRepository<ResortFacility, Long> {
    Optional<ResortFacility> findByName(String name);
}
