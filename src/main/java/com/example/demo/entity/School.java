package com.example.demo.entity;

import com.example.demo.enums.SchoolType;

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
@Table(name = "schools")
@Data
@EqualsAndHashCode(callSuper = true)
public class School extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SchoolType type;

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
    private String website;

    @Column
    private Integer studentCapacity;

    @Column
    private Integer currentStudents;

    @Column
    private String principalName;

    @Column
    private String affiliation;

    @Column
    private String registrationNumber;
}
