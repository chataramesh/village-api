package com.example.demo.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Mandal;
import com.example.demo.repository.MandalRepository;

@Service
public class MandalService {

    @Autowired
    private MandalRepository mandalRepository;

    public List<Mandal> getAllMandals() {
        return mandalRepository.findAll();
    }

    public Mandal getMandalById(UUID id) {
        return mandalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mandal not found with id: " + id));
    }

    public List<Mandal> getMandalsByDistrictId(UUID districtId) {
        return mandalRepository.findByDistrictId(districtId);
    }

    public Mandal createMandal(Mandal mandal) {
        return mandalRepository.save(mandal);
    }

    public Mandal updateMandal(UUID id, Mandal mandalDetails) {
        Mandal mandal = getMandalById(id);
        mandal.setName(mandalDetails.getName());
        if (mandalDetails.getDistrict() != null) {
            mandal.setDistrict(mandalDetails.getDistrict());
        }
        return mandalRepository.save(mandal);
    }

    public void deleteMandal(UUID id) {
        Mandal mandal = getMandalById(id);
        mandalRepository.delete(mandal);
    }
}
