package it.sevenbits.seabattle.core.service.session;

import it.sevenbits.seabattle.core.model.cell.Cell;
import it.sevenbits.seabattle.core.model.session.Session;
import it.sevenbits.seabattle.core.repository.cell.CellRepository;
import it.sevenbits.seabattle.core.repository.session.SessionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class SessionService {

    private final SessionRepository sessionRepository;
    private final CellRepository cellRepository;

    public Optional<Session> getById(Long id) {
        return sessionRepository.findById(id);
    }

    public List<Session> getAll() {
        return null;
    }


    public void remove(Long id) {

    }

    public void remove(Session object) {

    }

    public void update(Long id, Session objectToBeUpdated) {

    }

    public void save(Session objectToSave) {
    }

    public List<Cell> getUserCells(Long playerId, Long sessionId) {
//      return cellRepository.findAllByUserIdAndSessionId(playerId, sessionId);
        return null;
    }
}
