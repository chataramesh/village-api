package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.response.EntityItemResponse;
import com.example.demo.repository.EntityItemRepository;

@Service
public class EntityItemService {

	@Autowired
	 private EntityItemRepository entityItemRepository;

	    public List<EntityItemResponse> getAllEntities() {
	        return entityItemRepository.findAll()
	                .stream()
	                .map(EntityItemMapper::toResponse)
	                .collect(Collectors.toList());
	    }
}
