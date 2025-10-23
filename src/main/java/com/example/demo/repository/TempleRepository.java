package com.example.demo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.dto.response.TempleResponse;
import com.example.demo.entity.Temple;
import com.example.demo.entity.User;
import com.example.demo.entity.Village;
import com.example.demo.enums.TempleType;



public interface TempleRepository extends JpaRepository<Temple, UUID> {

    List<TempleResponse> findByVillage(Village village);

    List<TempleResponse> findByOwner(User owner);

    List<TempleResponse> findByType(TempleType type);

    List<Temple> findByVillageId(UUID villageId);

    List<Temple> findByOwnerId(UUID ownerId);

    @Query("""
            SELECT COUNT(t)
            FROM Temple t
            WHERE t.isActive = true
        """)
    Long countActiveTemples();

    @Query("""
            SELECT COUNT(t)
            FROM Temple t
            WHERE t.type = :type AND t.isActive = true
        """)
    Long countByTypeAndActive(TempleType type);

    // Response DTO queries for hierarchical filtering
    @Query("""
            SELECT t
            FROM Temple t
            JOIN t.village v
            JOIN t.owner u
            WHERE v.id = :villageId AND t.isActive = true
        """)
    List<Temple> findByVillageIdActive(UUID villageId);

    @Query("""
            SELECT t
            FROM Temple t
            JOIN t.village v
            JOIN t.owner u
            JOIN v.mandal m
            WHERE m.id = :mandalId AND t.isActive = true
        """)
    List<Temple> findByMandalIdActive(UUID mandalId);

    @Query("""
            SELECT t
            FROM Temple t
            JOIN t.village v
            JOIN t.owner u
            JOIN v.mandal m
            JOIN m.district d
            WHERE d.id = :districtId AND t.isActive = true
        """)
    List<Temple> findByDistrictIdActive(UUID districtId);

    @Query("""
            SELECT t
            FROM Temple t
            JOIN t.village v
            JOIN t.owner u
            JOIN v.mandal m
            JOIN m.district d
            JOIN d.state st
            WHERE st.id = :stateId AND t.isActive = true
        """)
    List<Temple> findByStateIdActive(UUID stateId);
}
