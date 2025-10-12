package com.example.demo.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.request.VillageRequest;
import com.example.demo.dto.response.VillageCountResponse;
import com.example.demo.dto.response.VillageResponse;
import com.example.demo.entity.Mandal;
import com.example.demo.entity.Village;
import com.example.demo.repository.VillageRepository;

@Service
public class VillageService {

	@Autowired
	private VillageRepository villageRepo;

	public VillageResponse createVillage(VillageRequest request) {
		Village village = new Village();
		BeanUtils.copyProperties(request, village);
		villageRepo.save(village);

		VillageResponse response = new VillageResponse();
		BeanUtils.copyProperties(village, response);
		return response;
	}

	public List<VillageResponse> getVillagesByDistrict(String district) {
		return villageRepo.findByMandal(new Mandal()).stream().map(v -> {
			VillageResponse r = new VillageResponse();
			BeanUtils.copyProperties(v, r);
			return r;
		}).collect(Collectors.toList());
	}

	public VillageCountResponse getCount() {
		// TODO Auto-generated method stub
		return villageRepo.getVillageCount();
	}

	public Village save(Village village) {
		// TODO Auto-generated method stub
		return  villageRepo.save(village);
	}

	public Village getVillageById(UUID id) {
		// TODO Auto-generated method stub
		return villageRepo.findById(id).orElseThrow(() -> new RuntimeException("Village not found"));
	}
	
	public List<Village> getAllVillages() {
		// TODO Auto-generated method stub
		return villageRepo.findAll();	}

	
	public Village updateVillage(UUID id, Village villageDetails) {
		Village village = getVillageById(id);
		village.setName(villageDetails.getName());
		if (villageDetails.getMandal() != null) {
			village.setMandal(villageDetails.getMandal());
		}
		return villageRepo.save(village);
	}

	public void deleteVillage(UUID id) {
		Village village = getVillageById(id);
		villageRepo.delete(village);
	}

	public List<Village> getAllVillagesByMandal(UUID mandalId) {
		// TODO Auto-generated method stub
		return villageRepo.findByMandalId(mandalId);
	}
}
