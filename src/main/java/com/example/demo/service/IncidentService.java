package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Incident;
import com.example.demo.enums.IncidentStatus;
import com.example.demo.enums.IncidentCategory;
import com.example.demo.enums.IncidentPriority;
import com.example.demo.repository.IncidentRepository;

@Service
@Transactional
public class IncidentService {

    @Autowired
    private IncidentRepository incidentRepository;

    // ==================== BASIC CRUD OPERATIONS ====================

    public Incident createIncident(Incident incident) {
        incident.setStatus(IncidentStatus.OPEN);
        incident.setCreatedAt(LocalDateTime.now());

        // Auto-escalate critical incidents
        if (incident.getPriority() == IncidentPriority.CRITICAL) {
            escalateIncident(incident, "SYSTEM_AUTO_ESCALATION");
        }

        return incidentRepository.save(incident);
    }

    public Incident getIncidentById(UUID id) {
        return incidentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Incident not found with id: " + id));
    }

    public List<Incident> getAllIncidents() {
        return incidentRepository.findAll();
    }

    public Incident updateIncident(UUID id, Incident incidentDetails) {
        Incident incident = getIncidentById(id);

        if (incidentDetails.getTitle() != null) {
            incident.setTitle(incidentDetails.getTitle());
        }
        if (incidentDetails.getDescription() != null) {
            incident.setDescription(incidentDetails.getDescription());
        }
        if (incidentDetails.getCategory() != null) {
            incident.setCategory(incidentDetails.getCategory());
        }
        if (incidentDetails.getPriority() != null) {
            incident.setPriority(incidentDetails.getPriority());
        }
        if (incidentDetails.getLocation() != null) {
            incident.setLocation(incidentDetails.getLocation());
        }
        if (incidentDetails.getLocationType() != null) {
            incident.setLocationType(incidentDetails.getLocationType());
        }
        if (incidentDetails.getContactInfo() != null) {
            incident.setContactInfo(incidentDetails.getContactInfo());
        }
        if (incidentDetails.getUrgencyReason() != null) {
            incident.setUrgencyReason(incidentDetails.getUrgencyReason());
        }
        incident.setRequiresFollowUp(incidentDetails.isRequiresFollowUp());

        return incidentRepository.save(incident);
    }

    public void deleteIncident(UUID id) {
        Incident incident = getIncidentById(id);
        incidentRepository.delete(incident);
    }

    // ==================== STATUS MANAGEMENT ====================

    public Incident updateIncidentStatus(UUID id, IncidentStatus newStatus, String updatedBy) {
        Incident incident = getIncidentById(id);
        IncidentStatus oldStatus = incident.getStatus();

        incident.setStatus(newStatus);

        // Handle status-specific logic
        if (newStatus == IncidentStatus.RESOLVED && incident.getResolution() != null) {
            incident.setResolvedAt(LocalDateTime.now());
        } else if (newStatus == IncidentStatus.ESCALATED) {
            incident.setEscalatedAt(LocalDateTime.now());
        }

        return incidentRepository.save(incident);
    }

    public Incident assignIncident(UUID id, String assignedTo) {
        Incident incident = getIncidentById(id);
        incident.setAssignedTo(assignedTo);
        incident.setStatus(IncidentStatus.IN_PROGRESS);
        return incidentRepository.save(incident);
    }

    public Incident escalateIncident(UUID id, String escalatedTo, String escalationLevel) {
        Incident incident = getIncidentById(id);
        incident.setEscalatedTo(escalatedTo);
        incident.setEscalationLevel(escalationLevel);
        incident.setEscalatedAt(LocalDateTime.now());
        incident.setStatus(IncidentStatus.ESCALATED);
        return incidentRepository.save(incident);
    }

    public Incident escalateIncident(Incident incident, String escalatedTo) {
        return escalateIncident(incident.getId(), escalatedTo, "LEVEL_1");
    }

    public Incident resolveIncident(UUID id, String resolution) {
        Incident incident = getIncidentById(id);
        incident.setResolution(resolution);
        incident.setResolvedAt(LocalDateTime.now());
        incident.setStatus(IncidentStatus.RESOLVED);
        return incidentRepository.save(incident);
    }

    // ==================== QUERY METHODS ====================

    public List<Incident> getIncidentsByStatus(IncidentStatus status) {
        return incidentRepository.findByStatusOrderByCreatedAtDesc(status);
    }

    public List<Incident> getIncidentsByCategory(IncidentCategory category) {
        return incidentRepository.findByCategoryOrderByCreatedAtDesc(category);
    }

    public List<Incident> getIncidentsByPriority(IncidentPriority priority) {
        return incidentRepository.findByPriorityOrderByCreatedAtDesc(priority);
    }

    public List<Incident> getIncidentsByLocation(String location) {
        return incidentRepository.findByLocationContainingOrderByCreatedAtDesc(location);
    }

    public List<Incident> getIncidentsByLocationType(String locationType) {
        return incidentRepository.findByLocationType(locationType);
    }

    public List<Incident> getIncidentsByReportedBy(String reportedBy) {
        return incidentRepository.findByReportedBy(reportedBy);
    }

    public List<Incident> getIncidentsByAssignedTo(String assignedTo) {
        return incidentRepository.findByAssignedTo(assignedTo);
    }

    public List<Incident> getIncidentsByEscalatedTo(String escalatedTo) {
        return incidentRepository.findEscalatedIncidentsByAuthority(escalatedTo);
    }

    public List<Incident> searchIncidents(String searchTerm) {
        return incidentRepository.searchByTitleOrDescription(searchTerm, searchTerm);
    }

    public List<Incident> getPendingIncidents() {
        return incidentRepository.findPendingIncidentsOrderByPriorityAndDate();
    }

    public List<Incident> getIncidentsRequiringFollowUp() {
        return incidentRepository.findIncidentsRequiringFollowUp();
    }

    // ==================== STATISTICS ====================

    public Long getIncidentCountByStatus(IncidentStatus status) {
        return incidentRepository.countByStatus(status);
    }

    public Long getIncidentCountByCategory(IncidentCategory category) {
        return incidentRepository.countByCategory(category);
    }

    public Long getIncidentCountByPriority(IncidentPriority priority) {
        return incidentRepository.countByPriority(priority);
    }

    public List<Incident> getRecentIncidents(int limit) {
        return incidentRepository.findAll()
                .stream()
                .sorted((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()))
                .limit(limit)
                .toList();
    }

    // ==================== ESCALATION HELPERS ====================

    public List<String> getEscalationLevels() {
        return List.of(
            "LOCAL_AUTHORITY",
            "SARPANCH",
            "MLA",
            "MP",
            "DISTRICT_COLLECTOR",
            "STATE_GOVERNMENT"
        );
    }

    public boolean shouldEscalate(Incident incident) {
        // Auto-escalate based on priority and time
        if (incident.getPriority() == IncidentPriority.CRITICAL) {
            return true;
        }

        if (incident.getStatus() == IncidentStatus.OPEN &&
            incident.getCreatedAt().isBefore(LocalDateTime.now().minusDays(7))) {
            return true;
        }

        return false;
    }

    public String getNextEscalationLevel(String currentLevel) {
        List<String> levels = getEscalationLevels();
        int currentIndex = levels.indexOf(currentLevel);
        if (currentIndex >= 0 && currentIndex < levels.size() - 1) {
            return levels.get(currentIndex + 1);
        }
        return "STATE_GOVERNMENT"; // Highest level
    }
}
