package com.behaviourbridgetracks.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalTime;

@Document(collection = "invoices")
public class Invoice {

    @Id
    private String id;

    private String appointmentId;

    private String clientName;
    private String clientEmail;

    private String practitionerName;

    private LocalDate appointmentDate;
    private LocalTime startTime;
    private LocalTime endTime;

    private int sessionDurationMinutes;

    private double feeAmount;

    private String status;

    private String internalBillingNotes;

    public Invoice() {
    }

    public Invoice(
            String appointmentId,
            String clientName,
            String clientEmail,
            String practitionerName,
            LocalDate appointmentDate,
            LocalTime startTime,
            LocalTime endTime,
            int sessionDurationMinutes,
            double feeAmount,
            String status,
            String internalBillingNotes
    ) {
        this.appointmentId = appointmentId;
        this.clientName = clientName;
        this.clientEmail = clientEmail;
        this.practitionerName = practitionerName;
        this.appointmentDate = appointmentDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.sessionDurationMinutes = sessionDurationMinutes;
        this.feeAmount = feeAmount;
        this.status = status;
        this.internalBillingNotes = internalBillingNotes;
    }

    public String getId() {
        return id;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public String getPractitionerName() {
        return practitionerName;
    }

    public void setPractitionerName(String practitionerName) {
        this.practitionerName = practitionerName;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public int getSessionDurationMinutes() {
        return sessionDurationMinutes;
    }

    public void setSessionDurationMinutes(int sessionDurationMinutes) {
        this.sessionDurationMinutes = sessionDurationMinutes;
    }

    public double getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(double feeAmount) {
        this.feeAmount = feeAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInternalBillingNotes() {
        return internalBillingNotes;
    }

    public void setInternalBillingNotes(String internalBillingNotes) {
        this.internalBillingNotes = internalBillingNotes;
    }
}
