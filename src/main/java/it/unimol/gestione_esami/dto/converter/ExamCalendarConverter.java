package it.unimol.gestione_esami.dto.converter;

import it.unimol.gestione_esami.dto.ExamCalendarDTO;
import it.unimol.gestione_esami.entity.Exam;
import org.springframework.stereotype.Component;

@Component
public class ExamCalendarConverter {
    public ExamCalendarDTO toDto(Exam exam){
        ExamCalendarDTO dto = new ExamCalendarDTO();
        dto.setExamId(exam.getId());
        dto.setCourseCode(exam.getCourseId() != null ? exam.getCourseId().toString() : "N/A");
        dto.setCourseName(exam.getCourseName());
        dto.setProfessorName(exam.getProfessorName());
        dto.setExamDate(exam.getDate());
        dto.setExamTime(exam.getTime());
        dto.setClassroomName(exam.getClassroomName());
        dto.setAvailableSlots(exam.getMaxStudents() - exam.getCurrentEnrollments());
        dto.setStatus(exam.getStatus());
        return dto;
    }
}
