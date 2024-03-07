package it.sevenbits.seabattle.core.repository.session;

import it.sevenbits.seabattle.core.model.session.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, Long> {
}
