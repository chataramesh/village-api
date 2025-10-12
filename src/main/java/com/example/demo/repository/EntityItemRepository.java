package com.example.demo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Entity_Item;

public interface EntityItemRepository extends JpaRepository<Entity_Item, UUID> {

    List<Entity_Item> findByOwnerVillageId(UUID villageId);

    List<Entity_Item> findByType(String type);

    List<Entity_Item> findByOwnerVillageIdAndType(UUID villageId, String type);

    List<Entity_Item> findByIsActiveTrue();

    List<Entity_Item> findByOwnerVillageIdAndIsActiveTrue(UUID villageId);

    @Query("SELECT e FROM Entity_Item e WHERE e.owner.id = :ownerId")
    List<Entity_Item> findByOwnerId(@Param("ownerId") UUID ownerId);

    @Query("SELECT e FROM Entity_Item e WHERE e.status = :status AND e.isActive = true")
    List<Entity_Item> findByStatusAndActive(@Param("status") com.example.demo.enums.EntityStatus status);
}
