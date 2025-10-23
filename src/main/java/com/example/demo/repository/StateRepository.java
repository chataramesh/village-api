package com.example.demo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.dto.response.StateCountResponse;
import com.example.demo.entity.State;

public interface StateRepository extends JpaRepository<State, UUID> {

    List<State> findByCountryId(UUID countryId);

    @Query("""
            SELECT new com.example.demo.dto.response.StateCountResponse(
                COUNT(s),
                SUM(CASE WHEN s.isActive = true THEN 1 ELSE 0 END),
                SUM(CASE WHEN s.isActive = false THEN 1 ELSE 0 END)
            )
            FROM State s
        """)
    StateCountResponse getStateCount();
}
