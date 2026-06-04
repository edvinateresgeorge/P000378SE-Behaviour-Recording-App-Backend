package com.behaviourbridgetracks.repository;

import com.behaviourbridgetracks.model.Appointment;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository
        extends MongoRepository<Appointment, String> {

    // Get all appointments for a specific user (parent)
    List<Appointment> findByUserId(String userId);

    // Get all appointments for a specific client (child)
    List<Appointment> findByClientId(String clientId);

    // Get all appointments for a practitioner
    List<Appointment> findByPractitionerId(
            String practitionerId);

    // Get appointments by status
    List<Appointment> findByUserIdAndStatus(
            String userId, String status);

    // Get upcoming appointments
    List<Appointment> findByUserIdAndAppointmentDateAfter(
            String userId, LocalDateTime date);

    // Get past appointments (history)
    List<Appointment> findByUserIdAndAppointmentDateBefore(
            String userId, LocalDateTime date);

    // Get all upcoming appointments (admin)
    List<Appointment> findByAppointmentDateAfter(
            LocalDateTime date);
}