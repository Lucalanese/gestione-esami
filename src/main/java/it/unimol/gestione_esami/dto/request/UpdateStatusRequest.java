package it.unimol.gestione_esami.dto.request;

import it.unimol.gestione_esami.enums.EnrollmentStatus;

public class UpdateStatusRequest {
    private EnrollmentStatus status;
    public UpdateStatusRequest() {
    }

    public UpdateStatusRequest(EnrollmentStatus status) {
        this.status = status;
    }
    public EnrollmentStatus getStatus() {
        return status;
    }
    public void setStatus(EnrollmentStatus status) {
        this.status = status;
    }
}
