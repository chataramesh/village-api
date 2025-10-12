package com.example.demo.entity;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.example.demo.enums.EntityStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "entities")
@Data
@JsonIgnoreProperties({ "subscriptions", "hibernateLazyInitializer", "handler" })
public class Entity_Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private String type; // GOVT_HOSPITAL, SHOP, MRO_OFFICE, POLICE_STATION, POST_OFFICE, etc.

    // Owner of the entity (admin who manages it)
//    @ManyToOne(fetch = FetchType.LAZY)
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    // Operating hours
    @Column(nullable = false)
    private LocalTime openingTime;

    @Column(nullable = false)
    private LocalTime closingTime;

    // Current status - different from isActive (administrative)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EntityStatus status = EntityStatus.OPEN;

    // Administrative fields
    @Column(nullable = false)
    private boolean isActive = true;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt;

    // Location (optional)
    private Double latitude;
    private Double longitude;

    private String address;
    

    // Contact information
    private String contactNumber;
    private String email;

    // Capacity/Service related (only for certain entity types)
    private Integer capacity; // Total slots/capacity
    private Integer availableSlots;

    // Relationships
    @OneToMany(mappedBy = "entity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EntitySubscription> subscriptions = new ArrayList<>();

    // Helper methods
    public int getSubscriptionCount() {
        return subscriptions != null ? subscriptions.size() : 0;
    }

    public UUID getVillageId() {
        return owner != null && owner.getVillage() != null ? owner.getVillage().getId() : null;
    }

    public String getVillageName() {
        return owner != null && owner.getVillage() != null ? owner.getVillage().getName() : null;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
