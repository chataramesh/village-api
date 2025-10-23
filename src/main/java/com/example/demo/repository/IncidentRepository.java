package com.example.demo.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Incident;
import com.example.demo.enums.IncidentCategory;
import com.example.demo.enums.IncidentPriority;
import com.example.demo.enums.IncidentStatus;

public interface IncidentRepository extends JpaRepository<Incident, UUID> {

    List<Incident> findByStatus(IncidentStatus status);

    List<Incident> findByCategory(IncidentCategory category);

    List<Incident> findByPriority(IncidentPriority priority);

    List<Incident> findByLocation(String location);

    List<Incident> findByLocationType(String locationType);

    List<Incident> findByReportedBy(String reportedBy);

    List<Incident> findByAssignedTo(String assignedTo);

    List<Incident> findByEscalatedTo(String escalatedTo);

    List<Incident> findByStatusAndCategory(IncidentStatus status, IncidentCategory category);

    List<Incident> findByStatusAndPriority(IncidentStatus status, IncidentPriority priority);

    @Query("SELECT i FROM Incident i WHERE i.status = :status ORDER BY i.createdAt DESC")
    List<Incident> findByStatusOrderByCreatedAtDesc(@Param("status") IncidentStatus status);

    @Query("SELECT i FROM Incident i WHERE i.category = :category ORDER BY i.createdAt DESC")
    List<Incident> findByCategoryOrderByCreatedAtDesc(@Param("category") IncidentCategory category);

    @Query("SELECT i FROM Incident i WHERE i.priority = :priority ORDER BY i.createdAt DESC")
    List<Incident> findByPriorityOrderByCreatedAtDesc(@Param("priority") IncidentPriority priority);

    @Query("SELECT i FROM Incident i WHERE i.location LIKE %:location% ORDER BY i.createdAt DESC")
    List<Incident> findByLocationContainingOrderByCreatedAtDesc(@Param("location") String location);

    @Query("SELECT i FROM Incident i WHERE i.title LIKE %:title% OR i.description LIKE %:description% ORDER BY i.createdAt DESC")
    List<Incident> searchByTitleOrDescription(@Param("title") String title, @Param("description") String description);

    @Query("SELECT i FROM Incident i WHERE i.status IN ('OPEN', 'IN_PROGRESS') ORDER BY i.priority DESC, i.createdAt ASC")
    List<Incident> findPendingIncidentsOrderByPriorityAndDate();

    @Query("SELECT i FROM Incident i WHERE i.status = 'ESCALATED' AND i.escalatedTo = :authority ORDER BY i.escalatedAt DESC")
    List<Incident> findEscalatedIncidentsByAuthority(@Param("authority") String authority);

    @Query("SELECT COUNT(i) FROM Incident i WHERE i.status = :status")
    Long countByStatus(@Param("status") IncidentStatus status);

    @Query("SELECT COUNT(i) FROM Incident i WHERE i.category = :category")
    Long countByCategory(@Param("category") IncidentCategory category);

    @Query("SELECT COUNT(i) FROM Incident i WHERE i.priority = :priority")
    Long countByPriority(@Param("priority") IncidentPriority priority);

    @Query("SELECT i FROM Incident i WHERE i.requiresFollowUp = true ORDER BY i.updatedAt ASC")
    List<Incident> findIncidentsRequiringFollowUp();

    @Query("SELECT i FROM Incident i WHERE i.createdAt BETWEEN :startDate AND :endDate ORDER BY i.createdAt DESC")
    List<Incident> findIncidentsInDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT i FROM Incident i WHERE i.status = 'RESOLVED' AND i.resolvedAt BETWEEN :startDate AND :endDate ORDER BY i.resolvedAt DESC")
    List<Incident> findResolvedIncidentsInDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
