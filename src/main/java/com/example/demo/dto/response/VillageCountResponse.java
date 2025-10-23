package com.example.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VillageCountResponse {
    private long totalVillages;
    private long activeVillages;
    private long inactiveVillages;

    public VillageCountResponse(Long totalCount, Long activeCount, Long inactiveCount) {
        this.totalVillages = totalCount;
        this.activeVillages = activeCount;
        this.inactiveVillages = inactiveCount;
    }
}
