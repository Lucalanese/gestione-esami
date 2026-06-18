package it.unimol.gestione_esami.controller;

import it.unimol.gestione_esami.dto.EnrollmentDTO;
import it.unimol.gestione_esami.dto.request.EnrollmentRequest;
import it.unimol.gestione_esami.dto.request.UpdateStatusRequest;
import it.unimol.gestione_esami.service.EnrollmentService;
import it.unimol.gestione_esami.service.ExamService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;
    private final ExamService examService;

    public EnrollmentController(EnrollmentService enrollmentService, ExamService examService) {
        this.enrollmentService = enrollmentService;
        this.examService = examService;

    }

    @PostMapping("/{examId}/enroll")
    public ResponseEntity<EnrollmentDTO> enrollToExam(@PathVariable Long examId, @RequestBody EnrollmentRequest request, Authentication authentication) {
        Long studentId = Long.parseLong(authentication.getName());
        EnrollmentDTO enrolled = enrollmentService.enrollToExam(examId, studentId, request);
        return ResponseEntity.status(201).body(enrolled);
    }

    @GetMapping("/my")
    public ResponseEntity<List<EnrollmentDTO>> getMyEnrollments(Authentication authentication) {
        Long studentId = Long.parseLong(authentication.getName());
        List<EnrollmentDTO> enrollments = enrollmentService.getMyEnrollments(studentId);
        return ResponseEntity.ok(enrollments);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<EnrollmentDTO>> getStudentsEnrollments(@PathVariable Long studentId) {
        List<EnrollmentDTO> enrollments = enrollmentService.getStudentsEnrollments(studentId);
        return ResponseEntity.ok(enrollments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnrollmentDTO> getEnrollmentById(@PathVariable Long id, Authentication authentication) {
        Long requestId = Long.parseLong(authentication.getName());
        boolean isStudent = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_STUDENT"));
        EnrollmentDTO enrollment = enrollmentService.getEnrollmentById(id, requestId, isStudent);
        return ResponseEntity.ok(enrollment);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelEnrollment(@PathVariable Long id) {
        enrollmentService.cancelEnrollment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{examId}/enrollments")
    public ResponseEntity<List<EnrollmentDTO>> getExamEnrollments(@PathVariable Long examId) {
        List<EnrollmentDTO> enrollments = enrollmentService.getExamEnrollments(examId);
        return ResponseEntity.ok(enrollments);
    }

    @PutMapping("{enrollmentId}/status")
    public ResponseEntity<EnrollmentDTO> updateEnrollmentStatus(@PathVariable Long enrollmentId, @RequestBody UpdateStatusRequest request) {
        EnrollmentDTO updated = enrollmentService.updateEnrollmentStatus(enrollmentId, request);
        return ResponseEntity.ok(updated);
    }

    @GetMapping
    public ResponseEntity<List<EnrollmentDTO>> getAllEnrollments() {
        List<EnrollmentDTO> enrollments = enrollmentService.getAllEnrollments();
        return ResponseEntity.ok(enrollments);
    }


}
