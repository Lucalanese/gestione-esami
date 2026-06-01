package it.unimol.gestione_esami.dto.converter;

import it.unimol.gestione_esami.dto.EnrollmentDTO;
import it.unimol.gestione_esami.entity.ExamEnrollment;
import org.springframework.stereotype.Component;

@Component
public class EnrollmentConverter {

    public EnrollmentDTO toDto(ExamEnrollment examEnrollment){
        EnrollmentDTO dto = new EnrollmentDTO();
        dto.setId(examEnrollment.getId());
        dto.setExamId(examEnrollment.getExam().getId());
        dto.setExamName(examEnrollment.getExam().getName());
        dto.setExamDate(examEnrollment.getExam().getDate());
        dto.setStudentId(examEnrollment.getStudentId());
        dto.setStudentName(examEnrollment.getStudentName());
        dto.setEnrollmentDate(examEnrollment.getEnrollmentDate());
        dto.setStatus(examEnrollment.getStatus());
        dto.setNotes(examEnrollment.getNotes());
        dto.setHasGrade(examEnrollment.getExamGrade() != null);
        dto.setGrade(examEnrollment.getExamGrade() != null ? examEnrollment.getExamGrade().getGrade() : null);
        return dto;
    }
}
