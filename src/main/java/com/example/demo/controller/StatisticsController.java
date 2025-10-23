package com.example.demo.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.response.ComprehensiveCountResponse;
import com.example.demo.dto.response.MandalCountResponse;
import com.example.demo.dto.response.UserCountResponse;
import com.example.demo.dto.response.VillageCountResponse;
import com.example.demo.service.StatisticsService;

@RestController
@RequestMapping("/api/statistics")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class StatisticsController {

	@Autowired
	private StatisticsService statisticsService;

	/**
	 * Get comprehensive counts for all hierarchical entities Returns counts for
	 * Users, Villages, Mandals, Districts, States, Countries, and Entities
	 */
	@GetMapping("/comprehensive")
	public ResponseEntity<ComprehensiveCountResponse> getComprehensiveCounts() {
		ComprehensiveCountResponse response = statisticsService.getAllCounts();
		return ResponseEntity.ok(response);
	}

	/**
	 * Get counts specific to a village Returns counts for Users, Entities, and the
	 * specific Village
	 */
	@GetMapping("/village/{villageId}")
	public ResponseEntity<ComprehensiveCountResponse> getVillageCountsByVillageId(@PathVariable UUID villageId) {
		ComprehensiveCountResponse response = statisticsService.getVillageCountsByVillageId(villageId);
		return ResponseEntity.ok(response);
	}

	/**
	 * Get individual entity counts
	 */
	@GetMapping("/users")
	public ResponseEntity<UserCountResponse> getUserCounts() {
		UserCountResponse response = statisticsService.getUserCounts();
		return ResponseEntity.ok(response);
	}

	@GetMapping("/villages")
	public ResponseEntity<VillageCountResponse> getVillageCounts() {
		VillageCountResponse response = statisticsService.getVillageCounts();
		return ResponseEntity.ok(response);
	}

	@GetMapping("/mandals")
	public ResponseEntity<MandalCountResponse> getMandalCounts() {
		MandalCountResponse response = statisticsService.getMandalCounts();
		return ResponseEntity.ok(response);
	}
}
