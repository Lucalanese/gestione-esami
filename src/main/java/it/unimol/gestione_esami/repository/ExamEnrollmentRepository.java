package it.unimol.gestione_esami.repository;

import it.unimol.gestione_esami.entity.Exam;
import it.unimol.gestione_esami.entity.ExamEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamEnrollmentRepository extends JpaRepository<ExamEnrollment, Long> {

    List<ExamEnrollment> findByStudentId(Long studentId);
    List<ExamEnrollment> findByExam(Exam exam);
    List<ExamEnrollment> findByExamAndStudentId(Exam exam, Long studentId);
}
