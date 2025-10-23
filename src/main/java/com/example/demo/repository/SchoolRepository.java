package com.example.demo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.dto.response.SchoolResponse;
import com.example.demo.entity.School;
import com.example.demo.entity.User;
import com.example.demo.entity.Village;
import com.example.demo.enums.SchoolType;



public interface SchoolRepository extends JpaRepository<School, UUID> {

    List<SchoolResponse> findByVillage(Village village);

    List<SchoolResponse> findByOwner(User owner);

    List<SchoolResponse> findByType(SchoolType type);

    List<School> findByVillageId(UUID villageId);

    List<School> findByOwnerId(UUID ownerId);

    @Query("""
            SELECT COUNT(s)
            FROM School s
            WHERE s.isActive = true
        """)
    Long countActiveSchools();

    @Query("""
            SELECT COUNT(s)
            FROM School s
            WHERE s.type = :type AND s.isActive = true
        """)
    Long countByTypeAndActive(SchoolType type);   

    // Response DTO queries for hierarchical filtering
    @Query("""
            SELECT s
            FROM School s
            JOIN s.village v
            JOIN s.owner u
            WHERE v.id = :villageId AND s.isActive = true
        """)
    List<School> findByVillageIdActive(UUID villageId);

    @Query("""
            SELECT s
            FROM School s
            JOIN s.village v
            JOIN s.owner u
            JOIN v.mandal m
            WHERE m.id = :mandalId AND s.isActive = true
        """)
    List<School> findByMandalIdActive(UUID mandalId);

    @Query("""
            SELECT s
            FROM School s
            JOIN s.village v
            JOIN s.owner u
            JOIN v.mandal m
            JOIN m.district d
            WHERE d.id = :districtId AND s.isActive = true
        """)
    List<School> findByDistrictIdActive(UUID districtId);

    @Query("""
            SELECT s
            FROM School s
            JOIN s.village v
            JOIN s.owner u
            JOIN v.mandal m
            JOIN m.district d
            JOIN d.state st
            WHERE st.id = :stateId AND s.isActive = true
        """)
    List<School> findByStateIdActive(UUID stateId);
}
