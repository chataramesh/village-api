package com.example.demo.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Event;

public interface EventRepository extends JpaRepository<Event, UUID> {

    List<Event> findByIsActiveTrue();

    @Query("SELECT e FROM Event e WHERE e.startTime >= :currentTime AND e.isActive = true ORDER BY e.startTime")
    List<Event> findUpcomingEvents(@Param("currentTime") LocalDateTime currentTime);

    @Query("SELECT e FROM Event e WHERE e.startTime BETWEEN :startTime AND :endTime AND e.isActive = true")
    List<Event> findEventsInTimeRange(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    @Query("SELECT e FROM Event e WHERE e.place LIKE %:place% AND e.isActive = true")
    List<Event> findEventsByPlace(@Param("place") String place);

    @Query("SELECT e FROM Event e WHERE e.name LIKE %:name% AND e.isActive = true")
    List<Event> findEventsByName(@Param("name") String name);

    @Query("SELECT e FROM Event e WHERE e.startTime >= :currentTime AND e.endTime <= :currentTime AND e.isActive = true")
    List<Event> findOngoingEvents(@Param("currentTime") LocalDateTime currentTime);
}
