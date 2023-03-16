package com.midlaj.resort.repository;

import com.midlaj.resort.entity.Resort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResortRepository extends JpaRepository<Resort, Long> {

    List<Resort> findAll();

    List<Resort> findByUserId(Long id);

    @Query("UPDATE Resort r SET r.isBanned = ?2 WHERE r.id = ?1")
    @Modifying
    void updateBanStatus(Long id, Boolean isBanned);

    @Query("UPDATE Resort r SET r.isAdminApproved = true WHERE r.id = ?1")
    @Modifying
    void approveResort(Long id);

    @Query("UPDATE Resort r SET r.enabled = ?2  WHERE r.id = ?1")
    @Modifying
    void changeEnabledStatus(Long id, Boolean banStatus);
}
