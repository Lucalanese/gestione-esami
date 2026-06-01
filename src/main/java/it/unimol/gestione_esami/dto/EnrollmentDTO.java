package it.unimol.gestione_esami.dto;

import it.unimol.gestione_esami.enums.EnrollmentStatus;
import it.unimol.gestione_esami.enums.ExamStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class EnrollmentDTO {
    private Long id;
    private Long examId;
    private String examName;
    private LocalDate examDate;
    private Long studentId;
    private String studentName;
    private EnrollmentStatus status;
    private LocalDateTime enrollmentDate;
    private String notes;
    private Boolean hasGrade;
    private Integer grade;

    public EnrollmentDTO() {
    }

    public EnrollmentDTO(Long id, Long examId, String examName, LocalDate examDate, Long studentId, String studentName,
                         EnrollmentStatus status, LocalDateTime enrollmentDate, String notes, Boolean hasGrade,
                         Integer grade) {
        this.id = id;
        this.examId = examId;
        this.examName = examName;
        this.examDate = examDate;
        this.studentId = studentId;
        this.studentName = studentName;
        this.status = status;
        this.enrollmentDate = enrollmentDate;
        this.notes = notes;
        this.hasGrade = hasGrade;
        this.grade = grade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDate getExamDate() {
        return examDate;
    }

    public void setExamDate(LocalDate examDate) {
        this.examDate = examDate;
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

    public EnrollmentStatus getStatus() {
        return status;
    }

    public void setStatus(EnrollmentStatus status) {
        this.status = status;
    }

    public LocalDateTime getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(LocalDateTime enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Boolean getHasGrade() {
        return hasGrade;
    }

    public void setHasGrade(Boolean hasGrade) {
        this.hasGrade = hasGrade;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }
}
