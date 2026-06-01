package it.unimol.gestione_esami.repository;

import it.unimol.gestione_esami.entity.Exam;
import it.unimol.gestione_esami.entity.ExamGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExamGradeRepository extends JpaRepository<ExamGrade, Long> {
    List<ExamGrade> findByExamEnrollment_Exam(Exam examEnrollmentExam);

    Optional<ExamGrade> findByExamEnrollmentId(Long enrollmentId);

    List<ExamGrade> findByExamEnrollment_StudentId(Long studentId);

}
