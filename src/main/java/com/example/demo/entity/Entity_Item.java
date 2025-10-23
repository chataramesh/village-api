package com.example.demo.entity;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.example.demo.enums.EntityStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "entities")
@Data
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Entity_Item extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private String type; // GOVT_HOSPITAL, SHOP, MRO_OFFICE, POLICE_STATION, POST_OFFICE, etc.

    // Owner of the entity (admin who manages it)
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @ManyToOne
    private Village village;
    
    // Operating hours
    @Column(nullable = false)
    private LocalTime openingTime;

    @Column(nullable = false)
    private LocalTime closingTime;

    // Current status - different from isActive (administrative)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EntityStatus status = EntityStatus.OPEN;

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
    @JsonManagedReference(value = "entity-subscriptions")
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
}
