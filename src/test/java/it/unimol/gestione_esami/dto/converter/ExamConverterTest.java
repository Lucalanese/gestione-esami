package it.unimol.gestione_esami.dto.converter;


import it.unimol.gestione_esami.dto.ExamDTO;
import it.unimol.gestione_esami.entity.Exam;
import it.unimol.gestione_esami.enums.ExamStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class ExamConverterTest {

    private final ExamConverter examConverter = new ExamConverter();

    @Test
    void toDto_mapsAllFieldsCorrectly() {

        Exam exam = new Exam(
                1L, "Matematica", LocalDate.of(2025, 6, 15), LocalTime.of(9, 0),
                1L, "Corso di Matematica", 2L, "Prof. Rossi",
                3L, "Aula 1", 30, LocalDate.now().plusDays(10),
                ExamStatus.SCHEDULED, "Nessuna nota"
        );

        ExamDTO dto = examConverter.toDto(exam);

        assertEquals(1L,dto.getId());
        assertEquals("Matematica",dto.getName());
        assertEquals(LocalDate.of(2025, 6, 15),dto.getDate());
        assertEquals(LocalTime.of(9, 0),dto.getTime());
        assertEquals(1L,dto.getCourseId());
        assertEquals("Corso di Matematica",dto.getCourseName());
        assertEquals(2L,dto.getProfessorId());
        assertEquals("Prof. Rossi",dto.getProfessorName());
        assertEquals(3L,dto.getClassroomId());
        assertEquals("Aula 1",dto.getClassroomName());
        assertEquals(30,dto.getMaxStudents());
        assertEquals(LocalDate.now().plusDays(10),dto.getEnrollmentDeadline());
        assertEquals(ExamStatus.SCHEDULED,dto.getStatus());
        assertEquals("Nessuna nota",dto.getNotes());
        assertTrue(dto.getEnrollmentOpen());
    }
}
