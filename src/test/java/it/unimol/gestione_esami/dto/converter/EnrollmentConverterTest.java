package it.unimol.gestione_esami.dto.converter;

import it.unimol.gestione_esami.dto.EnrollmentDTO;
import it.unimol.gestione_esami.entity.Exam;
import it.unimol.gestione_esami.entity.ExamEnrollment;
import it.unimol.gestione_esami.entity.ExamGrade;
import it.unimol.gestione_esami.enums.EnrollmentStatus;
import it.unimol.gestione_esami.enums.ExamStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class EnrollmentConverterTest {

    private final EnrollmentConverter enrollmentConverter = new EnrollmentConverter();

    @Test
    void toDto_mapsAllFieldsCorrectly() {

        Exam exam = new Exam(1L, "Matematica", LocalDate.of(2026, 6, 15), LocalTime.of(9, 0),
                1L, "Corso di Matematica", 2L, "Prof. Rossi",
                3L, "Aula 1", 30, LocalDate.now().plusDays(10),
                ExamStatus.SCHEDULED, null);

        ExamEnrollment enrollment = new ExamEnrollment();
        enrollment.setId(1L);
        enrollment.setExam(exam);
        enrollment.setStudentId(10L);
        enrollment.setStudentName("Mario Rossi");
        enrollment.setEnrollmentDate(LocalDateTime.of(2026, 6, 1, 10, 0));
        enrollment.setStatus(EnrollmentStatus.ENROLLED);
        enrollment.setNotes("Nessuna nota");
        enrollment.setExamGrade(null);

        EnrollmentDTO dto = enrollmentConverter.toDto(enrollment);

        assertEquals(1L, dto.getId());
        assertEquals(1L, dto.getExamId());
        assertEquals("Matematica", dto.getExamName());
        assertEquals(LocalDate.of(2026, 6, 15), dto.getExamDate());
        assertEquals(10L, dto.getStudentId());
        assertEquals("Mario Rossi", dto.getStudentName());
        assertEquals(LocalDateTime.of(2026, 6, 1, 10, 0), dto.getEnrollmentDate());
        assertEquals(EnrollmentStatus.ENROLLED, dto.getStatus());
        assertEquals("Nessuna nota", dto.getNotes());
        assertFalse(dto.getHasGrade());
        assertNull(dto.getGrade());
    }

    @Test
    void toDto_whenHasGrade_mapsGradeCorrectly() {
        Exam exam = new Exam(1L, "Matematica", LocalDate.of(2026, 6, 15), LocalTime.of(9, 0),
                 1L, "Corso di Matematica", 2L, "Prof. Rossi",
                 3L, "Aula 1", 30, LocalDate.now().plusDays(10),
                ExamStatus.SCHEDULED, null);

        ExamEnrollment enrollment = new ExamEnrollment();
        enrollment.setId(1L);
        enrollment.setExam(exam);
        enrollment.setStudentId(10L);
        enrollment.setStatus(EnrollmentStatus.ENROLLED);

        ExamGrade grade = new ExamGrade();
        grade.setGrade(28);
        enrollment.setExamGrade(grade);

        EnrollmentDTO dto = enrollmentConverter.toDto(enrollment);

        assertTrue(dto.getHasGrade());
        assertEquals(28, dto.getGrade());
    }
}
