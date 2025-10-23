package com.example.demo.entity;

import java.time.LocalDateTime;

import com.example.demo.enums.IncidentCategory;
import com.example.demo.enums.IncidentPriority;
import com.example.demo.enums.IncidentStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "incidents")
@Data
@EqualsAndHashCode(callSuper = true)
public class Incident extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @Column(length = 2000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IncidentCategory category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IncidentStatus status = IncidentStatus.OPEN;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IncidentPriority priority = IncidentPriority.MEDIUM;

    @Column(nullable = false)
    private String location; // Specific location (school name, hospital name, etc.)

    @Column
    private String locationType; // Type of location (SCHOOL, HOSPITAL, OFFICE, etc.)

    @Column
    private String reportedBy; // Who reported the incident

    @Column
    private String assignedTo; // Who is assigned to handle it

    @Column
    private String escalatedTo; // Higher authority (SARPANCH, MLA, MP, etc.)

    @Column
    private String escalationLevel; // Current escalation level

    @Column(length = 1000)
    private String resolution; // How it was resolved

    @Column
    private LocalDateTime resolvedAt; // When it was resolved

    @Column
    private LocalDateTime escalatedAt; // When it was escalated

    @Column
    private String contactInfo; // Contact details of reporter

    @Column
    private String urgencyReason; // Why this needs urgent attention

    @Column
    private boolean requiresFollowUp = false;
    
}
