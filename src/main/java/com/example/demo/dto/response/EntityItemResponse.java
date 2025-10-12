package com.example.demo.dto.response;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

import com.example.demo.entity.User;
import com.example.demo.enums.EntityStatus;

import lombok.Data;

@Data
public class EntityItemResponse {
    private UUID id;
    private String name;
    private String description;
    private String type;
    private String address;
    private String contactNumber;
    private String email;
    private Integer capacity;
    private Integer availableSlots;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private EntityStatus status;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Double latitude;
    private Double longitude;

    private int subscriptionCount;

    // ✅ Added owner as full object
    private User owner;
}
