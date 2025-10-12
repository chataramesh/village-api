package com.example.demo.dto.request;

import lombok.Data;

@Data
public class VillageRequest {
    private String name;
    private String mandal;
    private String district;
    private String state;
    private String country;
    private Double latitude;
    private Double longitude;
}

