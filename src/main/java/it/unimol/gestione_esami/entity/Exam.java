package it.unimol.gestione_esami.entity;

import it.unimol.gestione_esami.enums.EnrollmentStatus;
import it.unimol.gestione_esami.enums.ExamStatus;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "exams")
public class Exam {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime time;

    @Column(name = "course_id", nullable = false)
    private Long courseId;

    @Column(name = "course_name")
    private String courseName;

    @Column(name = "professor_id", nullable = false)
    private Long professorId;

    @Column(name = "professor_name")
    private String professorName;

    @Column(name = "classroom_id", nullable = false)
    private Long classroomId;

    @Column(name = "classroom_name")
    private String classroomName;

    @Column(name ="max_students",nullable = false)
    private Integer maxStudents;

    @Column(name = "enrollment_deadline")
    private LocalDate enrollmentDeadline;

    @Enumerated(EnumType.STRING)
    @Column
    private ExamStatus status;

    @Column
    private String notes;

    @OneToMany(mappedBy = "exam", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExamEnrollment> enrollments = new ArrayList<>();

    public Exam() {
    }

    public Exam(Long id, String name, LocalDate date, LocalTime time, Long courseId, String courseName, Long professorId, String professorName, Long classroomId, String classroomName, Integer maxStudents, LocalDate enrollmentDeadline, ExamStatus status, String notes) {
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

    public List<ExamEnrollment> getEnrollments() {
        return enrollments;
    }
    public void setEnrollments(List<ExamEnrollment> enrollments) {
        this.enrollments = enrollments;
    }

    public int getCurrentEnrollments(){
        return (int) enrollments.stream()
                .filter(e ->e.getStatus() == EnrollmentStatus.ENROLLED)
                .count();
    }

    public boolean isEnrollmentOpen(){
        return status == ExamStatus.SCHEDULED
                && (enrollmentDeadline == null || !LocalDate.now().isAfter(enrollmentDeadline))
                && getCurrentEnrollments() < maxStudents;
    }

}
