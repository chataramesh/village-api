package com.example.demo.dto.request;

import java.util.UUID;

import lombok.Data;

@Data
public class VillageAdminRequest {
    private String name;
    private String email;
    private String phone;
    private UUID villageId;
}