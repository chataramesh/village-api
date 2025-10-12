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

import com.example.demo.entity.Country;
import com.example.demo.service.CountryService;

@RestController
@RequestMapping("/api/country")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CountryController {

    @Autowired
    private CountryService countryService;

    @GetMapping("/all")
    public ResponseEntity<List<Country>> getAllCountries() {
        return ResponseEntity.ok(countryService.getAllCountries());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Country> getCountryById(@PathVariable UUID id) {
        return ResponseEntity.ok(countryService.getCountryById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<Country> createCountry(@RequestBody Country country) {
        return ResponseEntity.ok(countryService.createCountry(country));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Country> updateCountry(@PathVariable UUID id, @RequestBody Country country) {
        return ResponseEntity.ok(countryService.updateCountry(id, country));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCountry(@PathVariable UUID id) {
        countryService.deleteCountry(id);
        return ResponseEntity.ok().build();
    }
}
