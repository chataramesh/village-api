package com.example.demo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.dto.response.VillageCountResponse;
import com.example.demo.dto.response.VillageResponse;
import com.example.demo.entity.Mandal;
import com.example.demo.entity.Village;

public interface VillageRepository extends JpaRepository<Village, UUID>{

	List<VillageResponse> findByMandal(Mandal mandal);

	@org.springframework.data.jpa.repository.Query("""
			SELECT new com.example.demo.dto.response.VillageCountResponse(
				COUNT(v),
				SUM(CASE WHEN v.isActive = true THEN 1 ELSE 0 END),
				SUM(CASE WHEN v.isActive = false THEN 1 ELSE 0 END)
			)
			FROM Village v
		""")
	VillageCountResponse getVillageCount();

	List<Village> findByMandalId(UUID mandalId);

}
