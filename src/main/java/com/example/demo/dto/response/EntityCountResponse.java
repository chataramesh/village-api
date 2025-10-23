package com.example.demo.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EntityCountResponse {
	private Long totalEntities;
	private Long activeEntities;
	private Long inactiveEntities;

	public EntityCountResponse(Long totalEntities, Long activeEntities, Long inactiveEntities) {
		super();
		this.totalEntities = totalEntities;
		this.activeEntities = activeEntities;
		this.inactiveEntities = inactiveEntities;
	}

}
