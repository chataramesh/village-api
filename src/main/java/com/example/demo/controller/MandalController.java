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

import com.example.demo.entity.Mandal;
import com.example.demo.service.MandalService;

@RestController
@RequestMapping("/api/mandal")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MandalController {

    @Autowired
    private MandalService mandalService;

    @GetMapping("/all")
    public ResponseEntity<List<Mandal>> getAllMandals() {
        return ResponseEntity.ok(mandalService.getAllMandals());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mandal> getMandalById(@PathVariable UUID id) {
        return ResponseEntity.ok(mandalService.getMandalById(id));
    }

    @GetMapping("/district/{districtId}")
    public ResponseEntity<List<Mandal>> getMandalsByDistrictId(@PathVariable UUID districtId) {
        return ResponseEntity.ok(mandalService.getMandalsByDistrictId(districtId));
    }

    @PostMapping("/create")
    public ResponseEntity<Mandal> createMandal(@RequestBody Mandal mandal) {
        return ResponseEntity.ok(mandalService.createMandal(mandal));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mandal> updateMandal(@PathVariable UUID id, @RequestBody Mandal mandal) {
        return ResponseEntity.ok(mandalService.updateMandal(id, mandal));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMandal(@PathVariable UUID id) {
        mandalService.deleteMandal(id);
        return ResponseEntity.ok().build();
    }
}
