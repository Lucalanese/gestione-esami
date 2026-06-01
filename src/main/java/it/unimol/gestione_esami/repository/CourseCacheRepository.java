package it.unimol.gestione_esami.repository;

import it.unimol.gestione_esami.entity.CourseCache;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseCacheRepository extends JpaRepository<CourseCache, Long> {
    CourseCache findByCourseId(Long courseId);
    CourseCache findByCourseName(String courseName);
    CourseCache findByCourseCode(String courseCode);
}
