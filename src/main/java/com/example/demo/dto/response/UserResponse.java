package com.example.demo.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.demo.entity.User;
import com.example.demo.entity.Village;
import com.example.demo.enums.Role;

import lombok.Data;

@Data
public class UserResponse {
    private UUID id;
    private String name;
    private String email;
    private String phone;
    private Role role;
    private Village village;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private User createdBy; // Instead of full user object
    private User updatedBy; // Instead of full user object
}
