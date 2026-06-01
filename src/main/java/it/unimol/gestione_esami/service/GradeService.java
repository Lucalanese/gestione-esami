package it.unimol.gestione_esami.service;

import it.unimol.gestione_esami.dto.GradeDTO;
import it.unimol.gestione_esami.dto.converter.GradeConverter;
import it.unimol.gestione_esami.dto.request.CreateGradeRequest;
import it.unimol.gestione_esami.entity.Exam;
import it.unimol.gestione_esami.entity.ExamEnrollment;
import it.unimol.gestione_esami.entity.ExamGrade;
import it.unimol.gestione_esami.enums.GradeStatus;
import it.unimol.gestione_esami.exception.ResourceNotFoundException;
import it.unimol.gestione_esami.messaging.ExamEventPublisher;
import it.unimol.gestione_esami.repository.ExamEnrollmentRepository;
import it.unimol.gestione_esami.repository.ExamGradeRepository;
import it.unimol.gestione_esami.repository.ExamRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class GradeService {

    private final ExamGradeRepository gradeRepository;
    private final ExamEnrollmentRepository enrollmentRepository;
    private final GradeConverter gradeConverter;
    private final ExamRepository examRepository;
    private final ExamEventPublisher eventPublisher;

    public GradeService(ExamGradeRepository gradeRepository, ExamEnrollmentRepository enrollmentRepository,
                        GradeConverter gradeConverter, ExamRepository examRepository, ExamEventPublisher eventPublisher) {
        this.gradeRepository = gradeRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.gradeConverter = gradeConverter;
        this.examRepository = examRepository;
        this.eventPublisher = eventPublisher;
    }

    public GradeDTO recordGrade(Long professorId, CreateGradeRequest request) {
        ExamEnrollment enrollment = enrollmentRepository.findById(request.getEnrollmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Iscrizione non trovata per questo esame e studente"));

        ExamGrade grade = new ExamGrade();
        grade.setExamEnrollment(enrollment);
        grade.setGrade(request.getGrade());
        grade.setHonors(request.getHonors());
        grade.setFeedback(request.getFeedback());
        grade.setProfessorId(professorId);
        grade.setEvaluationDate(LocalDateTime.now());
        grade.setStatus(GradeStatus.DRAFT);

        ExamGrade saved = gradeRepository.save(grade);
        eventPublisher.publishGradeRecorded(gradeConverter.toDto(saved));
        return gradeConverter.toDto(saved);

    }

    public GradeDTO getGradeById(Long id) {
        return gradeRepository.findById(id)
                .map(gradeConverter::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("La valutazione non esiste con questo ID: " + id));
    }

    public void deleteGrade(Long id) {
        gradeRepository.deleteById(id);
    }

    public GradeDTO updateGrade(Long id, CreateGradeRequest request) {
        ExamGrade grade = gradeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Voto non trovato con id: " + id));

        grade.setGrade(request.getGrade());
        grade.setHonors(request.getHonors());
        grade.setFeedback(request.getFeedback());

        ExamGrade saved = gradeRepository.save(grade);
        return gradeConverter.toDto(saved);
    }

    public List<GradeDTO> getExamGrades(Long examId) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new ResourceNotFoundException("Esame non trovato con questo ID: " + examId));
        return gradeRepository.findByExamEnrollment_Exam(exam)
                .stream()
                .map(gradeConverter::toDto)
                .toList();
    }

    public List<GradeDTO> getMyGrades(Long studentId) {
        return gradeRepository.findByExamEnrollment_StudentId(studentId)
                .stream()
                .map(gradeConverter::toDto)
                .collect(Collectors.toList());
    }

    public List<GradeDTO> getStudentGrades(Long studentId) {
        return gradeRepository.findByExamEnrollment_StudentId(studentId)
                .stream()
                .map(gradeConverter::toDto)
                .collect(Collectors.toList());
    }

}

