package it.unimol.gestione_esami.dto;

import it.unimol.gestione_esami.enums.ExamStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public class ExamDTO {
    private Long id;
    private String name;
    private LocalDate date;
    private LocalTime time;
    private Long courseId;
    private String courseName;
    private Long professorId;
    private String professorName;
    private Long classroomId;
    private String classroomName;
    private Integer maxStudents;
    private LocalDate enrollmentDeadline;
    private ExamStatus status;
    private String notes;
    private Integer currentEnrollments;
    private Boolean enrollmentOpen;

    public ExamDTO() {
    }

    public ExamDTO(Long id, String name, LocalDate date, LocalTime time, Long courseId,
                   String courseName, Long professorId, String professorName, Long classroomId, String classroomName,
                   Integer maxStudents, LocalDate enrollmentDeadline, ExamStatus status, String notes,
                   Integer currentEnrollments, Boolean enrollmentOpen) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
        this.courseId = courseId;
        this.courseName = courseName;
        this.professorId = professorId;
        this.professorName = professorName;
        this.classroomId = classroomId;
        this.classroomName = classroomName;
        this.maxStudents = maxStudents;
        this.enrollmentDeadline = enrollmentDeadline;
        this.status = status;
        this.notes = notes;
        this.currentEnrollments = currentEnrollments;
        this.enrollmentOpen = enrollmentOpen;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
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

    public Long getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(Long classroomId) {
        this.classroomId = classroomId;
    }

    public String getClassroomName() {
        return classroomName;
    }

    public void setClassroomName(String classroomName) {
        this.classroomName = classroomName;
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

    public Integer getCurrentEnrollments() {
        return currentEnrollments;
    }

    public void setCurrentEnrollments(Integer currentEnrollments) {
        this.currentEnrollments = currentEnrollments;
    }

    public Boolean getEnrollmentOpen() {
        return enrollmentOpen;
    }

    public void setEnrollmentOpen(Boolean enrollmentOpen) {
        this.enrollmentOpen = enrollmentOpen;
    }
}
