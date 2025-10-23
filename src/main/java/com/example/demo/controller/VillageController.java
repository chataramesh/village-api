package com.example.demo.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.request.VillageRequest;
import com.example.demo.dto.response.VillageCountResponse;
import com.example.demo.dto.response.VillageResponse;
import com.example.demo.entity.Country;
import com.example.demo.entity.District;
import com.example.demo.entity.Mandal;
import com.example.demo.entity.State;
import com.example.demo.entity.Village;
import com.example.demo.repository.CountryRepository;
import com.example.demo.repository.DistrictRepository;
import com.example.demo.repository.MandalRepository;
import com.example.demo.repository.StateRepository;
import com.example.demo.service.VillageService;

@RestController
@RequestMapping("/api/village")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class VillageController {

	@Autowired
	private VillageService villageService;

	@Autowired
	private MandalRepository mandalRepository;

	@Autowired
	private DistrictRepository districtRepository;

	@Autowired
	private StateRepository stateRepository;

	@Autowired
	private CountryRepository countryRepository;

	@PostMapping("/villages")
	public ResponseEntity<VillageResponse> createVillage(@RequestBody VillageRequest request) {
		return ResponseEntity.ok(villageService.createVillage(request));
	}

	@GetMapping("/count")
	public ResponseEntity<VillageCountResponse> getViilageCount() {
		return ResponseEntity.ok(villageService.getCount());
	}

	@GetMapping("/mandals/all")
	public ResponseEntity<?> getAllMandals() {
		return ResponseEntity.ok(mandalRepository.findAll());
	}

	@PostMapping("/mandals")
	public ResponseEntity<?> mandalCreate(@RequestBody Mandal mandal) {
		return ResponseEntity.ok(mandalRepository.save(mandal));
	}

	@GetMapping("/mandals-by-district/{districtId}")
	public ResponseEntity<?> getAllMandalsByDistrict(@PathVariable UUID districtId) {
		return ResponseEntity.ok(mandalRepository.findByDistrictId(districtId));
	}

	@GetMapping("/states/all")
	public ResponseEntity<?> getAllStates() {
		return ResponseEntity.ok(stateRepository.findAll());
	}

	@PostMapping("/states")
	public ResponseEntity<?> Statecreate(@RequestBody State state) {
		return ResponseEntity.ok(stateRepository.save(state));
	}

	@GetMapping("/states-by-country/{countryId}")
	public ResponseEntity<?> getAllStatesByCountry(@PathVariable UUID countryId) {
		return ResponseEntity.ok(stateRepository.findByCountryId(countryId));
	}

	@GetMapping("/country/all")
	public ResponseEntity<?> getAllCountries() {
		return ResponseEntity.ok(countryRepository.findAll());
	}

	@PostMapping("/country")
	public ResponseEntity<?> Countrycreate(@RequestBody Country country) {
		System.out.println("Country Data: " + country.getName());

		return ResponseEntity.ok(countryRepository.save(country));
	}

	@GetMapping("/district/all")
	public ResponseEntity<?> getAllDistricts() {
		return ResponseEntity.ok(districtRepository.findAll());
	}

	@GetMapping("/district-by-state/{stateId}")
	public ResponseEntity<?> getAllDistrictsByState(@PathVariable UUID stateId) {
		return ResponseEntity.ok(districtRepository.findAll());
	}

	@PostMapping("/district")
	public ResponseEntity<?> Countrycreate(@RequestBody District district) {
		return ResponseEntity.ok(districtRepository.save(district));
	}

	@PostMapping("/create")
	public ResponseEntity<?> viilagereate(@RequestBody Village village) {
		return ResponseEntity.ok(villageService.save(village));
	}

	@GetMapping("/all")
	public ResponseEntity<List<Village>> getAllVillages() {
		return ResponseEntity.ok(villageService.getAllVillages());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Village> getVillageById(@PathVariable UUID id) {
		return ResponseEntity.ok(villageService.getVillageById(id));
	}
	
	 @PutMapping("/{id}")
	    public ResponseEntity<Village> updateVillage(@PathVariable UUID id, @RequestBody Village village) {
	        return ResponseEntity.ok(villageService.updateVillage(id, village));
	    }

	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> deleteVillage(@PathVariable UUID id) {
	    	villageService.deleteVillage(id);
	        return ResponseEntity.ok().build();
	    }
		@GetMapping("/villages-by-mandal/{mandalId}")
		public ResponseEntity<?> getAllVillagesByMandal(@PathVariable UUID mandalId) {
			return ResponseEntity.ok(villageService.getAllVillagesByMandal(mandalId));
		}
}
