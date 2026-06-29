package it.unimol.gestione_esami.dto.converter;

import it.unimol.gestione_esami.dto.GradeDTO;
import it.unimol.gestione_esami.entity.Exam;
import it.unimol.gestione_esami.entity.ExamEnrollment;
import it.unimol.gestione_esami.entity.ExamGrade;
import it.unimol.gestione_esami.entity.UserCache;
import it.unimol.gestione_esami.enums.EnrollmentStatus;
import it.unimol.gestione_esami.enums.ExamStatus;
import it.unimol.gestione_esami.enums.GradeStatus;
import it.unimol.gestione_esami.repository.UserCacheRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GradeConverterTest {

    @Mock
    private UserCacheRepository userCacheRepository;

    @InjectMocks
    private GradeConverter converter;

    @Test
    void toDto_mapsAllFieldCorrectly() {
        Exam exam = new Exam(1L, "Matematica", LocalDate.of(2026, 6, 15), LocalTime.of(9, 0),
                1L, "Matematica", 2L, "Prof. Rossi",
                3L, "Aula 1", 30, LocalDate.now().plusDays(10),
                ExamStatus.SCHEDULED, null);

        ExamEnrollment enrollment = new ExamEnrollment();
        enrollment.setId(1L);
        enrollment.setExam(exam);
        enrollment.setStudentId(10L);
        enrollment.setStatus(EnrollmentStatus.ENROLLED);

        ExamGrade grade = new ExamGrade();
        grade.setId(1L);
        grade.setExamEnrollment(enrollment);
        grade.setGrade(28);
        grade.setHonors(false);
        grade.setStatus(GradeStatus.DRAFT);
        grade.setEvaluationDate(LocalDateTime.of(2026, 6, 20, 10, 0));
        grade.setProfessorId(2L);
        grade.setFeedback("Buon lavoro");

        UserCache student = new UserCache();
        student.setFullName("Mario Rossi");

        UserCache professor = new UserCache();
        professor.setFullName("Prof. Bianchi");

        when(userCacheRepository.findByUserId(10L)).thenReturn(student);
        when(userCacheRepository.findByUserId(2L)).thenReturn(professor);

        GradeDTO dto = converter.toDto(grade);

        assertEquals(1L, dto.getId());
        assertEquals(1L, dto.getEnrollmentId());
        assertEquals(1L, dto.getExamId());
        assertEquals("Matematica", dto.getExamName());
        assertEquals(10L, dto.getStudentId());
        assertEquals("Mario Rossi", dto.getStudentName());
        assertEquals(28, dto.getGrade());
        assertFalse(dto.getHonors());
        assertEquals(GradeStatus.DRAFT, dto.getStatus());
        assertEquals(2L, dto.getProfessorId());
        assertEquals("Prof. Bianchi", dto.getProfessorName());
        assertEquals("Buon lavoro", dto.getFeedback());
        assertEquals("28", dto.getFormattedGrade());
    }

    @Test
    void toDto_whenGradeIs30WithHonors_formattedGradeIs30L() {
        Exam exam = new Exam(1L, "Matematica", LocalDate.of(2026, 6, 15), LocalTime.of(9, 0),
                 1L, "Matematica", 2L, "Prof. Rossi",
                 3L, "Aula 1", 30, LocalDate.now().plusDays(10),
                ExamStatus.SCHEDULED, null);

        ExamEnrollment enrollment = new ExamEnrollment();
        enrollment.setId(1L);
        enrollment.setExam(exam);
        enrollment.setStudentId(10L);
        enrollment.setStatus(EnrollmentStatus.ENROLLED);

        ExamGrade grade = new ExamGrade();
        grade.setId(1L);
        grade.setExamEnrollment(enrollment);
        grade.setGrade(30);
        grade.setHonors(true);
        grade.setProfessorId(2L);

        when(userCacheRepository.findByUserId(10L)).thenReturn(null);

        GradeDTO dto = converter.toDto(grade);

        assertEquals("30L", dto.getFormattedGrade());
    }
}
