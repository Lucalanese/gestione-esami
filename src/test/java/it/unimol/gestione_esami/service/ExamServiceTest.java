package it.unimol.gestione_esami.service;

import it.unimol.gestione_esami.dto.ExamDTO;
import it.unimol.gestione_esami.dto.converter.ExamCalendarConverter;
import it.unimol.gestione_esami.dto.converter.ExamConverter;
import it.unimol.gestione_esami.entity.Exam;
import it.unimol.gestione_esami.entity.ExamEnrollment;
import it.unimol.gestione_esami.enums.EnrollmentStatus;
import it.unimol.gestione_esami.enums.ExamStatus;
import it.unimol.gestione_esami.exception.BusinessException;
import it.unimol.gestione_esami.exception.ResourceNotFoundException;
import it.unimol.gestione_esami.messaging.ExamEventPublisher;
import it.unimol.gestione_esami.repository.ClassroomCacheRepository;
import it.unimol.gestione_esami.repository.CourseCacheRepository;
import it.unimol.gestione_esami.repository.ExamRepository;
import it.unimol.gestione_esami.repository.UserCacheRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExamServiceTest {

    @Mock
    private ExamRepository examRepository;

    @Mock
    private ExamConverter examConverter;

    @Mock
    private ExamCalendarConverter examCalendarConverter;

    @Mock
    private ExamEventPublisher eventPublisher;

    @Mock
    private CourseCacheRepository courseCacheRepository;

    @Mock
    private ClassroomCacheRepository classroomCacheRepository;

    @Mock
    private UserCacheRepository userCacheRepository;

    @InjectMocks
    private ExamService examService;

    @Test
    void getExamById_whenExamExists_returnsDto() {

        Exam exam = new Exam(1L, "Matematica", LocalDate.now(), LocalTime.of(9, 0), 1L, "Matematica", 1L, "Prof. Rossi", 1L, "Aula 1", 30, LocalDate.now().plusDays(5), ExamStatus.SCHEDULED, null);

        ExamDTO expectedDTO = new ExamDTO();
        expectedDTO.setId(1L);
        expectedDTO.setName("Matematica");

        when(examRepository.findById(1L)).thenReturn(Optional.of(exam));
        when(examConverter.toDto(exam)).thenReturn(expectedDTO);

        ExamDTO result = examService.getExamById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Matematica", result.getName());
        verify(examRepository, times(1)).findById(1L);
    }

    @Test
    void getExamById_whenExamNotFound_throwsResourceNotFoundException() {

        when(examRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> examService.getExamById(99L));
    }

    @Test
    void deleteExam_whenExamHasActiveEnrollments_throwsBusinessException() {

        Exam exam = new Exam(1L, "Matematica", LocalDate.now(), LocalTime.of(9, 0),
                1L, "Matematica", 1L, "Prof. Rossi",
                1L, "Aula 1", 30, LocalDate.now().plusDays(5),
                ExamStatus.SCHEDULED, null);

        ExamEnrollment enrollment = new ExamEnrollment();
        enrollment.setStatus(EnrollmentStatus.ENROLLED);
        exam.setEnrollments(List.of(enrollment));

        when(examRepository.findById(1L)).thenReturn(Optional.of(exam));

        assertThrows(BusinessException.class, () -> examService.deleteExam(1L));
        verify(examRepository, never()).deleteById(any());
    }

    @Test
    void getAllExams_returnListOfDtos() {
        Exam exam1 = new Exam(1L, "Matematica", LocalDate.now(), LocalTime.of(9, 0),
                1L, "Matematica", 1L, "Prof. Rossi",
                1L, "Aula 1", 30, LocalDate.now().plusDays(5),
                ExamStatus.SCHEDULED, null);
        Exam exam2 = new Exam(2L, "Fisica", LocalDate.now(), LocalTime.of(11, 0),
                2L, "Fisica", 2L, "Prof. Bianchi",
                2L, "Aula 2", 25, LocalDate.now().plusDays(3),
                ExamStatus.SCHEDULED, null);

        ExamDTO dto1 = new ExamDTO();
        dto1.setId(1L);
        dto1.setName("Matematica");

        ExamDTO dto2 = new ExamDTO();
        dto2.setId(2L);
        dto2.setName("Fisica");

        when(examRepository.findAll()).thenReturn(List.of(exam1, exam2));
        when(examConverter.toDto(exam1)).thenReturn(dto1);
        when(examConverter.toDto(exam2)).thenReturn(dto2);

        List<ExamDTO> result = examService.getAllExams();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals("Matematica", result.get(0).getName());
        assertEquals("Fisica", result.get(1).getName());
        verify(examRepository, times(1)).findAll();
    }

    @Test
    void deleteExam_whenExamHasNoEnrollments_deletesSuccessfully() {

        Exam exam = new Exam(1L, "Matematica", LocalDate.now(), LocalTime.of(9, 0),
                1L, "Matematica", 1L, "Prof. Rossi",
                1L, "Aula 1", 30, LocalDate.now().plusDays(5),
                ExamStatus.SCHEDULED, null);

        when(examRepository.findById(1L)).thenReturn(Optional.of(exam));

        examService.deleteExam(1L);

        verify(examRepository, times(1)).deleteById(1L);
        verify(eventPublisher, times(1)).publishExamDeleted(1L);
    }
}
