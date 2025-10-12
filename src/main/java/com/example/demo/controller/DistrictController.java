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

import com.example.demo.entity.District;
import com.example.demo.service.DistrictService;

@RestController
@RequestMapping("/api/district")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DistrictController {

    @Autowired
    private DistrictService districtService;

    @GetMapping("/all")
    public ResponseEntity<List<District>> getAllDistricts() {
        return ResponseEntity.ok(districtService.getAllDistricts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<District> getDistrictById(@PathVariable UUID id) {
        return ResponseEntity.ok(districtService.getDistrictById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<District> createDistrict(@RequestBody District district) {
        return ResponseEntity.ok(districtService.createDistrict(district));
    }

    @PutMapping("/{id}")
    public ResponseEntity<District> updateDistrict(@PathVariable UUID id, @RequestBody District district) {
        return ResponseEntity.ok(districtService.updateDistrict(id, district));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDistrict(@PathVariable UUID id) {
        districtService.deleteDistrict(id);
        return ResponseEntity.ok().build();
    }
}
