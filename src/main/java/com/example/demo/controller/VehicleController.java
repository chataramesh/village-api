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

import com.example.demo.entity.Vehicle;
import com.example.demo.service.VehicleService;

@RestController
@RequestMapping("/api/vehicles")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    /**
     * Create a new vehicle registration
     */
    @PostMapping("/create")
    public ResponseEntity<Vehicle> createVehicle(@RequestBody Vehicle vehicle) {
        Vehicle createdVehicle = vehicleService.createVehicle(vehicle);
        return ResponseEntity.ok(createdVehicle);
    }

    /**
     * Update an existing vehicle
     */
    @PutMapping("/{vehicleId}")
    public ResponseEntity<Vehicle> updateVehicle(@PathVariable UUID vehicleId, @RequestBody Vehicle vehicleDetails) {
        Vehicle updatedVehicle = vehicleService.updateVehicle(vehicleId, vehicleDetails);
        return ResponseEntity.ok(updatedVehicle);
    }

    /**
     * Get vehicle by ID
     */
    @GetMapping("/{vehicleId}")
    public ResponseEntity<Vehicle> getVehicleById(@PathVariable UUID vehicleId) {
        Vehicle vehicle = vehicleService.getVehicleById(vehicleId);
        return ResponseEntity.ok(vehicle);
    }

    /**
     * Get all vehicles
     */
    @GetMapping("/all")
    public ResponseEntity<List<Vehicle>> getAllVehicles() {
        List<Vehicle> vehicles = vehicleService.getAllVehicles();
        return ResponseEntity.ok(vehicles);
    }

    /**
     * Get vehicles by owner
     */
    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<Vehicle>> getVehiclesByOwner(@PathVariable UUID ownerId) {
        List<Vehicle> vehicles = vehicleService.getVehiclesByOwner(ownerId);
        return ResponseEntity.ok(vehicles);
    }

    /**
     * Get vehicles by village
     */
    @GetMapping("/village/{villageId}")
    public ResponseEntity<List<Vehicle>> getVehiclesByVillage(@PathVariable UUID villageId) {
        List<Vehicle> vehicles = vehicleService.getVehiclesByVillage(villageId);
        return ResponseEntity.ok(vehicles);
    }

    /**
     * Get vehicles by type
     */
    @GetMapping("/type/{vehicleType}")
    public ResponseEntity<List<Vehicle>> getVehiclesByType(@PathVariable String vehicleType) {
        List<Vehicle> vehicles = vehicleService.getVehiclesByType(vehicleType);
        return ResponseEntity.ok(vehicles);
    }

    /**
     * Get expired vehicles
     */
    @GetMapping("/expired")
    public ResponseEntity<List<Vehicle>> getExpiredVehicles() {
        List<Vehicle> vehicles = vehicleService.getExpiredVehicles();
        return ResponseEntity.ok(vehicles);
    }


    

    /**
     * Delete a vehicle
     */
    @DeleteMapping("/{vehicleId}")
    public ResponseEntity<String> deleteVehicle(@PathVariable UUID vehicleId) {
        vehicleService.deleteVehicle(vehicleId);
        return ResponseEntity.ok("Vehicle deleted successfully");
    }
}
