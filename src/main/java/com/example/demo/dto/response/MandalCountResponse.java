package com.example.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MandalCountResponse {
    private long totalMandals;
    private long activeMandals;
    private long inactiveMandals;
}
