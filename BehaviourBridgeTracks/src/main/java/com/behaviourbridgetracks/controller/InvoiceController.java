package com.behaviourbridgetracks.controller;

import com.behaviourbridgetracks.model.Invoice;
import com.behaviourbridgetracks.repository.InvoiceRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoices")
@CrossOrigin(origins = "*")
public class InvoiceController {

    private final InvoiceRepository invoiceRepository;

    public InvoiceController(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @PostMapping
    public ResponseEntity<Invoice> createInvoice(@RequestBody Invoice invoice) {
        invoice.setStatus(
                invoice.getStatus() == null || invoice.getStatus().isBlank()
                        ? "Draft"
                        : invoice.getStatus()
        );

        Invoice savedInvoice = invoiceRepository.save(invoice);
        return ResponseEntity.ok(savedInvoice);
    }

    @GetMapping
    public ResponseEntity<List<Invoice>> getAllInvoices() {
        return ResponseEntity.ok(invoiceRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Invoice> getInvoiceById(@PathVariable String id) {
        return invoiceRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/appointment/{appointmentId}")
    public ResponseEntity<Invoice> getInvoiceByAppointmentId(
            @PathVariable String appointmentId
    ) {
        return invoiceRepository.findByAppointmentId(appointmentId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Invoice> updateInvoice(
            @PathVariable String id,
            @RequestBody Invoice updatedInvoice
    ) {
        return invoiceRepository.findById(id)
                .map(existingInvoice -> {
                    existingInvoice.setAppointmentId(updatedInvoice.getAppointmentId());
                    existingInvoice.setClientName(updatedInvoice.getClientName());
                    existingInvoice.setClientEmail(updatedInvoice.getClientEmail());
                    existingInvoice.setPractitionerName(updatedInvoice.getPractitionerName());
                    existingInvoice.setAppointmentDate(updatedInvoice.getAppointmentDate());
                    existingInvoice.setStartTime(updatedInvoice.getStartTime());
                    existingInvoice.setEndTime(updatedInvoice.getEndTime());
                    existingInvoice.setSessionDurationMinutes(updatedInvoice.getSessionDurationMinutes());
                    existingInvoice.setFeeAmount(updatedInvoice.getFeeAmount());
                    existingInvoice.setStatus(updatedInvoice.getStatus());
                    existingInvoice.setInternalBillingNotes(updatedInvoice.getInternalBillingNotes());

                    Invoice savedInvoice = invoiceRepository.save(existingInvoice);
                    return ResponseEntity.ok(savedInvoice);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable String id) {
        if (!invoiceRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        invoiceRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
