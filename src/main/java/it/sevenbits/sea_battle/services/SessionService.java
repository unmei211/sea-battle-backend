package it.sevenbits.sea_battle.services;

import it.sevenbits.sea_battle.entity.Cell;
import it.sevenbits.sea_battle.entity.Session;
import it.sevenbits.sea_battle.repository.CellRepository;
import it.sevenbits.sea_battle.repository.SessionRepository;
import it.sevenbits.sea_battle.repository.ShipRepository;
import it.sevenbits.sea_battle.services.interfaces.CrudService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class SessionService implements CrudService<Session> {

    private final SessionRepository sessionRepository;
    private final CellRepository cellRepository;


    @Override
    public Optional<Session> getById(Long id) {

        return sessionRepository.findById(id);
    }

    @Override
    public List<Session> getAll() {
        return null;
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public void remove(Session object) {

    }

    @Override
    public void update(Long id, Session objectToBeUpdated) {

    }

    @Override
    public void save(Session objectToSave) {
    }

    public List<Cell> getUserCells(Long playerId, Long sessionId) {
      return cellRepository.findAllByUserIdAndSessionId(playerId, sessionId);
    }
}
