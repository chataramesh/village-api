package com.example.demo.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.User;
import com.example.demo.entity.Vehicle;
import com.example.demo.entity.Village;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.VehicleRepository;
import com.example.demo.repository.VillageRepository;

@Service
@Transactional
public class VehicleService {
	

	@Autowired
	private VehicleRepository vehicleRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private VillageRepository villageRepository;

	public Vehicle createVehicle(Vehicle vehicle) {
		// Validate owner exists and is active
		User owner = userRepository.findById(vehicle.getOwner().getId()).filter(User::isActive).orElseThrow(
				() -> new RuntimeException("Active owner not found with id: " + vehicle.getOwner().getId()));

		// Validate village exists and is active
		Village village = villageRepository.findById(vehicle.getVillage().getId()).filter(Village::isActive)
				.orElseThrow(() -> new RuntimeException(
						"Active village not found with id: " + vehicle.getVillage().getId()));

		// Check if vehicle number already exists
		if (vehicleRepository.existsByVehicleNumber(vehicle.getVehicleNumber())) {
			throw new RuntimeException("Vehicle with number " + vehicle.getVehicleNumber() + " already exists");
		}

		vehicle.setOwner(owner);
		vehicle.setVillage(village);

		return vehicleRepository.save(vehicle);
	}

	public Vehicle updateVehicle(UUID vehicleId, Vehicle vehicleDetails) {
		Vehicle vehicle = vehicleRepository.findById(vehicleId)
				.orElseThrow(() -> new RuntimeException("Vehicle not found with id: " + vehicleId));

		// Update fields if provided
		if (vehicleDetails.getVehicleNumber() != null) {
			// Check if new vehicle number conflicts with existing vehicles
			if (!vehicleDetails.getVehicleNumber().equals(vehicle.getVehicleNumber())
					&& vehicleRepository.existsByVehicleNumber(vehicleDetails.getVehicleNumber())) {
				throw new RuntimeException(
						"Vehicle with number " + vehicleDetails.getVehicleNumber() + " already exists");
			}
			vehicle.setVehicleNumber(vehicleDetails.getVehicleNumber());
		}

		if (vehicleDetails.getVehicleType() != null) {
			vehicle.setVehicleType(vehicleDetails.getVehicleType());
		}
		if (vehicleDetails.getWheelerType() != null) {
			vehicle.setWheelerType(vehicleDetails.getWheelerType());
		}

		if (vehicleDetails.getSeatingCapacity() != null) {
			vehicle.setSeatingCapacity(vehicleDetails.getSeatingCapacity());
		}

		vehicle.setActive(vehicleDetails.isActive());

		return vehicleRepository.save(vehicle);
	}

	public Vehicle getVehicleById(UUID vehicleId) {
		return vehicleRepository.findById(vehicleId)
				.orElseThrow(() -> new RuntimeException("Vehicle not found with id: " + vehicleId));
	}

	public List<Vehicle> getAllVehicles() {
		return vehicleRepository.findAll();
	}

	public List<Vehicle> getVehiclesByOwner(UUID ownerId) {
		return vehicleRepository.findByOwnerIdAndIsActiveTrue(ownerId);
	}

	public List<Vehicle> getVehiclesByVillage(UUID villageId) {
		return vehicleRepository.findByVillageIdAndIsActiveTrue(villageId);
	}

	public List<Vehicle> getVehiclesByType(String vehicleType) {
		return vehicleRepository.findByVehicleType(vehicleType);
	}

	public void deleteVehicle(UUID vehicleId) {
		Vehicle vehicle = vehicleRepository.findById(vehicleId)
				.orElseThrow(() -> new RuntimeException("Vehicle not found with id: " + vehicleId));

		vehicleRepository.delete(vehicle);
	}

	public List<Vehicle> getExpiredVehicles() {
		return null;
	}

}
