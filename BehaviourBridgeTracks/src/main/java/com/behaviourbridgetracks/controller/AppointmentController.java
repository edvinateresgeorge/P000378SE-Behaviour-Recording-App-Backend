package com.behaviourbridgetracks.controller;

import com.behaviourbridgetracks.dto.AppointmentRequest;
import com.behaviourbridgetracks.model.Appointment;
import com.behaviourbridgetracks.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentRepository appointmentRepo;

    // ── Book a new appointment ──────────────────────────
    // Both CLIENT and ADMIN can book
    @PostMapping
    public ResponseEntity<?> bookAppointment(
            @RequestBody AppointmentRequest request,
            @AuthenticationPrincipal String userId) {

        Appointment appointment = Appointment.builder()
                .clientId(request.getClientId())
                .userId(userId)
                .clientName(request.getClientName())
                .practitionerId(request.getPractitionerId())
                .practitionerName(request.getPractitionerName())
                .title(request.getTitle())
                .description(request.getDescription())
                .location(request.getLocation())
                .type(request.getType())
                .appointmentDate(request.getAppointmentDate())
                .durationMinutes(request.getDurationMinutes())
                .status("PENDING")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return ResponseEntity.status(201)
                .body(appointmentRepo.save(appointment));
    }

    // ── Get my appointments ─────────────────────────────
    // CLIENT sees their own upcoming appointments
    @GetMapping("/my")
    public ResponseEntity<List<Appointment>> getMyAppointments(
            @AuthenticationPrincipal String userId) {

        List<Appointment> upcoming =
                appointmentRepo
                        .findByUserIdAndAppointmentDateAfter(
                                userId, LocalDateTime.now());

        return ResponseEntity.ok(upcoming);
    }

    // Get appointment history
// Shows COMPLETED and CANCELLED appointments
    @GetMapping("/history")
    public ResponseEntity<List<Appointment>> getHistory(
            @AuthenticationPrincipal String userId) {

        // Get all appointments for this user
        List<Appointment> all =
                appointmentRepo.findByUserId(userId);

        // Filter completed and cancelled
        List<Appointment> history = all.stream()
                .filter(a -> a.getStatus().equals("COMPLETED")
                        || a.getStatus().equals("CANCELLED"))
                .collect(java.util.stream.Collectors.toList());

        return ResponseEntity.ok(history);
    }

    // ── Get all appointments ────────────────────────────
    // ADMIN sees all appointments across all clients
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Appointment>> getAll() {
        return ResponseEntity.ok(
                appointmentRepo.findAll());
    }

    // ── Get single appointment ──────────────────────────
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
            @PathVariable String id) {
        return appointmentRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ── Update appointment ──────────────────────────────
    // ADMIN can confirm or update details
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateAppointment(
            @PathVariable String id,
            @RequestBody AppointmentRequest request) {

        return appointmentRepo.findById(id)
                .map(existing -> {
                    existing.setTitle(request.getTitle());
                    existing.setDescription(
                            request.getDescription());
                    existing.setLocation(request.getLocation());
                    existing.setType(request.getType());
                    existing.setAppointmentDate(
                            request.getAppointmentDate());
                    existing.setDurationMinutes(
                            request.getDurationMinutes());
                    existing.setUpdatedAt(LocalDateTime.now());
                    return ResponseEntity.ok(
                            appointmentRepo.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // ── Confirm appointment ─────────────────────────────
    // ADMIN confirms a pending appointment
    @PutMapping("/{id}/confirm")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> confirmAppointment(
            @PathVariable String id) {

        return appointmentRepo.findById(id)
                .map(existing -> {
                    existing.setStatus("CONFIRMED");
                    existing.setUpdatedAt(LocalDateTime.now());
                    return ResponseEntity.ok(
                            appointmentRepo.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // ── Cancel appointment ──────────────────────────────
    // Both roles can cancel
    @PutMapping("/{id}/cancel")
    public ResponseEntity<?> cancelAppointment(
            @PathVariable String id,
            @AuthenticationPrincipal String userId) {

        return appointmentRepo.findById(id)
                .map(existing -> {
                    existing.setStatus("CANCELLED");
                    existing.setUpdatedAt(LocalDateTime.now());
                    return ResponseEntity.ok(
                            appointmentRepo.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // ── Complete appointment ────────────────────────────
    // ADMIN marks appointment as completed
    // and adds session notes
    @PutMapping("/{id}/complete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> completeAppointment(
            @PathVariable String id,
            @RequestBody(required = false)
            String sessionNotes) {

        return appointmentRepo.findById(id)
                .map(existing -> {
                    existing.setStatus("COMPLETED");
                    existing.setSessionNotes(sessionNotes);
                    existing.setUpdatedAt(LocalDateTime.now());
                    return ResponseEntity.ok(
                            appointmentRepo.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // ── Get upcoming appointments (admin) ───────────────
    @GetMapping("/upcoming")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Appointment>> getUpcoming() {
        return ResponseEntity.ok(
                appointmentRepo.findByAppointmentDateAfter(
                        LocalDateTime.now()));
    }

    // ── Get appointments by client (admin) ──────────────
    @GetMapping("/client/{clientId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Appointment>> getByClient(
            @PathVariable String clientId) {
        return ResponseEntity.ok(
                appointmentRepo.findByClientId(clientId));
    }

}