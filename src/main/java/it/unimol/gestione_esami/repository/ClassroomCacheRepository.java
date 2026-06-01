package it.unimol.gestione_esami.repository;

import it.unimol.gestione_esami.entity.ClassroomCache;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassroomCacheRepository extends JpaRepository<ClassroomCache, Long> {
    ClassroomCache findByClassroomId(Long classroomId);
}