package com.example.demo.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.State;
import com.example.demo.service.StateService;

@RestController
@RequestMapping("/api/state")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class StateController {

    @Autowired
    private StateService stateService;

    @GetMapping("/all")
    public ResponseEntity<List<State>> getAllStates() {
        return ResponseEntity.ok(stateService.getAllStates());
    }

    @GetMapping("/{id}")
    public ResponseEntity<State> getStateById(@PathVariable UUID id) {
        return ResponseEntity.ok(stateService.getStateById(id));
    }

    @GetMapping("/country/{countryId}")
    public ResponseEntity<List<State>> getStatesByCountryId(@PathVariable UUID countryId) {
        return ResponseEntity.ok(stateService.getStatesByCountryId(countryId));
    }

    @PostMapping("/create")
    public ResponseEntity<State> createState(@RequestBody State state) {
        return ResponseEntity.ok(stateService.createState(state));
    }

    @PutMapping("/{id}")
    public ResponseEntity<State> updateState(@PathVariable UUID id, @RequestBody State state) {
        return ResponseEntity.ok(stateService.updateState(id, state));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteState(@PathVariable UUID id) {
        stateService.deleteState(id);
        return ResponseEntity.ok().build();
    }
}
