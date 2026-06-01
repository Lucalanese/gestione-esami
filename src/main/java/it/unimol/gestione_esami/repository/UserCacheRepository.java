package it.unimol.gestione_esami.repository;

import it.unimol.gestione_esami.entity.UserCache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCacheRepository extends JpaRepository<UserCache, Long> {
    UserCache findByUserId(Long userId);
}
