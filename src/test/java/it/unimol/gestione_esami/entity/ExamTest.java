package it.unimol.gestione_esami.entity;

import it.unimol.gestione_esami.enums.EnrollmentStatus;
import it.unimol.gestione_esami.enums.ExamStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExamTest {

    private Exam createExam(ExamStatus status, LocalDate deadline, int maxStudents) {
        Exam exam = new Exam(1L, "Matematica", LocalDate.of(2026, 6, 15), LocalTime.of(9, 0),
                1L, "Matematica", 2L, "Prof. Rossi",
                3L, "Aula 1", maxStudents, deadline,
                status, null);
        return exam;
    }

    @Test
    void getCurrentEnrollments_whenNoEnrollments_returnsZero() {
        Exam exam = createExam(ExamStatus.SCHEDULED, LocalDate.now().plusDays(5), 30);
        assertEquals(0, exam.getCurrentEnrollments());
    }

    @Test
    void getCurrentEnrollments_whenOneEnrolled_returnsOne() {
        Exam exam = createExam(ExamStatus.SCHEDULED, LocalDate.now().plusDays(5), 30);
        ExamEnrollment enrollment = new ExamEnrollment();
        enrollment.setStatus(EnrollmentStatus.ENROLLED);
        exam.setEnrollments(List.of(enrollment));
        assertEquals(1, exam.getCurrentEnrollments());
    }

    @Test
    void getCurrentEnrollments_countsOnlyEnrolledStatus() {
        Exam exam = createExam(ExamStatus.SCHEDULED, LocalDate.now().plusDays(5), 30);
        ExamEnrollment enrolled = new ExamEnrollment();
        enrolled.setStatus(EnrollmentStatus.ENROLLED);
        ExamEnrollment withdrew = new ExamEnrollment();
        withdrew.setStatus(EnrollmentStatus.WITHDREW);
        exam.setEnrollments(List.of(enrolled, withdrew));
        assertEquals(1, exam.getCurrentEnrollments());
    }

    @Test
    void isEnrollmentOpen_whenScheduledAndDeadlineNotPassed_returnsTrue() {
        Exam exam = createExam(ExamStatus.SCHEDULED, LocalDate.now().plusDays(5), 30);
        assertTrue(exam.isEnrollmentOpen());
    }

    @Test
    void isEnrollmentOpen_whenDeadlinePassed_returnsFalse() {
        Exam exam = createExam(ExamStatus.SCHEDULED, LocalDate.now().minusDays(1), 30);
        assertFalse(exam.isEnrollmentOpen());
    }

    @Test
    void isEnrollmentOpen_whenExamFull_returnsFalse() {
        Exam exam = createExam(ExamStatus.SCHEDULED, LocalDate.now().plusDays(5), 1);
        ExamEnrollment enrollment = new ExamEnrollment();
        enrollment.setStatus(EnrollmentStatus.ENROLLED);
        exam.setEnrollments(List.of(enrollment));
        assertFalse(exam.isEnrollmentOpen());
    }

    @Test
    void isEnrollmentOpen_whenStatusNotScheduled_returnsFalse() {
        Exam exam = createExam(ExamStatus.COMPLETED, LocalDate.now().plusDays(5), 30);
        assertFalse(exam.isEnrollmentOpen());
    }
}