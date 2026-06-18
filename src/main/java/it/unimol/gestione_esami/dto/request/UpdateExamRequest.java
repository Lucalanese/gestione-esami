package it.unimol.gestione_esami.dto.request;

import it.unimol.gestione_esami.enums.ExamStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public class UpdateExamRequest {
    private String name;
    private LocalDate date;
    private LocalTime time;
    private Long courseId;
    private Long professorId;
    private Long classroomId;
    private Integer maxStudents;
    private LocalDate enrollmentDeadline;
    private ExamStatus status;
    private String notes;

    public UpdateExamRequest() {
    }

    public UpdateExamRequest(String name, LocalDate date, LocalTime time, Long courseId, Long professorId,
                             Long classroomId, Integer maxStudents, LocalDate enrollmentDeadline, ExamStatus status, String notes) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.courseId = courseId;
        this.professorId = professorId;
        this.classroomId = classroomId;
        this.maxStudents = maxStudents;
        this.enrollmentDeadline = enrollmentDeadline;
        this.status = status;
        this.notes = notes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getProfessorId() {
        return professorId;
    }

    public void setProfessorId(Long professorId) {
        this.professorId = professorId;
    }

    public Long getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(Long classroomId) {
        this.classroomId = classroomId;
    }

    public Integer getMaxStudents() {
        return maxStudents;
    }

    public void setMaxStudents(Integer maxStudents) {
        this.maxStudents = maxStudents;
    }

    public LocalDate getEnrollmentDeadline() {
        return enrollmentDeadline;
    }

    public void setEnrollmentDeadline(LocalDate enrollmentDeadline) {
        this.enrollmentDeadline = enrollmentDeadline;
    }

    public ExamStatus getStatus() {
        return status;
    }

    public void setStatus(ExamStatus status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
