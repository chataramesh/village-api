package com.example.demo.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Entity_Item;

public interface EntityItemRepository extends JpaRepository<Entity_Item, UUID> {

    List<Entity_Item> findByVillageId(UUID villageId);

    List<Entity_Item> findByType(String type);

    List<Entity_Item> findByVillageIdAndType(UUID villageId, String type);

    List<Entity_Item> findByIsActiveTrue();

    List<Entity_Item> findByVillageIdAndIsActiveTrue(UUID villageId);

    @Query("SELECT e FROM Entity_Item e WHERE e.villageId = :villageId AND e.isActive = true")
    List<Entity_Item> findActiveByVillageId(@Param("villageId") UUID villageId);

    @Query("SELECT e FROM Entity_Item e WHERE e.type = :type AND e.isActive = true")
    List<Entity_Item> findActiveByType(@Param("type") String type);

    Optional<Entity_Item> findByIdAndIsActiveTrue(UUID id);

    @Query("SELECT e FROM Entity_Item e WHERE e.latitude BETWEEN :lat1 AND :lat2 AND e.longitude BETWEEN :lng1 AND :lng2")
    List<Entity_Item> findWithinRadius(@Param("lat1") Double lat1, @Param("lat2") Double lat2,
                                      @Param("lng1") Double lng1, @Param("lng2") Double lng2);
}
