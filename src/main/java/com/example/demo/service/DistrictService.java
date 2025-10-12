package com.example.demo.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.District;
import com.example.demo.repository.DistrictRepository;

@Service
public class DistrictService {

    @Autowired
    private DistrictRepository districtRepository;

    public List<District> getAllDistricts() {
        return districtRepository.findAll();
    }

    public District getDistrictById(UUID id) {
        return districtRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("District not found with id: " + id));
    }

    public District createDistrict(District district) {
        return districtRepository.save(district);
    }

    public District updateDistrict(UUID id, District districtDetails) {
        District district = getDistrictById(id);
        district.setName(districtDetails.getName());
        if (districtDetails.getState() != null) {
            district.setState(districtDetails.getState());
        }
        return districtRepository.save(district);
    }

    public void deleteDistrict(UUID id) {
        District district = getDistrictById(id);
        districtRepository.delete(district);
    }
}
