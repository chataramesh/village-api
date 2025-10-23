package com.example.demo.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.demo.enums.SchoolType;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SchoolResponse {
    private UUID id;
    private String name;
    private String description;
    private SchoolType type;
    private String villageName;
    private String ownerName;
    private String address;
    private String phone;
    private String email;
    private String website;
    private Integer studentCapacity;
    private Integer currentStudents;
    private String principalName;
    private String affiliation;
    private String registrationNumber;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
