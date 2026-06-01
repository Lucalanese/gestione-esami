package it.unimol.gestione_esami.controller;


import it.unimol.gestione_esami.dto.EnrollmentDTO;
import it.unimol.gestione_esami.dto.ExamCalendarDTO;
import it.unimol.gestione_esami.dto.ExamDTO;
import it.unimol.gestione_esami.dto.GradeDTO;
import it.unimol.gestione_esami.dto.request.CreateExamRequest;
import it.unimol.gestione_esami.dto.request.CreateGradeRequest;
import it.unimol.gestione_esami.dto.request.UpdateExamRequest;
import it.unimol.gestione_esami.service.EnrollmentService;
import it.unimol.gestione_esami.service.ExamService;
import it.unimol.gestione_esami.service.GradeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/exams")
public class ExamController {

    private final ExamService examService;
    private final GradeService gradeService;
    private final EnrollmentService enrollmentService;

    public ExamController(ExamService examService, GradeService gradeService, EnrollmentService enrollmentService) {
        this.examService = examService;
        this.gradeService = gradeService;
        this.enrollmentService = enrollmentService;
    }

    @GetMapping
    public ResponseEntity<List<ExamDTO>> getAllExams() {
        List<ExamDTO> exams = examService.getAllExams();
        return ResponseEntity.ok(exams);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExamDTO> getExamById(@PathVariable Long id) {
        ExamDTO exam = examService.getExamById(id);
        return ResponseEntity.ok(exam);
    }

    @GetMapping("/{examId}/enrollments")
    public ResponseEntity<List<EnrollmentDTO>> getExamEnrollments(@PathVariable Long examId) {
        List<EnrollmentDTO> enrollments = enrollmentService.getExamEnrollments(examId);
        return ResponseEntity.ok(enrollments);
    }

    @PostMapping
    public ResponseEntity<ExamDTO> createExam(@Valid @RequestBody CreateExamRequest request) {
        ExamDTO created = examService.createExam(request);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExamDTO> updateExam(@PathVariable Long id, @RequestBody UpdateExamRequest request) {
        ExamDTO updated = examService.updateExam(id, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExam(@PathVariable Long id) {
        examService.deleteExam(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<ExamDTO>> getExamsByCourse(@PathVariable Long courseId) {
        List<ExamDTO> exams = examService.getExamsByCourse(courseId);
        return ResponseEntity.ok(exams);
    }

    @GetMapping("/professor/{professorId}")
    public ResponseEntity<List<ExamDTO>> getExamsByProfessor(@PathVariable Long professorId) {
        List<ExamDTO> exams = examService.getExamsByProfessor(professorId);
        return ResponseEntity.ok(exams);
    }

    @GetMapping("/calendar")
    public ResponseEntity<List<ExamCalendarDTO>> getExamCalendar() {
        List<ExamCalendarDTO> exams = examService.getExamCalendar();
        return ResponseEntity.ok(exams);
    }

    @GetMapping("/available")
    public ResponseEntity<List<ExamDTO>> getAvailableExams() {
        List<ExamDTO> exams = examService.getAvailableExams();
        return ResponseEntity.ok(exams);
    }

    @PostMapping("/{examId}/grades")
    public ResponseEntity<GradeDTO> recordGrade(@PathVariable Long examId, @Valid @RequestBody CreateGradeRequest request, Authentication authentication) {
        Long professorId = Long.parseLong(authentication.getName());
        GradeDTO grade = gradeService.recordGrade(professorId, request);
        return ResponseEntity.status(201).body(grade);
    }

}
