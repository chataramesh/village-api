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

import com.example.demo.dto.request.SchoolRequest;
import com.example.demo.dto.response.SchoolResponse;
import com.example.demo.enums.SchoolType;
import com.example.demo.service.SchoolService;

@RestController
@RequestMapping("/api/schools")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SchoolController {

    @Autowired
    private SchoolService schoolService;

    /**
     * Create a new school
     */
    @PostMapping("/create")
    public ResponseEntity<SchoolResponse> createSchool(@RequestBody SchoolRequest schoolRequest) {
        SchoolResponse createdSchool = schoolService.createSchool(schoolRequest);
        return ResponseEntity.ok(createdSchool);
    }

    /**
     * Update an existing school
     */
    @PutMapping("/{schoolId}")
    public ResponseEntity<SchoolResponse> updateSchool(@PathVariable UUID schoolId, @RequestBody SchoolRequest schoolRequest) {
        SchoolResponse updatedSchool = schoolService.updateSchool(schoolId, schoolRequest);
        return ResponseEntity.ok(updatedSchool);
    }

    /**
     * Get school by ID
     */
    @GetMapping("/{schoolId}")
    public ResponseEntity<SchoolResponse> getSchoolById(@PathVariable UUID schoolId) {
        SchoolResponse school = schoolService.getSchoolById(schoolId);
        return ResponseEntity.ok(school);
    }

    /**
     * Get all schools
     */
    @GetMapping("/all")
    public ResponseEntity<List<SchoolResponse>> getAllSchools() {
        List<SchoolResponse> schools = schoolService.getAllSchools();
        return ResponseEntity.ok(schools);
    }

    /**
     * Get schools by village
     */
    @GetMapping("/village/{villageId}")
    public ResponseEntity<List<SchoolResponse>> getSchoolsByVillage(@PathVariable UUID villageId) {
        List<SchoolResponse> schools = schoolService.getSchoolsByVillage(villageId);
        return ResponseEntity.ok(schools);
    }

    /**
     * Get schools by mandal
     */
    @GetMapping("/mandal/{mandalId}")
    public ResponseEntity<List<SchoolResponse>> getSchoolsByMandal(@PathVariable UUID mandalId) {
        List<SchoolResponse> schools = schoolService.getSchoolsByMandal(mandalId);
        return ResponseEntity.ok(schools);
    }

    /**
     * Get schools by district
     */
    @GetMapping("/district/{districtId}")
    public ResponseEntity<List<SchoolResponse>> getSchoolsByDistrict(@PathVariable UUID districtId) {
        List<SchoolResponse> schools = schoolService.getSchoolsByDistrict(districtId);
        return ResponseEntity.ok(schools);
    }

    /**
     * Get schools by state
     */
    @GetMapping("/state/{stateId}")
    public ResponseEntity<List<SchoolResponse>> getSchoolsByState(@PathVariable UUID stateId) {
        List<SchoolResponse> schools = schoolService.getSchoolsByState(stateId);
        return ResponseEntity.ok(schools);
    }

    /**
     * Get schools by owner
     */
    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<SchoolResponse>> getSchoolsByOwner(@PathVariable UUID ownerId) {
        List<SchoolResponse> schools = schoolService.getSchoolsByOwner(ownerId);
        return ResponseEntity.ok(schools);
    }

    /**
     * Get schools by type
     */
//    @GetMapping("/type/{schoolType}")
//    public ResponseEntity<List<SchoolResponse>> getSchoolsByType(@PathVariable SchoolType schoolType) {
//        List<SchoolResponse> schools = schoolService.getSchoolsByType(schoolType);
//        return ResponseEntity.ok(schools);
//    }

    /**
     * Get active schools count
     */
    @GetMapping("/count/active")
    public ResponseEntity<Long> getActiveSchoolsCount() {
        Long count = schoolService.getActiveSchoolsCount();
        return ResponseEntity.ok(count);
    }

    /**
     * Get schools count by type
     */
    @GetMapping("/count/type/{schoolType}")
    public ResponseEntity<Long> getSchoolsCountByType(@PathVariable SchoolType schoolType) {
        Long count = schoolService.getSchoolsCountByType(schoolType);
        return ResponseEntity.ok(count);
    }

    /**
     * Delete a school (soft delete)
     */
    @DeleteMapping("/{schoolId}")
    public ResponseEntity<String> deleteSchool(@PathVariable UUID schoolId) {
        schoolService.deleteSchool(schoolId);
        return ResponseEntity.ok("School deleted successfully");
    }
}
