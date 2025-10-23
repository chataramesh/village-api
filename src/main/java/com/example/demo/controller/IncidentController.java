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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Incident;
import com.example.demo.enums.IncidentCategory;
import com.example.demo.enums.IncidentPriority;
import com.example.demo.enums.IncidentStatus;
import com.example.demo.service.IncidentService;

@RestController
@RequestMapping("/api/incidents")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class IncidentController {

    @Autowired
    private IncidentService incidentService;

    // ==================== BASIC CRUD OPERATIONS ====================

    @GetMapping("/all")
    public ResponseEntity<List<Incident>> getAllIncidents() {
        List<Incident> incidents = incidentService.getAllIncidents();
        return ResponseEntity.ok(incidents);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Incident> getIncidentById(@PathVariable UUID id) {
        Incident incident = incidentService.getIncidentById(id);
        return ResponseEntity.ok(incident);
    }

    @PostMapping("/create")
    public ResponseEntity<Incident> createIncident(@RequestBody Incident incident) {
        Incident createdIncident = incidentService.createIncident(incident);
        return ResponseEntity.ok(createdIncident);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Incident> updateIncident(@PathVariable UUID id, @RequestBody Incident incidentDetails) {
        Incident updatedIncident = incidentService.updateIncident(id, incidentDetails);
        return ResponseEntity.ok(updatedIncident);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteIncident(@PathVariable UUID id) {
        incidentService.deleteIncident(id);
        return ResponseEntity.ok("Incident deleted successfully");
    }

    // ==================== STATUS MANAGEMENT ====================

    @PutMapping("/{id}/status/{status}")
    public ResponseEntity<Incident> updateIncidentStatus(
            @PathVariable UUID id,
            @PathVariable IncidentStatus status,
            @RequestParam(value = "updatedBy", required = false) String updatedBy) {
        Incident incident = incidentService.updateIncidentStatus(id, status, updatedBy);
        return ResponseEntity.ok(incident);
    }

    @PutMapping("/{id}/assign")
    public ResponseEntity<Incident> assignIncident(
            @PathVariable UUID id,
            @RequestParam String assignedTo) {
        Incident incident = incidentService.assignIncident(id, assignedTo);
        return ResponseEntity.ok(incident);
    }

    @PutMapping("/{id}/escalate")
    public ResponseEntity<Incident> escalateIncident(
            @PathVariable UUID id,
            @RequestParam String escalatedTo,
            @RequestParam(value = "escalationLevel", required = false) String escalationLevel) {
        Incident incident = incidentService.escalateIncident(id, escalatedTo, escalationLevel);
        return ResponseEntity.ok(incident);
    }

    @PutMapping("/{id}/resolve")
    public ResponseEntity<Incident> resolveIncident(
            @PathVariable UUID id,
            @RequestParam String resolution) {
        Incident incident = incidentService.resolveIncident(id, resolution);
        return ResponseEntity.ok(incident);
    }

    // ==================== FILTERING AND SEARCH ====================

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Incident>> getIncidentsByStatus(@PathVariable IncidentStatus status) {
        List<Incident> incidents = incidentService.getIncidentsByStatus(status);
        return ResponseEntity.ok(incidents);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Incident>> getIncidentsByCategory(@PathVariable IncidentCategory category) {
        List<Incident> incidents = incidentService.getIncidentsByCategory(category);
        return ResponseEntity.ok(incidents);
    }

    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<Incident>> getIncidentsByPriority(@PathVariable IncidentPriority priority) {
        List<Incident> incidents = incidentService.getIncidentsByPriority(priority);
        return ResponseEntity.ok(incidents);
    }

    @GetMapping("/location/{location}")
    public ResponseEntity<List<Incident>> getIncidentsByLocation(@PathVariable String location) {
        List<Incident> incidents = incidentService.getIncidentsByLocation(location);
        return ResponseEntity.ok(incidents);
    }

    @GetMapping("/location-type/{locationType}")
    public ResponseEntity<List<Incident>> getIncidentsByLocationType(@PathVariable String locationType) {
        List<Incident> incidents = incidentService.getIncidentsByLocationType(locationType);
        return ResponseEntity.ok(incidents);
    }

    @GetMapping("/reported-by/{reportedBy}")
    public ResponseEntity<List<Incident>> getIncidentsByReportedBy(@PathVariable String reportedBy) {
        List<Incident> incidents = incidentService.getIncidentsByReportedBy(reportedBy);
        return ResponseEntity.ok(incidents);
    }

    @GetMapping("/assigned-to/{assignedTo}")
    public ResponseEntity<List<Incident>> getIncidentsByAssignedTo(@PathVariable String assignedTo) {
        List<Incident> incidents = incidentService.getIncidentsByAssignedTo(assignedTo);
        return ResponseEntity.ok(incidents);
    }

    @GetMapping("/escalated-to/{escalatedTo}")
    public ResponseEntity<List<Incident>> getIncidentsByEscalatedTo(@PathVariable String escalatedTo) {
        List<Incident> incidents = incidentService.getIncidentsByEscalatedTo(escalatedTo);
        return ResponseEntity.ok(incidents);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Incident>> searchIncidents(@RequestParam String query) {
        List<Incident> incidents = incidentService.searchIncidents(query);
        return ResponseEntity.ok(incidents);
    }

    @GetMapping("/pending")
    public ResponseEntity<List<Incident>> getPendingIncidents() {
        List<Incident> incidents = incidentService.getPendingIncidents();
        return ResponseEntity.ok(incidents);
    }

    @GetMapping("/follow-up")
    public ResponseEntity<List<Incident>> getIncidentsRequiringFollowUp() {
        List<Incident> incidents = incidentService.getIncidentsRequiringFollowUp();
        return ResponseEntity.ok(incidents);
    }

    @GetMapping("/recent")
    public ResponseEntity<List<Incident>> getRecentIncidents(@RequestParam(defaultValue = "10") int limit) {
        List<Incident> incidents = incidentService.getRecentIncidents(limit);
        return ResponseEntity.ok(incidents);
    }

    // ==================== STATISTICS ====================

    @GetMapping("/stats/status/{status}")
    public ResponseEntity<Long> getIncidentCountByStatus(@PathVariable IncidentStatus status) {
        Long count = incidentService.getIncidentCountByStatus(status);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/stats/category/{category}")
    public ResponseEntity<Long> getIncidentCountByCategory(@PathVariable IncidentCategory category) {
        Long count = incidentService.getIncidentCountByCategory(category);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/stats/priority/{priority}")
    public ResponseEntity<Long> getIncidentCountByPriority(@PathVariable IncidentPriority priority) {
        Long count = incidentService.getIncidentCountByPriority(priority);
        return ResponseEntity.ok(count);
    }

    // ==================== ENUMS AND CONSTANTS ====================

    @GetMapping("/categories")
    public ResponseEntity<IncidentCategory[]> getIncidentCategories() {
        return ResponseEntity.ok(IncidentCategory.values());
    }

    @GetMapping("/statuses")
    public ResponseEntity<IncidentStatus[]> getIncidentStatuses() {
        return ResponseEntity.ok(IncidentStatus.values());
    }

    @GetMapping("/priorities")
    public ResponseEntity<IncidentPriority[]> getIncidentPriorities() {
        return ResponseEntity.ok(IncidentPriority.values());
    }

    @GetMapping("/escalation-levels")
    public ResponseEntity<List<String>> getEscalationLevels() {
        List<String> levels = incidentService.getEscalationLevels();
        return ResponseEntity.ok(levels);
    }

    // ==================== DASHBOARD DATA ====================

    @GetMapping("/dashboard/summary")
    public ResponseEntity<IncidentDashboardSummary> getDashboardSummary() {
        long totalIncidents = incidentService.getAllIncidents().size();
        long openIncidents = incidentService.getIncidentCountByStatus(IncidentStatus.OPEN);
        long inProgressIncidents = incidentService.getIncidentCountByStatus(IncidentStatus.IN_PROGRESS);
        long resolvedIncidents = incidentService.getIncidentCountByStatus(IncidentStatus.RESOLVED);
        long escalatedIncidents = incidentService.getIncidentCountByStatus(IncidentStatus.ESCALATED);

        List<Incident> recentIncidents = incidentService.getRecentIncidents(5);
        List<Incident> pendingIncidents = incidentService.getPendingIncidents();

        IncidentDashboardSummary summary = new IncidentDashboardSummary();
        summary.setTotalIncidents(totalIncidents);
        summary.setOpenIncidents(openIncidents);
        summary.setInProgressIncidents(inProgressIncidents);
        summary.setResolvedIncidents(resolvedIncidents);
        summary.setEscalatedIncidents(escalatedIncidents);
        summary.setRecentIncidents(recentIncidents);
        summary.setPendingIncidents(pendingIncidents);

        return ResponseEntity.ok(summary);
    }

    // DTO class for dashboard summary
    public static class IncidentDashboardSummary {
        private long totalIncidents;
        private long openIncidents;
        private long inProgressIncidents;
        private long resolvedIncidents;
        private long escalatedIncidents;
        private List<Incident> recentIncidents;
        private List<Incident> pendingIncidents;

        // Getters and setters
        public long getTotalIncidents() { return totalIncidents; }
        public void setTotalIncidents(long totalIncidents) { this.totalIncidents = totalIncidents; }

        public long getOpenIncidents() { return openIncidents; }
        public void setOpenIncidents(long openIncidents) { this.openIncidents = openIncidents; }

        public long getInProgressIncidents() { return inProgressIncidents; }
        public void setInProgressIncidents(long inProgressIncidents) { this.inProgressIncidents = inProgressIncidents; }

        public long getResolvedIncidents() { return resolvedIncidents; }
        public void setResolvedIncidents(long resolvedIncidents) { this.resolvedIncidents = resolvedIncidents; }

        public long getEscalatedIncidents() { return escalatedIncidents; }
        public void setEscalatedIncidents(long escalatedIncidents) { this.escalatedIncidents = escalatedIncidents; }

        public List<Incident> getRecentIncidents() { return recentIncidents; }
        public void setRecentIncidents(List<Incident> recentIncidents) { this.recentIncidents = recentIncidents; }

        public List<Incident> getPendingIncidents() { return pendingIncidents; }
        public void setPendingIncidents(List<Incident> pendingIncidents) { this.pendingIncidents = pendingIncidents; }
    }
}
