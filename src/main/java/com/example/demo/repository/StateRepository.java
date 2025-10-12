package com.example.demo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.State;

public interface StateRepository extends JpaRepository<State, UUID> {

	List<State> findByCountryId(UUID countryId);

}
