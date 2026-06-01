package it.unimol.gestione_esami.dto;

import it.unimol.gestione_esami.enums.ExamStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public class ExamCalendarDTO {
    private Long examId;
    private String courseCode;
    private String courseName;
    private String professorName;
    private LocalDate examDate;
    private LocalTime examTime;
    private String classroomName;
    private Integer availableSlots;
    private ExamStatus status;

    public ExamCalendarDTO() {
    }

    public ExamCalendarDTO(ExamStatus status, Integer availableSlots, String classroomName, LocalTime examTime,
                           LocalDate examDate, String professorName, String courseName, String courseCode, Long examId) {
        this.status = status;
        this.availableSlots = availableSlots;
        this.classroomName = classroomName;
        this.examTime = examTime;
        this.examDate = examDate;
        this.professorName = professorName;
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.examId = examId;
    }

    public Long getExamId() {
        return examId;
    }

    public void setExamId(Long examId) {
        this.examId = examId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getProfessorName() {
        return professorName;
    }

    public void setProfessorName(String professorName) {
        this.professorName = professorName;
    }

    public LocalDate getExamDate() {
        return examDate;
    }

    public void setExamDate(LocalDate examDate) {
        this.examDate = examDate;
    }

    public LocalTime getExamTime() {
        return examTime;
    }

    public void setExamTime(LocalTime examTime) {
        this.examTime = examTime;
    }

    public String getClassroomName() {
        return classroomName;
    }

    public void setClassroomName(String classroomName) {
        this.classroomName = classroomName;
    }

    public Integer getAvailableSlots() {
        return availableSlots;
    }

    public void setAvailableSlots(Integer availableSlots) {
        this.availableSlots = availableSlots;
    }

    public ExamStatus getStatus() {
        return status;
    }

    public void setStatus(ExamStatus status) {
        this.status = status;
    }
}
