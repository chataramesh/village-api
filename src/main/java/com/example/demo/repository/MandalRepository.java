package com.example.demo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Mandal;

public interface MandalRepository  extends JpaRepository<Mandal, UUID> {

	List<Mandal> findByDistrictId(UUID districtId);

}
