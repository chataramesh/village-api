package com.example.demo.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.request.SchoolRequest;
import com.example.demo.dto.response.SchoolResponse;
import com.example.demo.entity.School;
import com.example.demo.entity.User;
import com.example.demo.entity.Village;
import com.example.demo.enums.SchoolType;
import com.example.demo.repository.SchoolRepository;

@Service
public class SchoolService {

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private VillageService villageService;

    public SchoolResponse createSchool(SchoolRequest request) {
        School school = new School();

        // Copy basic properties
        school.setName(request.getName());
        school.setDescription(request.getDescription());
        school.setType(SchoolType.valueOf(request.getType()));
        school.setAddress(request.getAddress());
        school.setPhone(request.getPhone());
        school.setEmail(request.getEmail());
        school.setWebsite(request.getWebsite());
        school.setStudentCapacity(request.getStudentCapacity());
        school.setPrincipalName(request.getPrincipalName());
        school.setAffiliation(request.getAffiliation());
        school.setRegistrationNumber(request.getRegistrationNumber());

        // Set relationships
        if (request.getVillageId() != null) {
            Village village = villageService.getVillageById(UUID.fromString(request.getVillageId()));
            school.setVillage(village);
        }

        if (request.getOwnerId() != null) {
            User owner = userService.getUserById(UUID.fromString(request.getOwnerId()));
            school.setOwner(owner);
        }

        School savedSchool = schoolRepository.save(school);

        return mapToResponse(savedSchool);
    }

    public List<SchoolResponse> getAllSchools() {
        return schoolRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public SchoolResponse getSchoolById(UUID id) {
        School school = schoolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("School not found with id: " + id));
        return mapToResponse(school);
    }

  

    public List<SchoolResponse> getSchoolsByOwner(UUID ownerId) {
        return schoolRepository.findByOwnerId(ownerId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

   

    public SchoolResponse updateSchool(UUID id, SchoolRequest request) {
        School school = schoolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("School not found with id: " + id));

        // Update basic properties
        school.setName(request.getName());
        school.setDescription(request.getDescription());
        school.setType(SchoolType.valueOf(request.getType()));
        school.setAddress(request.getAddress());
        school.setPhone(request.getPhone());
        school.setEmail(request.getEmail());
        school.setWebsite(request.getWebsite());
        school.setStudentCapacity(request.getStudentCapacity());
        school.setPrincipalName(request.getPrincipalName());
        school.setAffiliation(request.getAffiliation());
        school.setRegistrationNumber(request.getRegistrationNumber());

        // Update relationships if provided
        if (request.getVillageId() != null) {
            Village village = villageService.getVillageById(UUID.fromString(request.getVillageId()));
            school.setVillage(village);
        }

        if (request.getOwnerId() != null) {
            User owner = userService.getUserById(UUID.fromString(request.getOwnerId()));
            school.setOwner(owner);
        }

        School updatedSchool = schoolRepository.save(school);
        return mapToResponse(updatedSchool);
    }

    public void deleteSchool(UUID id) {
        School school = schoolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("School not found with id: " + id));
        school.setActive(false);
        schoolRepository.save(school);
    }

    public Long getActiveSchoolsCount() {
        return schoolRepository.countActiveSchools();
    }

    public Long getSchoolsCountByType(SchoolType type) {
        return schoolRepository.countByTypeAndActive(type);
    }

    // Hierarchical filtering methods
    public List<SchoolResponse> getSchoolsByVillage(UUID villageId) {
        return schoolRepository.findByVillageIdActive(villageId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<SchoolResponse> getSchoolsByMandal(UUID mandalId) {
        return schoolRepository.findByMandalIdActive(mandalId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<SchoolResponse> getSchoolsByDistrict(UUID districtId) {
        return schoolRepository.findByDistrictIdActive(districtId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<SchoolResponse> getSchoolsByState(UUID stateId) {
        return schoolRepository.findByStateIdActive(stateId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private SchoolResponse mapToResponse(School school) {
        SchoolResponse response = new SchoolResponse();
        BeanUtils.copyProperties(school, response);

        // Set additional fields
        if (school.getVillage() != null) {
            response.setVillageName(school.getVillage().getName());
        }
        if (school.getOwner() != null) {
            response.setOwnerName(school.getOwner().getName());
        }

        return response;
    }
}
