package com.example.demo.dto.response;

import lombok.Data;

@Data
public class VillageCountResponse {

	private Long totalVillages;
	private Long activeVillages;
	private Long inactiveVillages;
	
	 public VillageCountResponse(Long totalCount, Long activeCount, Long inactiveCount) {
	        this.totalVillages = totalCount;
	        this.activeVillages = activeCount;
	        this.inactiveVillages = inactiveCount;
	    }
}
