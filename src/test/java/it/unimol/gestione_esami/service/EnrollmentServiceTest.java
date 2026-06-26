package it.unimol.gestione_esami.service;

import it.unimol.gestione_esami.dto.EnrollmentDTO;
import it.unimol.gestione_esami.dto.converter.EnrollmentConverter;
import it.unimol.gestione_esami.dto.request.EnrollmentRequest;
import it.unimol.gestione_esami.entity.Exam;
import it.unimol.gestione_esami.entity.ExamEnrollment;
import it.unimol.gestione_esami.enums.EnrollmentStatus;
import it.unimol.gestione_esami.enums.ExamStatus;
import it.unimol.gestione_esami.exception.BusinessException;
import it.unimol.gestione_esami.exception.ResourceNotFoundException;
import it.unimol.gestione_esami.messaging.ExamEventPublisher;
import it.unimol.gestione_esami.repository.ExamEnrollmentRepository;
import it.unimol.gestione_esami.repository.ExamRepository;
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
class EnrollmentServiceTest {

    @Mock
    private ExamEnrollmentRepository enrollmentRepository;

    @Mock
    private ExamRepository examRepository;

    @Mock
    private EnrollmentConverter enrollmentConverter;

    @Mock
    private ExamEventPublisher eventPublisher;

    @InjectMocks
    private EnrollmentService enrollmentService;

    @Test
    void enrollToExam_whenExamNotFound_throwsResourceNotFoundException() {

        when(examRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> enrollmentService.enrollToExam(99L, 1L, new EnrollmentRequest()));
        verify(enrollmentRepository, never()).save(any());
    }

    @Test
    void enrollToExam_whenStudentAlreadyEnrolled_throwsBusinessException() {

        Exam exam = new Exam(1L, "Matematica", LocalDate.now(), LocalTime.of(9, 0),
                1L, "Matematica", 1L, "Prof. Rossi",
                1L, "Aula 1", 30, LocalDate.now().plusDays(5),
                ExamStatus.SCHEDULED, null);

        ExamEnrollment existingEnrollment = new ExamEnrollment();
        existingEnrollment.setStatus(EnrollmentStatus.ENROLLED);

        when(examRepository.findById(1L)).thenReturn(Optional.of(exam));
        when(enrollmentRepository.findByExamAndStudentId(exam, 1L))
                .thenReturn(List.of(existingEnrollment));

        assertThrows(BusinessException.class,
                () -> enrollmentService.enrollToExam(1L, 1L, new EnrollmentRequest()));
        verify(enrollmentRepository, never()).save(any());
    }

    @Test
    void enrollToExam_whenExamIsFull_throwsBusinessException() {

        Exam exam = new Exam(1L, "Matematica", LocalDate.now(), LocalTime.of(9, 0),
                1L, "Matematica", 1L, "Prof. Rossi",
                1L, "Aula 1", 1, LocalDate.now().plusDays(5),
                ExamStatus.SCHEDULED, null);

        ExamEnrollment existingEnrollment = new ExamEnrollment();
        existingEnrollment.setStatus(EnrollmentStatus.ENROLLED);
        exam.setEnrollments(List.of(existingEnrollment));

        when(examRepository.findById(1L)).thenReturn(Optional.of(exam));
        when(enrollmentRepository.findByExamAndStudentId(exam, 2L)).thenReturn(List.of());

        assertThrows(BusinessException.class,
                () -> enrollmentService.enrollToExam(1L, 2L, new EnrollmentRequest()));
        verify(enrollmentRepository, never()).save(any());
    }

    @Test
    void enrollToExam_whenExamCancelled_throwsBusinessException() {

        Exam exam = new Exam(1L, "Matematica", LocalDate.now(), LocalTime.of(9, 0),
                1L, "Matematica", 1L, "Prof. Rossi",
                1L, "Aula 1", 30, LocalDate.now().plusDays(5),
                ExamStatus.CANCELLED, null);

        when(examRepository.findById(1L)).thenReturn(Optional.of(exam));
        when(enrollmentRepository.findByExamAndStudentId(exam, 1L)).thenReturn(List.of());

        assertThrows(BusinessException.class,
                () -> enrollmentService.enrollToExam(1L, 1L, new EnrollmentRequest()));
        verify(enrollmentRepository, never()).save(any());
    }

    @Test
    void cancelEnrollment_whenStudentCancelsOtherStudentEnrollment_throwsBusinessException() {

        ExamEnrollment enrollment = new ExamEnrollment();
        enrollment.setStudentId(2L);

        when(enrollmentRepository.findById(1L)).thenReturn(Optional.of(enrollment));

        assertThrows(BusinessException.class, () -> enrollmentService.cancelEnrollment(1L, 1L, true));
        verify(enrollmentRepository, never()).deleteById(any());
    }

    @Test
    void getStudentEnrollments_whenStudentRequestsOtherEnrollments_throwsBusinessException() {

        assertThrows(BusinessException.class, () -> enrollmentService.getStudentsEnrollments(2L, 1L, true));
    }

    @Test
    void enrollToExam_whenAllConditionsMet_savesEnrollmentSuccessfully() {
        Exam exam = new Exam(1L, "Matematica", LocalDate.now(), LocalTime.of(9, 0),
                1L, "Matematica", 1L, "Prof. Rossi",
                1L, "Aula 1", 30, LocalDate.now().plusDays(5),
                ExamStatus.SCHEDULED, null);

        EnrollmentRequest request = new EnrollmentRequest("Nessuna nota");

        ExamEnrollment savedEnrollment = new ExamEnrollment();
        savedEnrollment.setStudentId(1L);
        savedEnrollment.setStatus(EnrollmentStatus.ENROLLED);

        EnrollmentDTO expectedDTO = new EnrollmentDTO();
        expectedDTO.setStudentId(1L);
        expectedDTO.setStatus(EnrollmentStatus.ENROLLED);

        when(examRepository.findById(1L)).thenReturn(Optional.of(exam));
        when(enrollmentRepository.findByExamAndStudentId(exam, 1L)).thenReturn(List.of());
        when(enrollmentRepository.save(any(ExamEnrollment.class))).thenReturn(savedEnrollment);
        when(enrollmentConverter.toDto(savedEnrollment)).thenReturn(expectedDTO);

        EnrollmentDTO result = enrollmentService.enrollToExam(1L, 1L, request);

        assertNotNull(result);
        assertEquals(1L, result.getStudentId());
        assertEquals(EnrollmentStatus.ENROLLED, result.getStatus());
        verify(enrollmentRepository, times(1)).save(any(ExamEnrollment.class));
        verify(eventPublisher, times(1)).publishEnrollmentRequested(expectedDTO);
    }
}
