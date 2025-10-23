package com.example.demo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.dto.response.MandalCountResponse;
import com.example.demo.entity.Mandal;

public interface MandalRepository extends JpaRepository<Mandal, UUID> {

    List<Mandal> findByDistrictId(UUID districtId);

    @Query("""
            SELECT new com.example.demo.dto.response.MandalCountResponse(
                COUNT(m),
                SUM(CASE WHEN m.isActive = true THEN 1 ELSE 0 END),
                SUM(CASE WHEN m.isActive = false THEN 1 ELSE 0 END)
            )
            FROM Mandal m
        """)
    MandalCountResponse getMandalCount();
}
