package it.unimol.gestione_esami.repository;

import it.unimol.gestione_esami.entity.Exam;
import it.unimol.gestione_esami.enums.ExamStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {

    List<Exam> findByCourseId(Long courseId);

    List<Exam> findByProfessorId(Long professorId);

    List<Exam> findByStatus(ExamStatus status);

    List<Exam> findByClassroomId(Long classroomId);


}
