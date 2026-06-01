package it.unimol.gestione_esami.dto.converter;

import it.unimol.gestione_esami.dto.ExamDTO;
import it.unimol.gestione_esami.entity.Exam;
import org.springframework.stereotype.Component;

@Component
public class ExamConverter {

    public ExamDTO toDto(Exam exam){
        ExamDTO dto = new ExamDTO();
        dto.setId(exam.getId());
        dto.setName(exam.getName());
        dto.setDate(exam.getDate());
        dto.setTime(exam.getTime());
        dto.setCourseId(exam.getCourseId());
        dto.setCourseName(exam.getCourseName());
        dto.setProfessorId(exam.getProfessorId());
        dto.setProfessorName(exam.getProfessorName());
        dto.setClassroomId(exam.getClassroomId());
        dto.setClassroomName(exam.getClassroomName());
        dto.setMaxStudents(exam.getMaxStudents());
        dto.setEnrollmentDeadline(exam.getEnrollmentDeadline());
        dto.setStatus(exam.getStatus());
        dto.setNotes(exam.getNotes());
        dto.setCurrentEnrollments(exam.getCurrentEnrollments());
        dto.setEnrollmentOpen(exam.isEnrollmentOpen());
        return dto;



    }
}
