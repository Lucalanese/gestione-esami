package it.unimol.gestione_esami.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;
import jakarta.validation.constraints.*;

public class CreateExamRequest {
    @NotBlank(message = "Il nome è obbligatorio")
    private String name;

    @NotNull
    @FutureOrPresent(message = "La data deve essere successiva o uguale a oggi")
    private LocalDate date;

    @NotNull(message = "L'orario è obbligatorio")
    private LocalTime time;

    @NotNull(message = "Il corso è obbligatorio")
    @Min(value = 1, message = "Il corso deve essere valido")
    private Long courseId;

    @NotNull(message = "Il prof è obbligatorio")
    @Min(value = 1, message = "Il prof deve essere valido")
    private Long professorId;

    @NotNull(message = "L'aula è obbligatoria")
    @Min(value = 1, message = "L'aula deve essere valida")
    private Long classroomId;

    @NotNull(message = "il numero massimo di studenti è obbligatorio")
    @Min(value = 1, message = "il numero massimo di studenti deve essere maggiore di 1")
    private Integer maxStudents;
    private LocalDate enrollmentDeadline;
    private String notes;


    public CreateExamRequest() {
    }

    public CreateExamRequest(String name, LocalDate date, LocalTime time, Long courseId, Long professorId,
                             Long classroomId, Integer maxStudents, LocalDate enrollmentDeadline, String notes) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.courseId = courseId;
        this.professorId = professorId;
        this.classroomId = classroomId;
        this.maxStudents = maxStudents;
        this.enrollmentDeadline = enrollmentDeadline;
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
