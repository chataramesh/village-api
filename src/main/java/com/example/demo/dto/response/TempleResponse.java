package com.example.demo.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.demo.enums.TempleType;

import lombok.Data;

@Data
public class TempleResponse {
    private UUID id;
    private String name;
    private String description;
    private TempleType type;
    private String villageName;
    private String ownerName;
    private String address;
    private String phone;
    private String email;
    private String priestName;
    private String caretakerName;
    private String deity;
    private String establishedYear;
    private String registrationNumber;
    private String timings;
    private String specialDays;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
