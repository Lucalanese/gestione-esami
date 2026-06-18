package it.unimol.gestione_esami.service;

import it.unimol.gestione_esami.dto.EnrollmentDTO;
import it.unimol.gestione_esami.dto.converter.EnrollmentConverter;
import it.unimol.gestione_esami.dto.request.EnrollmentRequest;
import it.unimol.gestione_esami.dto.request.UpdateStatusRequest;
import it.unimol.gestione_esami.entity.Exam;
import it.unimol.gestione_esami.entity.ExamEnrollment;
import it.unimol.gestione_esami.enums.EnrollmentStatus;
import it.unimol.gestione_esami.exception.BusinessException;
import it.unimol.gestione_esami.exception.ResourceNotFoundException;
import it.unimol.gestione_esami.messaging.ExamEventPublisher;
import it.unimol.gestione_esami.repository.ExamEnrollmentRepository;
import it.unimol.gestione_esami.repository.ExamRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class EnrollmentService {
    private final ExamEnrollmentRepository enrollmentRepository;
    private final ExamRepository examRepository;
    private final EnrollmentConverter enrollmentConverter;
    private final ExamEventPublisher eventPublisher;

    public EnrollmentService(ExamEnrollmentRepository enrollmentRepository, ExamRepository examRepository,
                             EnrollmentConverter enrollmentConverter, ExamEventPublisher eventPublisher) {
        this.enrollmentRepository = enrollmentRepository;
        this.examRepository = examRepository;
        this.enrollmentConverter = enrollmentConverter;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public EnrollmentDTO enrollToExam(Long examId, Long studentId, EnrollmentRequest request) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new ResourceNotFoundException("Esame non trovato con questo ID: " + examId));

        List<ExamEnrollment> existing = enrollmentRepository.findByExamAndStudentId(exam, studentId);
        if(!existing.isEmpty()) {
            throw new BusinessException("Lo studente è già iscritto a questo esame");
        }

        if(exam.getCurrentEnrollments() >= exam.getMaxStudents()) {
            throw new BusinessException("Numero massimo di iscrizioni raggiunto per questo esame");
        }

        ExamEnrollment enrollment = new ExamEnrollment();
        enrollment.setExam(exam);
        enrollment.setStudentId(studentId);
        enrollment.setEnrollmentDate(LocalDateTime.now());
        enrollment.setStatus(EnrollmentStatus.ENROLLED);
        enrollment.setNotes(request.getNotes());

        ExamEnrollment saved = enrollmentRepository.save(enrollment);
        eventPublisher.publishEnrollmentRequested(enrollmentConverter.toDto(saved));
        return enrollmentConverter.toDto(saved);

    }

    public List<EnrollmentDTO> getMyEnrollments(Long studentId) {
        return enrollmentRepository.findByStudentId(studentId)
                .stream()
                .map(enrollmentConverter::toDto)
                .collect(Collectors.toList());

    }

    public EnrollmentDTO getEnrollmentById(Long id, Long requesterId, boolean isStudent) {
        ExamEnrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Iscrizione non trovata con questo ID: " + id));

        if(isStudent && !enrollment.getStudentId().equals(requesterId)) {
            throw new BusinessException("Lo studente non può visualizzare iscrizioni di altri studenti");
        }

        return enrollmentConverter.toDto(enrollment);
    }

    public void cancelEnrollment(Long id) {
        enrollmentRepository.deleteById(id);
    }

    public List<EnrollmentDTO> getExamEnrollments(Long examId) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new ResourceNotFoundException("Esame non trovato con questo ID: " + examId));
        return enrollmentRepository.findByExam(exam)
                .stream()
                .map(enrollmentConverter::toDto)
                .collect(Collectors.toList());
    }

    public EnrollmentDTO updateEnrollmentStatus(Long enrollmentId, UpdateStatusRequest request) {
        ExamEnrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Iscrizione non trovata con questo ID: " + enrollmentId));

        enrollment.setStatus(request.getStatus());
        ExamEnrollment saved = enrollmentRepository.save(enrollment);
        return enrollmentConverter.toDto(saved);

    }

    public List<EnrollmentDTO> getAllEnrollments() {
        return enrollmentRepository.findAll()
                .stream()
                .map(enrollmentConverter::toDto)
                .collect(Collectors.toList());
    }

    public List<EnrollmentDTO> getStudentsEnrollments(Long studentId) {
        return enrollmentRepository.findByStudentId(studentId)
                .stream()
                .map(enrollmentConverter::toDto)
                .collect(Collectors.toList());

    }


}
