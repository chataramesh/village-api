package com.example.demo.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Vehicle;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, UUID> {

    // Find vehicles by owner
    List<Vehicle> findByOwnerId(UUID ownerId);

    // Find active vehicles by owner
    List<Vehicle> findByOwnerIdAndIsActiveTrue(UUID ownerId);

    // Find vehicles by village
    List<Vehicle> findByVillageId(UUID villageId);

    // Find active vehicles by village
    List<Vehicle> findByVillageIdAndIsActiveTrue(UUID villageId);

    // Find vehicles by type
    List<Vehicle> findByVehicleType(String vehicleType);

    // Find vehicles by registration number
    List<Vehicle> findByVehicleNumber(String vehicleNumber);

   
    // Check if vehicle number exists (for uniqueness)
    boolean existsByVehicleNumber(String vehicleNumber);

}
