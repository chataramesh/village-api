package com.example.demo.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.request.TempleRequest;
import com.example.demo.dto.response.TempleResponse;
import com.example.demo.entity.Temple;
import com.example.demo.entity.User;
import com.example.demo.entity.Village;
import com.example.demo.enums.TempleType;
import com.example.demo.repository.TempleRepository;

@Service
public class TempleService {

    @Autowired
    private TempleRepository templeRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private VillageService villageService;

    public TempleResponse createTemple(TempleRequest request) {
        Temple temple = new Temple();

        // Copy basic properties
        temple.setName(request.getName());
        temple.setDescription(request.getDescription());
        temple.setType(TempleType.valueOf(request.getType()));
        temple.setAddress(request.getAddress());
        temple.setPhone(request.getPhone());
        temple.setEmail(request.getEmail());
        temple.setPriestName(request.getPriestName());
        temple.setCaretakerName(request.getCaretakerName());
        temple.setDeity(request.getDeity());
        temple.setEstablishedYear(request.getEstablishedYear());
        temple.setRegistrationNumber(request.getRegistrationNumber());
        temple.setTimings(request.getTimings());
        temple.setSpecialDays(request.getSpecialDays());

        // Set relationships
        if (request.getVillageId() != null) {
            Village village = villageService.getVillageById(UUID.fromString(request.getVillageId()));
            temple.setVillage(village);
        }

        if (request.getOwnerId() != null) {
            User owner = userService.getUserById(UUID.fromString(request.getOwnerId()));
            temple.setOwner(owner);
        }

        Temple savedTemple = templeRepository.save(temple);

        return mapToResponse(savedTemple);
    }

    public List<TempleResponse> getAllTemples() {
        return templeRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public TempleResponse getTempleById(UUID id) {
        Temple temple = templeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Temple not found with id: " + id));
        return mapToResponse(temple);
    }

    
    public List<TempleResponse> getTemplesByOwner(UUID ownerId) {
        return templeRepository.findByOwnerId(ownerId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

  

    public TempleResponse updateTemple(UUID id, TempleRequest request) {
        Temple temple = templeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Temple not found with id: " + id));

        // Update basic properties
        temple.setName(request.getName());
        temple.setDescription(request.getDescription());
        temple.setType(TempleType.valueOf(request.getType()));
        temple.setAddress(request.getAddress());
        temple.setPhone(request.getPhone());
        temple.setEmail(request.getEmail());
        temple.setPriestName(request.getPriestName());
        temple.setCaretakerName(request.getCaretakerName());
        temple.setDeity(request.getDeity());
        temple.setEstablishedYear(request.getEstablishedYear());
        temple.setRegistrationNumber(request.getRegistrationNumber());
        temple.setTimings(request.getTimings());
        temple.setSpecialDays(request.getSpecialDays());

        // Update relationships if provided
        if (request.getVillageId() != null) {
            Village village = villageService.getVillageById(UUID.fromString(request.getVillageId()));
            temple.setVillage(village);
        }

        if (request.getOwnerId() != null) {
            User owner = userService.getUserById(UUID.fromString(request.getOwnerId()));
            temple.setOwner(owner);
        }

        Temple updatedTemple = templeRepository.save(temple);
        return mapToResponse(updatedTemple);
    }

    public void deleteTemple(UUID id) {
        Temple temple = templeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Temple not found with id: " + id));
        temple.setActive(false);
        templeRepository.save(temple);
    }

    public Long getActiveTemplesCount() {
        return templeRepository.countActiveTemples();
    }

    public Long getTemplesCountByType(TempleType type) {
        return templeRepository.countByTypeAndActive(type);
    }

    // Hierarchical filtering methods
    public List<TempleResponse> getTemplesByVillage(UUID villageId) {
        return templeRepository.findByVillageIdActive(villageId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<TempleResponse> getTemplesByMandal(UUID mandalId) {
        return templeRepository.findByMandalIdActive(mandalId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<TempleResponse> getTemplesByDistrict(UUID districtId) {
        return templeRepository.findByDistrictIdActive(districtId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<TempleResponse> getTemplesByState(UUID stateId) {
        return templeRepository.findByStateIdActive(stateId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private TempleResponse mapToResponse(Temple temple) {
        TempleResponse response = new TempleResponse();
        BeanUtils.copyProperties(temple, response);

        // Set additional fields
        if (temple.getVillage() != null) {
            response.setVillageName(temple.getVillage().getName());
        }
        if (temple.getOwner() != null) {
            response.setOwnerName(temple.getOwner().getName());
        }

        return response;
    }
}
