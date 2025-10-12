package com.example.demo.dto.response;

import java.util.UUID;

import lombok.Data;

@Data
public class VillageResponse {
    private UUID id;
    private String name;
    private String mandal;
    private String district;
    private String state;
    private String country;
}
