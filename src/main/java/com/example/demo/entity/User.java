package com.example.demo.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.example.demo.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
@JsonIgnoreProperties({ "entitySubscriptions"}) // Ignore lazy-loaded relationships
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;
    private String email;
    private String phone;
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne
    private Village village;

    private boolean isActive = true;

    // Entity subscriptions - users can subscribe to multiple entities
    @OneToMany(mappedBy = "user")
    private List<EntitySubscription> entitySubscriptions = new ArrayList<>();
}
