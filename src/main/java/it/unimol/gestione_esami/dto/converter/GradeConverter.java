package it.unimol.gestione_esami.dto.converter;

import it.unimol.gestione_esami.dto.GradeDTO;
import it.unimol.gestione_esami.entity.ExamGrade;
import it.unimol.gestione_esami.entity.UserCache;
import it.unimol.gestione_esami.repository.UserCacheRepository;
import org.springframework.stereotype.Component;

@Component
public class GradeConverter {

    private final UserCacheRepository userCacheRepository;

    public GradeConverter(UserCacheRepository userCacheRepository) {
        this.userCacheRepository = userCacheRepository;
    }

    public GradeDTO toDto(ExamGrade examGrade) {
        GradeDTO dto = new GradeDTO();
        dto.setId(examGrade.getId());
        dto.setEnrollmentId(examGrade.getExamEnrollment().getId());
        dto.setExamId(examGrade.getExamEnrollment().getExam().getId());
        dto.setExamName(examGrade.getExamEnrollment().getExam().getName());
        dto.setStudentId(examGrade.getExamEnrollment().getStudentId());
        UserCache student = userCacheRepository.findByUserId(examGrade.getExamEnrollment().getStudentId());
        dto.setStudentName(student != null ? student.getFullName()
                : "Studente " + examGrade.getExamEnrollment().getStudentId());
        dto.setGrade(examGrade.getGrade());
        dto.setHonors(examGrade.getHonors());
        dto.setStatus(examGrade.getStatus());
        dto.setEvaluationDate(examGrade.getEvaluationDate());
        dto.setProfessorId(examGrade.getProfessorId());
        UserCache professor = userCacheRepository.findByUserId(examGrade.getProfessorId());
        dto.setProfessorName(professor != null ? professor.getFullName() : "Prof. " + examGrade.getProfessorId());
        dto.setFeedback(examGrade.getFeedback());
        dto.setPublishedDate(examGrade.getPublishedDate());
        dto.setFormattedGrade(examGrade.getGrade() != null
                ? (examGrade.getGrade() == 30 && Boolean.TRUE.equals(examGrade.getHonors()) ? "30L" : examGrade.getGrade().toString())
                : "N/A");
        return dto;
    }
}
