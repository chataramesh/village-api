package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Event;
import com.example.demo.repository.EventRepository;

@Service
@Transactional
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public Event createEvent(Event event) {
        if (event.getStartTime().isAfter(event.getEndTime())) {
            throw new RuntimeException("Start time cannot be after end time");
        }
        return eventRepository.save(event);
    }

    public Event getEventById(UUID id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found with id: " + id));
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public List<Event> getActiveEvents() {
        return eventRepository.findByIsActiveTrue();
    }

    public Event updateEvent(UUID id, Event eventDetails) {
        Event event = getEventById(id);

        if (eventDetails.getName() != null) {
            event.setName(eventDetails.getName());
        }
        if (eventDetails.getDescription() != null) {
            event.setDescription(eventDetails.getDescription());
        }
        if (eventDetails.getStartTime() != null) {
            event.setStartTime(eventDetails.getStartTime());
        }
        if (eventDetails.getEndTime() != null) {
            event.setEndTime(eventDetails.getEndTime());
        }
        if (eventDetails.getPlace() != null) {
            event.setPlace(eventDetails.getPlace());
        }
        event.setActive(eventDetails.isActive());

        return eventRepository.save(event);
    }

    public void deleteEvent(UUID id) {
        Event event = getEventById(id);
        eventRepository.delete(event);
    }

    public List<Event> getUpcomingEvents() {
        return eventRepository.findUpcomingEvents(LocalDateTime.now());
    }

    public List<Event> getEventsInTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        return eventRepository.findEventsInTimeRange(startTime, endTime);
    }

    public List<Event> getEventsByPlace(String place) {
        return eventRepository.findEventsByPlace(place);
    }

    public List<Event> getEventsByName(String name) {
        return eventRepository.findEventsByName(name);
    }

    public List<Event> getOngoingEvents() {
        LocalDateTime now = LocalDateTime.now();
        return eventRepository.findOngoingEvents(now);
    }
}
