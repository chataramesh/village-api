package com.example.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DistrictCountResponse {
    private long totalDistricts;
    private long activeDistricts;
    private long inactiveDistricts;
}
