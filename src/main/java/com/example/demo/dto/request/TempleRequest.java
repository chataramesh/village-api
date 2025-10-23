package com.example.demo.dto.request;

import lombok.Data;

@Data
public class TempleRequest {
    private String name;
    private String description;
    private String type; // HINDU, MUSLIM, CHRISTIAN, etc.
    private String villageId; // UUID as string
    private String ownerId; // UUID as string
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
}
