package it.unimol.gestione_esami.entity;

import it.unimol.gestione_esami.enums.GradeStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "exam_grades")
public class ExamGrade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "exam_enrollment_id", nullable = false)
    private ExamEnrollment examEnrollment;

    @Column(nullable = false)
    private Integer grade;

    @Column
    private Boolean honors;

    @Enumerated(EnumType.STRING)
    private GradeStatus status;

    @Column(name = "evaluation_date")
    private LocalDateTime evaluationDate;

    @Column(name = "professor_id")
    private Long professorId;

    @Column
    private String feedback;

    @Column(name = "published_date")
    private LocalDateTime publishedDate;

    public ExamGrade() {
    }

    public ExamGrade(ExamEnrollment examEnrollment, Integer grade, Boolean honors, GradeStatus status, LocalDateTime evaluationDate, Long professorId, String feedback, LocalDateTime publishedDate) {
        this.examEnrollment = examEnrollment;
        this.grade = grade;
        this.honors = honors;
        this.status = status;
        this.evaluationDate = evaluationDate;
        this.professorId = professorId;
        this.feedback = feedback;
        this.publishedDate = publishedDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ExamEnrollment getExamEnrollment() {
        return examEnrollment;
    }

    public void setExamEnrollment(ExamEnrollment examEnrollment) {
        this.examEnrollment = examEnrollment;
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
}
