package it.unimol.gestione_esami.dto.request;

import it.unimol.gestione_esami.enums.EnrollmentStatus;

public class UpdateStatusRequest {
    private EnrollmentStatus status;
    private String notes;
    public UpdateStatusRequest() {
    }

    public UpdateStatusRequest(EnrollmentStatus status, String notes) {
        this.status = status;
        this.notes = notes;
    }
    public EnrollmentStatus getStatus() {
        return status;
    }
    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }
    public void setStatus(EnrollmentStatus status) {
        this.status = status;
    }
}
