package com.example.demo.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EventCountResponse {
	private Long totalEvents;
	private Long activeEvents;
	private Long inactiveEvents;

	public EventCountResponse(Long totalEvents, Long activeEvents, Long inactiveEvents) {
		super();
		this.totalEvents = totalEvents;
		this.activeEvents = activeEvents;
		this.inactiveEvents = inactiveEvents;
	}

}
