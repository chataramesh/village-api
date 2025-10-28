package com.example.demo.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IncidentCountResponse {

	private Long totalIncidents;
	private Long activeIncidents;
	private Long inactiveIncidents;

	public IncidentCountResponse(Long totalIncidents, Long activeIncidents, Long inactiveIncidents) {
		super();
		this.totalIncidents = totalIncidents;
		this.activeIncidents = activeIncidents;
		this.inactiveIncidents = inactiveIncidents;
	}
}
