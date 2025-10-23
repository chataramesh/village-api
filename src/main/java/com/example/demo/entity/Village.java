
package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Village extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column
    private String code; // Village code for identification

    @Column(length = 1000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "mandal_id", nullable = false)
    private Mandal mandal;
}
