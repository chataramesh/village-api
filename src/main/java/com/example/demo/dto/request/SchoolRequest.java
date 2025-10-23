package com.example.demo.dto.request;

import lombok.Data;

@Data
public class SchoolRequest {
    private String name;
    private String description;
    private String type; // SCHOOL, COLLEGE, UNIVERSITY, etc.
    private String villageId; // UUID as string
    private String ownerId; // UUID as string
    private String address;
    private String phone;
    private String email;
    private String website;
    private Integer studentCapacity;
    private String principalName;
    private String affiliation;
    private String registrationNumber;
}
