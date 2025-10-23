package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Country extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column
    private String code; // Country code for identification

    @Column(length = 1000)
    private String description;
}
