package it.sevenbits.seabattle.core.service.session;

import it.sevenbits.seabattle.core.model.cell.Cell;
import it.sevenbits.seabattle.core.model.session.Session;
import it.sevenbits.seabattle.core.model.user.User;
import it.sevenbits.seabattle.core.repository.cell.CellRepository;
import it.sevenbits.seabattle.core.repository.session.SessionRepository;
import it.sevenbits.seabattle.core.service.user.UserService;
import it.sevenbits.seabattle.core.validator.session.ArrangementValidator;
import it.sevenbits.seabattle.web.model.SessionModel;
import it.sevenbits.seabattle.web.model.ShipArrangement;
import it.sevenbits.seabattle.web.model.StatePullingRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * session service
 */
@AllArgsConstructor
@Service
public class SessionService {

    private final SessionRepository sessionRepository;
    private final CellRepository cellRepository;
    private final UserService userService;
    private final ArrangementValidator arrangementValidator;

    /**
     * get session by id
     *
     * @param id - session id
     * @return - return session
     */
    public Optional<Session> getById(final Long id) {
        return sessionRepository.findById(id);
    }

    public Session getActualSession(final Long userId) {
        List<Session> sessions = sessionRepository.findAllByGameState(Session.STATUS_PENDING);
        if (sessions.isEmpty()) {
            //TODO: subscribe to socket
            //TODO: add PendingTaskTimer to timer
            return createSession(userId);
        } else {
            Optional<User> userSecond = userService.getById(userId);
            Session actualSession = sessions.get(0);
            actualSession.setUserSecond(userSecond.get());
            actualSession.setGameState(Session.STATUS_ARRANGEMENT);
            Date date = new Date();
            Timestamp timestamp = new Timestamp(date.getTime());
            actualSession.setArrangementStartDate(timestamp);
            //TODO: remove PendingTaskTimer from timer
            //TODO: notificate FirstUser by messageBroker
            return sessionRepository.save(actualSession);
        }
    }

    private Session createSession(final Long userId) {
        Session session = new Session();
        Date currentDate = new Date();
        Timestamp timeStamp = new Timestamp(currentDate.getTime());
        Optional<User> firstUser = userService.getById(userId);
        session.setUserFirst(firstUser.get());
        session.setCreateDate(timeStamp);
        session.setGameState(Session.STATUS_PENDING);
        return sessionRepository.save(session);
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
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        session.setCreateDate(timestamp);
//        session.setGameState();
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
        System.out.println(arrangementValidator.validateArrangementCount(shipArrangement));
    }
}
