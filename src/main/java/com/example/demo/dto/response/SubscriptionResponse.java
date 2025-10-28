package com.example.demo.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.demo.entity.Entity_Item;

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
	private Entity_Item entity;
	private String subscriptionType;
	private LocalDateTime subscribedAt;
	private boolean isActive;
}
