package com.example.demo.entity;

import com.example.demo.enums.VehicleType;
import com.example.demo.enums.WheelerType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "vehicles")
@Data
@EqualsAndHashCode(callSuper = true)
public class Vehicle extends BaseEntity {

    @Column(nullable = false)
    private String vehicleNumber; // Registration number

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType; // CAR, BIKE, TRUCK, BUS, AUTO, etc.

    // Additional vehicle information
    @Column(length = 1000)
    private String vehicleDescription;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private WheelerType wheelerType;

    // Owner of the vehicle
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    // Vehicle village (where it's registered)
    @ManyToOne
    @JoinColumn(name = "village_id", nullable = false)
    private Village village;

    private Integer seatingCapacity;


    private String fuelType; // PETROL, DIESEL, ELECTRIC, CNG

    private Double engineCapacity; // in CC

    private String vehicleClass; // LMV, HMV, etc.

    @Column(length = 1000)
    private String description;
}
