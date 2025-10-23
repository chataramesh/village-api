package com.example.demo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.dto.response.EntityCountResponse;
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

	@Query("""
			    SELECT new com.example.demo.dto.response.EntityCountResponse(
			        COUNT(ei),
			        SUM(CASE WHEN ei.isActive = true THEN 1 ELSE 0 END),
			        SUM(CASE WHEN ei.isActive = false THEN 1 ELSE 0 END)
			    )
			    FROM Entity_Item ei where ei.village.id = :villageId
			""")
	EntityCountResponse getEntityCountByVillageId(UUID villageId);

	@Query("""
			    SELECT new com.example.demo.dto.response.EntityCountResponse(
			        COUNT(ei),
			        SUM(CASE WHEN ei.isActive = true THEN 1 ELSE 0 END),
			        SUM(CASE WHEN ei.isActive = false THEN 1 ELSE 0 END)
			    )
			    FROM Entity_Item ei
			""")
	EntityCountResponse getEntityCount();
}
