package com.example.demo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.dto.response.DistrictCountResponse;
import com.example.demo.entity.District;

public interface DistrictRepository extends JpaRepository<District, UUID> {

    List<District> findByStateId(UUID stateId);

    @Query("""
            SELECT new com.example.demo.dto.response.DistrictCountResponse(
                COUNT(d),
                SUM(CASE WHEN d.isActive = true THEN 1 ELSE 0 END),
                SUM(CASE WHEN d.isActive = false THEN 1 ELSE 0 END)
            )
            FROM District d
        """)
    DistrictCountResponse getDistrictCount();
}
