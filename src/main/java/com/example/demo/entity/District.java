package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class District extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column
    private String code; // District code for identification

    @Column(length = 1000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "state_id", nullable = false)
    private State state;
}
