package com.example.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCountResponse {
	
	private long totalVillageAdmins;
	private long activeVillageAdmins;
	private long inactiveVillageAdmins;
	
	private long totalVillagers;
	private long activeVillagers;
	private long inactiveVillagers;
	
}
