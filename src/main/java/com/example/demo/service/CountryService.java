package com.example.demo.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Country;
import com.example.demo.repository.CountryRepository;

@Service
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;

    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    public Country getCountryById(UUID id) {
        return countryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Country not found with id: " + id));
    }

    public Country createCountry(Country country) {
        return countryRepository.save(country);
    }

    public Country updateCountry(UUID id, Country countryDetails) {
        Country country = getCountryById(id);
        country.setName(countryDetails.getName());
        return countryRepository.save(country);
    }

    public void deleteCountry(UUID id) {
        Country country = getCountryById(id);
        countryRepository.delete(country);
    }
}
