package com.example.demo.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.response.ComprehensiveCountResponse;
import com.example.demo.dto.response.CountryCountResponse;
import com.example.demo.dto.response.DistrictCountResponse;
import com.example.demo.dto.response.EntityCountResponse;
import com.example.demo.dto.response.MandalCountResponse;
import com.example.demo.dto.response.StateCountResponse;
import com.example.demo.dto.response.UserCountResponse;
import com.example.demo.dto.response.VillageCountResponse;
import com.example.demo.repository.CountryRepository;
import com.example.demo.repository.DistrictRepository;
import com.example.demo.repository.EntityItemRepository;
import com.example.demo.repository.MandalRepository;
import com.example.demo.repository.StateRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.VillageRepository;

@Service
public class StatisticsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private VillageRepository villageRepository;

	@Autowired
	private MandalRepository mandalRepository;

	@Autowired
	private DistrictRepository districtRepository;

	@Autowired
	private StateRepository stateRepository;

	@Autowired
	private CountryRepository countryRepository;

	  @Autowired
	    private EntityItemRepository entityRepository;
	  
	/**
	 * Get comprehensive counts for all hierarchical entities
	 */
	public ComprehensiveCountResponse getAllCounts() {
		UserCountResponse userCounts = userRepository.getUserCount();
		VillageCountResponse villageCounts = villageRepository.getVillageCount();
		MandalCountResponse mandalCounts = mandalRepository.getMandalCount();
		DistrictCountResponse districtCounts = districtRepository.getDistrictCount();
		StateCountResponse stateCounts = stateRepository.getStateCount();
		CountryCountResponse countryCounts = countryRepository.getCountryCount();
		EntityCountResponse entityCounts = entityRepository.getEntityCount();
		return new ComprehensiveCountResponse(userCounts, villageCounts, mandalCounts, districtCounts, stateCounts,
				countryCounts,entityCounts);
	}

	/**
	 * Get individual counts (for specific use cases)
	 */
	public UserCountResponse getUserCounts() {
		return userRepository.getUserCount();
	}

	public VillageCountResponse getVillageCounts() {
		return villageRepository.getVillageCount();
	}

	public MandalCountResponse getMandalCounts() {
		return mandalRepository.getMandalCount();
	}

	public DistrictCountResponse getDistrictCounts() {
		return districtRepository.getDistrictCount();
	}

	public StateCountResponse getStateCounts() {
		return stateRepository.getStateCount();
	}

	public CountryCountResponse getCountryCounts() {
		return countryRepository.getCountryCount();
	}

	public ComprehensiveCountResponse getVillageCountsByVillageId(UUID villageId) {
		// TODO Auto-generated method stub
		VillageCountResponse villageCounts = villageRepository.getVillageCount(villageId);
		UserCountResponse userCounts = userRepository.getUserCount();
		EntityCountResponse entityCounts = entityRepository.getEntityCountByVillageId(villageId);
		return new ComprehensiveCountResponse(userCounts, villageCounts, null, null, null, null,entityCounts);
	}
}
