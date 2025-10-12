package com.example.demo.service;

import com.example.demo.dto.response.EntityItemResponse;
import com.example.demo.entity.Entity_Item;

public class EntityItemMapper {

    public static EntityItemResponse toResponse(Entity_Item entity) {
        if (entity == null) return null;

        EntityItemResponse response = new EntityItemResponse();

        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setDescription(entity.getDescription());
        response.setType(entity.getType());
        response.setAddress(entity.getAddress());
        response.setContactNumber(entity.getContactNumber());
        response.setEmail(entity.getEmail());
        response.setCapacity(entity.getCapacity());
        response.setAvailableSlots(entity.getAvailableSlots());
        response.setOpeningTime(entity.getOpeningTime());
        response.setClosingTime(entity.getClosingTime());
        response.setStatus(entity.getStatus());
        response.setActive(entity.isActive());
        response.setCreatedAt(entity.getCreatedAt());
        response.setUpdatedAt(entity.getUpdatedAt());
        response.setLatitude(entity.getLatitude());
        response.setLongitude(entity.getLongitude());
        response.setSubscriptionCount(entity.getSubscriptionCount());

        // ✅ include full owner object (be careful with recursion)
        response.setOwner(entity.getOwner());

        return response;
    }
}
