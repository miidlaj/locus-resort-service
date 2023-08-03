package com.midlaj.resort.repository;

import com.midlaj.resort.entity.ResortLocationDetails;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ResortLocationRepository extends JpaRepository<ResortLocationDetails, Long> {
    @Query("SELECT DISTINCT r.location FROM ResortLocationDetails r WHERE lower(r.location) LIKE %:query% ORDER BY r.location ASC")
    List<String> searchByQuery(@Param("query") String query, Pageable pageable);

}
