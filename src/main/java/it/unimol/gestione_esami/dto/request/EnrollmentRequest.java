package it.unimol.gestione_esami.dto.request;

public class EnrollmentRequest {
    private String notes;

    public EnrollmentRequest() {
    }

    public EnrollmentRequest(String notes) {
        this.notes = notes;
    }
    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }
}
