package com.midlaj.resort.repository;

import com.midlaj.resort.entity.ResortCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<ResortCategory, Long> {
    Optional<ResortCategory> findByName(String name);
}
