package com.example.demo.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.demo.enums.VehicleType;
import com.example.demo.enums.WheelerType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleResponse {
    private UUID id;
    private String vehicleNumber;
    private String vehicleModel;
    private VehicleType vehicleType;
    private WheelerType wheelerType;
    private String vehicleMake;
    private String vehicleColor;
    private Integer manufacturingYear;
    private String chassisNumber;
    private String engineNumber;
    private UUID ownerId;
    private String ownerName;
    private UUID villageId;
    private String villageName;
    private LocalDateTime registrationDate;
    private LocalDateTime expiryDate;
    private String registrationAuthority;
    private boolean isActive;
    private LocalDateTime createdAt;
    private String description;
    private String fuelType;
    private Integer seatingCapacity;
    private Double engineCapacity;
    private String vehicleClass;
}
