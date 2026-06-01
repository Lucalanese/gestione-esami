package it.unimol.gestione_esami.controller;

import it.unimol.gestione_esami.dto.GradeDTO;
import it.unimol.gestione_esami.dto.request.CreateGradeRequest;
import it.unimol.gestione_esami.service.GradeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/grades")
public class GradeController {

    private final GradeService gradeService;

    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<GradeDTO> getGradeById(@PathVariable Long id) {
        GradeDTO grade = gradeService.getGradeById(id);
        return ResponseEntity.ok(grade);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GradeDTO> updateGrade(@PathVariable Long id, @RequestBody CreateGradeRequest request) {
        GradeDTO updated = gradeService.updateGrade(id, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrade(@PathVariable Long id) {
        gradeService.deleteGrade(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/my")
    public ResponseEntity<List<GradeDTO>> getMyGrades(Authentication authentication) {
        Long studentId = Long.parseLong(authentication.getName());
        List<GradeDTO> grades = gradeService.getMyGrades(studentId);
        return ResponseEntity.ok(grades);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<GradeDTO>> getStudentGrades(@PathVariable Long studentId) {
        List<GradeDTO> grades = gradeService.getStudentGrades(studentId);
        return ResponseEntity.ok(grades);
    }
}
