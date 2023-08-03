package com.midlaj.resort.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ResortStoredRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Long getUserIdByResortId(Long resortId) {
        String sql = "SELECT get_user_id_by_resort_id(?)";
        Long userId = jdbcTemplate.queryForObject(sql, Long.class, resortId);
        return userId;
    }


}
