package com.example.demo.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "entity_notifications")
@Data
@JsonIgnoreProperties({"entity", "recipients"}) // Ignore lazy-loaded relationships
public class EntityNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entity_id", nullable = false)
    private Entity_Item entity;

    @Column(nullable = false, length = 1000)
    private String title;

    @Column(nullable = false, length = 2000)
    private String message;

    @Column(nullable = false)
    private String notificationType; // EMERGENCY, UPDATE, GENERAL, MAINTENANCE

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private boolean isActive = true;

    private String priority = "NORMAL"; // LOW, NORMAL, HIGH, URGENT

    private LocalDateTime scheduledFor;

    // Many-to-many relationship with users who received this notification
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "notification_recipients",
        joinColumns = @JoinColumn(name = "notification_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> recipients = new HashSet<>();
}
