package com.example.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StateCountResponse {
    private long totalStates;
    private long activeStates;
    private long inactiveStates;
}
