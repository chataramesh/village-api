package com.example.demo.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.dto.response.CountryCountResponse;
import com.example.demo.entity.Country;

public interface CountryRepository extends JpaRepository<Country, UUID> {

    @Query("""
            SELECT new com.example.demo.dto.response.CountryCountResponse(
                COUNT(c),
                SUM(CASE WHEN c.isActive = true THEN 1 ELSE 0 END),
                SUM(CASE WHEN c.isActive = false THEN 1 ELSE 0 END)
            )
            FROM Country c
        """)
    CountryCountResponse getCountryCount();
}
