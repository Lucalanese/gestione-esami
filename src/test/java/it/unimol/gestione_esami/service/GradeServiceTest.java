package it.unimol.gestione_esami.service;

import it.unimol.gestione_esami.dto.converter.GradeConverter;
import it.unimol.gestione_esami.dto.request.CreateGradeRequest;
import it.unimol.gestione_esami.entity.ExamEnrollment;
import it.unimol.gestione_esami.entity.ExamGrade;
import it.unimol.gestione_esami.exception.BusinessException;
import it.unimol.gestione_esami.exception.ResourceNotFoundException;
import it.unimol.gestione_esami.messaging.ExamEventPublisher;
import it.unimol.gestione_esami.repository.ExamEnrollmentRepository;
import it.unimol.gestione_esami.repository.ExamGradeRepository;
import it.unimol.gestione_esami.repository.ExamRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GradeServiceTest {

    @Mock
    private ExamGradeRepository gradeRepository;

    @Mock
    private ExamEnrollmentRepository enrollmentRepository;

    @Mock
    private GradeConverter gradeConverter;

    @Mock
    private ExamRepository examRepository;

    @Mock
    private ExamEventPublisher eventPublisher;

    @InjectMocks
    private GradeService gradeService;

    @Test
    void getGradeById_whenGradeNotFound_throwsResourceNotFoundException() {

        when(gradeRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> gradeService.getGradeById(99L, 1L, false));
    }

    @Test
    void getGradeById_whenStudentRequestsOtherStudentGrades_throwsBusinessException() {

        ExamEnrollment enrollment = new ExamEnrollment();
        enrollment.setStudentId(2L);

        ExamGrade grade = new ExamGrade();
        grade.setExamEnrollment(enrollment);

        when(gradeRepository.findById(1L)).thenReturn(Optional.of(grade));

        assertThrows(BusinessException.class, () -> gradeService.getGradeById(1L, 1L, true));
    }

    @Test
    void deleteGrade_whenCalledByStudent_throwsBusinessException() {

        ExamGrade grade = new ExamGrade();
        when(gradeRepository.findById(1L)).thenReturn(Optional.of(grade));

        assertThrows(BusinessException.class, () -> gradeService.deleteGrade(1L, 1L, true));
        verify(gradeRepository, never()).deleteById(any());
    }

    @Test
    void deleteGrade_whenCalledByProfessor_deletesSuccessfully() {

        ExamGrade grade = new ExamGrade();
        when(gradeRepository.findById(1L)).thenReturn(Optional.of(grade));

        gradeService.deleteGrade(1L, 1L, false);

        verify(gradeRepository, times(1)).deleteById(1L);
    }

    @Test
    void recordGrade_whenEnrollmentNotFound_throwsResourceNotFoundException() {

        CreateGradeRequest request = new CreateGradeRequest();
        request.setEnrollmentId(99L);

        when(enrollmentRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> gradeService.recordGrade(1L, request));
        verify(gradeRepository, never()).save(any());
    }

    @Test
    void recordGrade_whenHonorsWithoutGrade30_throwsBusinessException() {

        ExamEnrollment enrollment = new ExamEnrollment();
        when(enrollmentRepository.findById(1L)).thenReturn(Optional.of(enrollment));

        CreateGradeRequest request = new CreateGradeRequest();
        request.setEnrollmentId(1L);
        request.setGrade(25);
        request.setHonors(true);

        assertThrows(BusinessException.class,
                () -> gradeService.recordGrade(1L, request));
        verify(gradeRepository, never()).save(any());
    }
}
