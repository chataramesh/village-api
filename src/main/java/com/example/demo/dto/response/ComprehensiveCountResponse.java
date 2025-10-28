package com.example.demo.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ComprehensiveCountResponse {
	private UserCountResponse userCounts;
	private VillageCountResponse villageCounts;
	private MandalCountResponse mandalCounts;
	private DistrictCountResponse districtCounts;
	private StateCountResponse stateCounts;
	private CountryCountResponse countryCounts;
	private EntityCountResponse entityCounts;
	private EventCountResponse eventCounts;
	private IncidentCountResponse incidentCounts;

	public ComprehensiveCountResponse(UserCountResponse userCounts, VillageCountResponse villageCounts,
			MandalCountResponse mandalCounts, DistrictCountResponse districtCounts, StateCountResponse stateCounts,
			CountryCountResponse countryCounts, EntityCountResponse entityCounts, EventCountResponse eventCounts,
			IncidentCountResponse incidentCounts) {
		super();
		this.userCounts = userCounts;
		this.villageCounts = villageCounts;
		this.mandalCounts = mandalCounts;
		this.districtCounts = districtCounts;
		this.stateCounts = stateCounts;
		this.countryCounts = countryCounts;
		this.entityCounts = entityCounts;
		this.eventCounts = eventCounts;
		this.incidentCounts = incidentCounts;
	}

}
