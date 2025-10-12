package com.example.demo.entity;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "entities")
@Data
public class Entity_Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String type; // GOVT_HOSPITAL, SHOP, MRO_OFFICE, POLICE_STATION, POST_OFFICE, etc.

    @Column(nullable = false)
    private UUID villageId;

    private String ownerName;
    private String contact;

    @Column(nullable = false)
    private boolean isOpen = true;

    private LocalTime openingTime;
    private LocalTime closingTime;

    private Double latitude;
    private Double longitude;

    private int bookedSlots = 0;
    private int availableSlots = 10; // Default capacity

    @Column(nullable = false)
    private boolean isActive = true;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "villageId", insertable = false, updatable = false)
    private Village village;

    @OneToMany(mappedBy = "entity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EntitySubscription> subscriptions = new ArrayList<>();

    // Helper method to get subscription count
    public int getSubscriptionCount() {
        return subscriptions != null ? subscriptions.size() : 0;
    }
}
