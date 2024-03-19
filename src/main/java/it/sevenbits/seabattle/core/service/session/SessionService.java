package it.sevenbits.seabattle.core.service.session;

import it.sevenbits.seabattle.core.model.cell.Cell;
import it.sevenbits.seabattle.core.model.session.Session;
import it.sevenbits.seabattle.core.model.user.User;
import it.sevenbits.seabattle.core.repository.cell.CellRepository;
import it.sevenbits.seabattle.core.repository.session.SessionRepository;
import it.sevenbits.seabattle.core.service.user.UserService;
import it.sevenbits.seabattle.web.model.SessionModel;
import it.sevenbits.seabattle.web.model.ShipArrangement;
import it.sevenbits.seabattle.web.model.StatePullingRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class SessionService {

    private final SessionRepository sessionRepository;
    private final CellRepository cellRepository;
    private final UserService userService;

    public Optional<Session> getById(Long id) {
        return sessionRepository.findById(id);
    }

    public Session getActualSession(final Long userId) {
        List<Session> sessions = sessionRepository.findAllByGameState(Session.STATUS_PENDING);
        if(sessions.isEmpty()) {
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


    public void remove(Long id) {

    }

    public void remove(Session object) {

    }

    public void update(Long id, Session objectToBeUpdated) {

    }

    public void save(SessionModel sessionModel) {
        Session session = new Session();
        session.setUserFirst(userService.getById(sessionModel.getUserFirst()).get());
        session.setUserSecond(userService.getById(sessionModel.getUserSecond()).get());
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        session.setCreateDate(timestamp);
//        session.setGameState();
        sessionRepository.save(session);
    }

    public List<Cell> getUserCells(Long playerId, Long sessionId) {
//      return cellRepository.findAllByUserIdAndSessionId(playerId, sessionId);
        return null;
    }

    public String makeTurn(Long sessionId, Long userId, int xPos, int yPos) {
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

    public StatePullingRequest statePulling(Long sessionId) {
        return new StatePullingRequest();
    }

    public Long getWinnerId(Long sessionId) {
        Optional<Session> session = sessionRepository.findById(sessionId);
        return session.get().getWinner().getId();
    }

    public void putShips(Long sessionId, Long userId, ShipArrangement shipArrangement) {
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 10; j++) {

            }
        }
    }
}
