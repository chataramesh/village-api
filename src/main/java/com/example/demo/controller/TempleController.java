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

import com.example.demo.dto.request.TempleRequest;
import com.example.demo.dto.response.TempleResponse;
import com.example.demo.enums.TempleType;
import com.example.demo.service.TempleService;

@RestController
@RequestMapping("/api/temples")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TempleController {

    @Autowired
    private TempleService templeService;

    /**
     * Create a new temple
     */
    @PostMapping("/create")
    public ResponseEntity<TempleResponse> createTemple(@RequestBody TempleRequest templeRequest) {
        TempleResponse createdTemple = templeService.createTemple(templeRequest);
        return ResponseEntity.ok(createdTemple);
    }

    /**
     * Update an existing temple
     */
    @PutMapping("/{templeId}")
    public ResponseEntity<TempleResponse> updateTemple(@PathVariable UUID templeId, @RequestBody TempleRequest templeRequest) {
        TempleResponse updatedTemple = templeService.updateTemple(templeId, templeRequest);
        return ResponseEntity.ok(updatedTemple);
    }

    /**
     * Get temple by ID
     */
    @GetMapping("/{templeId}")
    public ResponseEntity<TempleResponse> getTempleById(@PathVariable UUID templeId) {
        TempleResponse temple = templeService.getTempleById(templeId);
        return ResponseEntity.ok(temple);
    }

    /**
     * Get all temples
     */
    @GetMapping("/all")
    public ResponseEntity<List<TempleResponse>> getAllTemples() {
        List<TempleResponse> temples = templeService.getAllTemples();
        return ResponseEntity.ok(temples);
    }

    /**
     * Get temples by village
     */
    @GetMapping("/village/{villageId}")
    public ResponseEntity<List<TempleResponse>> getTemplesByVillage(@PathVariable UUID villageId) {
        List<TempleResponse> temples = templeService.getTemplesByVillage(villageId);
        return ResponseEntity.ok(temples);
    }

    /**
     * Get temples by mandal
     */
    @GetMapping("/mandal/{mandalId}")
    public ResponseEntity<List<TempleResponse>> getTemplesByMandal(@PathVariable UUID mandalId) {
        List<TempleResponse> temples = templeService.getTemplesByMandal(mandalId);
        return ResponseEntity.ok(temples);
    }

    /**
     * Get temples by district
     */
    @GetMapping("/district/{districtId}")
    public ResponseEntity<List<TempleResponse>> getTemplesByDistrict(@PathVariable UUID districtId) {
        List<TempleResponse> temples = templeService.getTemplesByDistrict(districtId);
        return ResponseEntity.ok(temples);
    }

    /**
     * Get temples by state
     */
    @GetMapping("/state/{stateId}")
    public ResponseEntity<List<TempleResponse>> getTemplesByState(@PathVariable UUID stateId) {
        List<TempleResponse> temples = templeService.getTemplesByState(stateId);
        return ResponseEntity.ok(temples);
    }

    /**
     * Get temples by owner
     */
    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<TempleResponse>> getTemplesByOwner(@PathVariable UUID ownerId) {
        List<TempleResponse> temples = templeService.getTemplesByOwner(ownerId);
        return ResponseEntity.ok(temples);
    }

    /**
     * Get temples by type
     */
//    @GetMapping("/type/{templeType}")
//    public ResponseEntity<List<TempleResponse>> getTemplesByType(@PathVariable TempleType templeType) {
//        List<TempleResponse> temples = templeService.getTemplesByType(templeType);
//        return ResponseEntity.ok(temples);
//    }

    /**
     * Get active temples count
     */
    @GetMapping("/count/active")
    public ResponseEntity<Long> getActiveTemplesCount() {
        Long count = templeService.getActiveTemplesCount();
        return ResponseEntity.ok(count);
    }

    /**
     * Get temples count by type
     */
    @GetMapping("/count/type/{templeType}")
    public ResponseEntity<Long> getTemplesCountByType(@PathVariable TempleType templeType) {
        Long count = templeService.getTemplesCountByType(templeType);
        return ResponseEntity.ok(count);
    }

    /**
     * Delete a temple (soft delete)
     */
    @DeleteMapping("/{templeId}")
    public ResponseEntity<String> deleteTemple(@PathVariable UUID templeId) {
        templeService.deleteTemple(templeId);
        return ResponseEntity.ok("Temple deleted successfully");
    }
}
