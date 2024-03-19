package it.sevenbits.seabattle.core.repository.session;

import it.sevenbits.seabattle.core.model.session.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Long> {
    List<Session> findAllByGameState(String state);
}
