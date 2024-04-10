package it.sevenbits.seabattle.core.repository.session;

import it.sevenbits.seabattle.core.model.session.Session;
import it.sevenbits.seabattle.core.model.user.User;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.NamedQuery;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * session repository
 */
import java.util.List;

public interface SessionRepository extends JpaRepository<Session, Long> {
    List<Session> findAllByGameState(String state);

}
