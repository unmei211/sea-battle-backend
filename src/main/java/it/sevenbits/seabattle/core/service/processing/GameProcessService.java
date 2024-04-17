package it.sevenbits.seabattle.core.service.processing;

import it.sevenbits.seabattle.core.model.session.Session;
import it.sevenbits.seabattle.core.model.user.User;
import it.sevenbits.seabattle.core.repository.session.SessionRepository;
import it.sevenbits.seabattle.core.service.session.SessionService;
import it.sevenbits.seabattle.core.util.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GameProcessService {
    private final SessionService sessionService;
    private final SessionRepository sessionRepository;

    public User getCurrentUser(final Long sessionId) {
        Session session = sessionRepository.findById(sessionId).orElseThrow(
                () -> new NotFoundException("session not found")
        );
        return session.getTurnUser();
    }

    public User getEnemy(final Long userId, final Long sessionId) {
        Session session = sessionRepository.findById(sessionId).orElseThrow(
                () -> new NotFoundException("session or user not found")
        );

        if (session.getUserSecond().getId().equals(userId)) {
            return session.getUserSecond();
        } else {
            return session.getUserFirst();
        }
    }
}
