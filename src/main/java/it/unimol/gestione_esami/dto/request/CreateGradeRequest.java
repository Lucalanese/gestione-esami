package it.unimol.gestione_esami.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class CreateGradeRequest {

    @NotNull(message = "L'iscrizione è obbligatoria")
    private Long enrollmentId;

    @NotNull(message = "Il voto è obbligatorio")
    @Min(value = 0, message = "Il voto deve essere almeno 0")
    @Max(value = 30, message = "Il voto deve essere massimo 30")
    private Integer grade;

    private Boolean honors;
    private String feedback;

    public CreateGradeRequest() {
    }

    public CreateGradeRequest(Long enrollmentId, Integer grade, Boolean honors, String feedback) {
        this.enrollmentId = enrollmentId;
        this.grade = grade;
        this.honors = honors;
        this.feedback = feedback;
    }

    public Long getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(Long enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Boolean getHonors() {
        return honors;
    }

    public void setHonors(Boolean honors) {
        this.honors = honors;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
