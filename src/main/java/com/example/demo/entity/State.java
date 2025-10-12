package com.example.demo.entity;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
public class State {

	 @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private UUID id;
	    private String name;

	    @ManyToOne
	    private Country country;
	    
	    private boolean isActive = true;
}
