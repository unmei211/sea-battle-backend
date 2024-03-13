package it.sevenbits.seabattle.core.service.session;

import it.sevenbits.seabattle.core.model.cell.Cell;
import it.sevenbits.seabattle.core.model.session.Session;
import it.sevenbits.seabattle.core.repository.cell.CellRepository;
import it.sevenbits.seabattle.core.repository.session.SessionRepository;
import it.sevenbits.seabattle.core.service.user.UserService;
import it.sevenbits.seabattle.web.model.SessionModel;
import it.sevenbits.seabattle.web.model.ShipArrangement;
import it.sevenbits.seabattle.web.model.StatePullingRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * session sevice
 */
@AllArgsConstructor
@Service
public class SessionService {

    private final SessionRepository sessionRepository;
    private final CellRepository cellRepository;
    private final UserService userService;

    /**
     * get session by id
     *
     * @param id - session id
     * @return - return session
     */
    public Optional<Session> getById(final Long id) {
        return sessionRepository.findById(id);
    }

    public List<Session> getAll() {
        return null;
    }

    /**
     * remove session by id
     *
     * @param id - session id
     */
    public void remove(final Long id) {

    }

    /**
     * update session
     *
     * @param id                - session id
     * @param objectToBeUpdated - new session
     */

    public void update(final Long id, final Session objectToBeUpdated) {

    }

    /**
     * save session in database
     *
     * @param sessionModel - session model
     */
    public void save(final SessionModel sessionModel) {
        Session session = new Session();
        session.setUserFirst(userService.getById(sessionModel.getUserFirst()).get());
        session.setUserSecond(userService.getById(sessionModel.getUserSecond()).get());
        session.setDate(new Date());
        session.setGameState("Preparing");
        sessionRepository.save(session);
    }

    /**
     * get cells specific user
     *
     * @param playerId  - player id
     * @param sessionId - session id
     * @return list of cells
     */
    public List<Cell> getUserCells(final Long playerId, final Long sessionId) {
//      return cellRepository.findAllByUserIdAndSessionId(playerId, sessionId);
        return null;
    }


    /**
     * calls when player make turn, return result (shoot or miss)
     *
     * @param sessionId - session id
     * @param userId    - user id
     * @param xPos      - x coordinate
     * @param yPos      - y coordinate
     * @return result (may error)
     */
    public String makeTurn(final Long sessionId, final Long userId, final int xPos, final int yPos) {
        Optional<Cell> cell = cellRepository.findCellBySessionIdAndUserIdAndAxisAndOrdinate(sessionId, userId, xPos, yPos);
        if (cell.get().isShotDown()) {
            return "Already attacked";
        }
        cell.get().setShotDown(true);
        cellRepository.save(cell.get());
        Optional<Session> session = sessionRepository.findById(sessionId);
        session.get().setTargetCellId(cell.get().getId());

        if (cell.get().isContainsShip()) {
            return "catch";
        } else {
            return "miss";
        }
    }

    /**
     * state pulling (not use)
     *
     * @param sessionId - session id
     * @return nothing
     */
    public StatePullingRequest statePulling(final Long sessionId) {
        return new StatePullingRequest();
    }

    /**
     * get winner id
     *
     * @param sessionId - session id
     * @return winner id
     */
    public Long getWinnerId(final Long sessionId) {
        Optional<Session> session = sessionRepository.findById(sessionId);
        return session.get().getWinner().getId();
    }

    /**
     * put ships specific user and fill other cells
     *
     * @param sessionId       - session id
     * @param userId          - user id
     * @param shipArrangement - list of ships
     */
    public void putShips(final Long sessionId, final Long userId, final ShipArrangement shipArrangement) {
        final int cellsNumber = 10;
        for (int i = 1; i <= cellsNumber; i++) {
            for (int j = 1; j <= cellsNumber; j++) {

            }
        }
    }
}
