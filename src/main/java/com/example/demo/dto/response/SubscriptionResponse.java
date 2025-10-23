package com.example.demo.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionResponse {
    private UUID id;
    private UUID entityId;
    private String entityName;
    private String entityType;
    private String subscriptionType;
    private LocalDateTime subscribedAt;
    private boolean isActive;
}
