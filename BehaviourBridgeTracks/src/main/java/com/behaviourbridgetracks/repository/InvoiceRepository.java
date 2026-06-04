package com.behaviourbridgetracks.repository;

import com.behaviourbridgetracks.model.Invoice;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface InvoiceRepository extends MongoRepository<Invoice, String> {
    Optional<Invoice> findByAppointmentId(String appointmentId);
}