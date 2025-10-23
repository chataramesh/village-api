package com.example.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountryCountResponse {
    private long totalCountries;
    private long activeCountries;
    private long inactiveCountries;
}
