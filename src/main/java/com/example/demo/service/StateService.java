package com.example.demo.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.State;
import com.example.demo.repository.StateRepository;

@Service
public class StateService {

    @Autowired
    private StateRepository stateRepository;

    public List<State> getAllStates() {
        return stateRepository.findAll();
    }

    public State getStateById(UUID id) {
        return stateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("State not found with id: " + id));
    }

    public List<State> getStatesByCountryId(UUID countryId) {
        return stateRepository.findByCountryId(countryId);
    }

    public State createState(State state) {
        return stateRepository.save(state);
    }

    public State updateState(UUID id, State stateDetails) {
        State state = getStateById(id);
        state.setName(stateDetails.getName());
        if (stateDetails.getCountry() != null) {
            state.setCountry(stateDetails.getCountry());
        }
        return stateRepository.save(state);
    }

    public void deleteState(UUID id) {
        State state = getStateById(id);
        stateRepository.delete(state);
    }
}
