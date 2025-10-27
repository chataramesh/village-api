package com.example.demo.util;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.demo.dto.request.StateLevel;
import com.example.demo.entity.Country;
import com.example.demo.entity.District;
import com.example.demo.entity.Mandal;
import com.example.demo.entity.State;
import com.example.demo.entity.Village;
import com.example.demo.repository.CountryRepository;
import com.example.demo.repository.DistrictRepository;
import com.example.demo.repository.MandalRepository;
import com.example.demo.repository.StateRepository;
import com.example.demo.repository.VillageRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class LocationJsonUtil {

	// Inject path from application.properties
	@Value("${app.location.data.path:/country_data.json}")
	private String resourcePath;

	@Autowired
	private MandalRepository mandalRepository;;

	@Autowired
	private VillageRepository villageRepository;

	@Autowired
	private StateRepository stateRepository;

	@Autowired
	private DistrictRepository districtRepository;
	
	@Autowired
	private CountryRepository countryRepository;

	/**
	 * Loads location data from the configured JSON resource.
	 */
	public List<StateLevel> loadLocations() {
		System.out.println("Loading location data from: " + resourcePath);
		try {
			ObjectMapper mapper = new ObjectMapper();

			InputStream inputStream = getClass().getResourceAsStream(resourcePath);

			if (inputStream == null) {
				throw new RuntimeException("Resource not found in classpath: " + resourcePath);
			}

			return mapper.readValue(inputStream, new TypeReference<List<StateLevel>>() {
			});
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to load location data from " + resourcePath, e);
		}
	}

	/**
	 * Utility to print all villages (for testing or logging)
	 */
	public void printAllVillages() {
		List<StateLevel> states = loadLocations();
		Country country=countryRepository.findById(UUID.fromString("dd32ccb5-0131-43bc-b8a8-c4d7a809cbb5")).get();
		for (StateLevel state : states) {
			State stateEntity = new State();
			String name = state.getState();
			stateEntity.setName(name);
			stateEntity.setDescription(name);
			stateEntity.setCountry(country);
			stateEntity = stateRepository.save(stateEntity);
			for (StateLevel.DistrictLevel district : state.getDistricts()) {
				District districtEntity = new District();
				name = district.getDistrict();
				districtEntity.setName(name);
				districtEntity.setDescription(name);
				districtEntity.setState(stateEntity);
				districtEntity = districtRepository.save(districtEntity);
				for (StateLevel.SubDistrictLevel sub : district.getSubDistricts()) {
					Mandal mandalEntity = new Mandal();
					name = sub.getSubDistrict();
					mandalEntity.setName(name);
					mandalEntity.setDescription(name);
					mandalEntity.setDistrict(districtEntity);
					mandalEntity = mandalRepository.save(mandalEntity);
					for (String villageName : sub.getVillages()) {
						Village villageEntity = new Village();
						villageEntity.setName(villageName);
						villageEntity.setDescription(villageName);
						villageEntity.setMandal(mandalEntity);
						villageRepository.save(villageEntity);
					}
					
//					System.out.println("State: " + state.getState() + " | District: " + district.getDistrict()
//							+ " | SubDistrict: " + sub.getSubDistrict() + " | Villages: " + sub.getVillages());
				}
			}
		}
	}
}
