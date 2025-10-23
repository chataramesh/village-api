package com.example.demo.entity;

import com.example.demo.enums.TempleType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "temples")
@Data
@EqualsAndHashCode(callSuper = true)
public class Temple extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TempleType type;

    @ManyToOne
    @JoinColumn(name = "village_id", nullable = false)
    private Village village;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @Column
    private String address;

    @Column
    private String phone;

    @Column
    private String email;

    @Column
    private String priestName;

    @Column
    private String caretakerName;

    @Column
    private String deity;

    @Column
    private String establishedYear;

    @Column
    private String registrationNumber;

    @Column
    private String timings;

    @Column
    private String specialDays;
}
