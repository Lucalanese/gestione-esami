package it.unimol.gestione_esami.dto;

import it.unimol.gestione_esami.enums.GradeStatus;

import java.time.LocalDateTime;

public class GradeDTO {
    private Long id;
    private Long enrollmentId;
    private Long examId;
    private String examName;
    private Long studentId;
    private String studentName;
    private Integer grade;
    private Boolean honors;
    private GradeStatus status;
    private LocalDateTime evaluationDate;
    private Long professorId;
    private String professorName;
    private String feedback;
    private LocalDateTime publishedDate;
    private String formattedGrade;

    public GradeDTO() {
    }

    public GradeDTO(Long id, Long enrollmentId, Long examId, String examName, Long studentId, String studentName,
                    Integer grade, Boolean honors, GradeStatus status, LocalDateTime evaluationDate, Long professorId,
                    String professorName, String feedback, LocalDateTime publishedDate, String formattedGrade) {
        this.id = id;
        this.enrollmentId = enrollmentId;
        this.examId = examId;
        this.examName = examName;
        this.studentId = studentId;
        this.studentName = studentName;
        this.grade = grade;
        this.honors = honors;
        this.status = status;
        this.evaluationDate = evaluationDate;
        this.professorId = professorId;
        this.professorName = professorName;
        this.feedback = feedback;
        this.publishedDate = publishedDate;
        this.formattedGrade = formattedGrade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(Long enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public Long getExamId() {
        return examId;
    }

    public void setExamId(Long examId) {
        this.examId = examId;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
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

    public GradeStatus getStatus() {
        return status;
    }

    public void setStatus(GradeStatus status) {
        this.status = status;
    }

    public LocalDateTime getEvaluationDate() {
        return evaluationDate;
    }

    public void setEvaluationDate(LocalDateTime evaluationDate) {
        this.evaluationDate = evaluationDate;
    }

    public Long getProfessorId() {
        return professorId;
    }

    public void setProfessorId(Long professorId) {
        this.professorId = professorId;
    }

    public String getProfessorName() {
        return professorName;
    }

    public void setProfessorName(String professorName) {
        this.professorName = professorName;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public LocalDateTime getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDateTime publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getFormattedGrade() {
        return formattedGrade;
    }

    public void setFormattedGrade(String formattedGrade) {
        this.formattedGrade = formattedGrade;
    }
}
