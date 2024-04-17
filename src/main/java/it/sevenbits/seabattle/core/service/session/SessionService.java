package it.sevenbits.seabattle.core.service.session;

import it.sevenbits.seabattle.core.model.cell.Cell;
import it.sevenbits.seabattle.core.model.session.Session;
import it.sevenbits.seabattle.core.model.user.User;
import it.sevenbits.seabattle.core.repository.cell.CellRepository;
import it.sevenbits.seabattle.core.repository.session.SessionRepository;
import it.sevenbits.seabattle.core.repository.user.UserRepository;
import it.sevenbits.seabattle.core.service.processing.GameProcessService;
import it.sevenbits.seabattle.core.service.user.UserService;
import it.sevenbits.seabattle.core.util.exceptions.NotFoundException;
import it.sevenbits.seabattle.core.util.notifier.Notifier;
import it.sevenbits.seabattle.core.util.session.SessionStatusEnum;
import it.sevenbits.seabattle.core.util.session.SessionStatusFactory;
import it.sevenbits.seabattle.core.util.timer.GameTimer;
import it.sevenbits.seabattle.core.util.timer.tasks.session.*;
import it.sevenbits.seabattle.core.validator.session.ArrangementValidator;
import it.sevenbits.seabattle.web.model.Coords;
import it.sevenbits.seabattle.web.model.SessionModel;
import it.sevenbits.seabattle.web.model.ShipArrangement;
import it.sevenbits.seabattle.web.model.StatePullingResponse;
import lombok.AllArgsConstructor;

import java.sql.Timestamp;
import java.util.*;

import static java.util.UUID.randomUUID;

/**
 * session service
 */
@AllArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;
    private final GameTimer gameTimer;
    private final CellRepository cellRepository;
    private final UserService userService;
    private final ArrangementValidator arrangementValidator;
    private final TaskFactory taskFactory;
    private final Notifier notifier;
    private final GameProcessService gameProcessService;

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
        List<Session> sessions = sessionRepository.findAllByGameState(SessionStatusEnum.STATUS_PENDING.toString());
        if (sessions.isEmpty()) {
            Session session = createSession(userId);
            gameTimer.addTask(taskFactory.createTask(session.getId(), PendingSessionTask.class), session.getId());
            return session;
        } else {
            Optional<User> userSecond = userService.getById(userId);
            Session actualSession = sessions.get(0);
            actualSession.setUserSecond(userSecond.get());
            actualSession.setGameState(SessionStatusEnum.STATUS_ARRANGEMENT.toString());
            Date date = new Date();
            Timestamp timestamp = new Timestamp(date.getTime());
            actualSession.setArrangementStartDate(timestamp);
            gameTimer.removeTask(actualSession.getId());
            gameTimer.addTask(taskFactory.createTask(actualSession.getId(), ArrangementTask.class), actualSession.getId());
            notifier.sendSessionArrangementSuccess(actualSession.getId());

            return sessionRepository.save(actualSession);
        }
    }

    public void arrangementReject(final Long sessionId) {
        Session session = sessionRepository.findById(sessionId).get();
        User userFirst = session.getUserFirst();
        User userSecond = session.getUserSecond();
        cellRepository.findCellBySessionIdAndUserId(sessionId, userFirst.getId());
        cellRepository.findCellBySessionIdAndUserId(sessionId, userSecond.getId());

        remove(sessionId);
    }

    private Session createSession(final Long userId) {
        Session session = new Session();
        Date currentDate = new Date();
        Timestamp timeStamp = new Timestamp(currentDate.getTime());
        Optional<User> firstUser = userService.getById(userId);
        session.setUserFirst(firstUser.get());
        session.setCreateDate(timeStamp);
        session.setGameState(SessionStatusEnum.STATUS_PENDING.toString());
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
        sessionRepository.deleteById(id);
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
        String response = "";

        if (cell.isPresent() && cell.get().isShotDown()) {
            response = "Already attacked";
            return response;
        }

        if (cell.isEmpty()) {
            Cell newCell = new Cell();
            newCell.setSession(sessionRepository.findById(sessionId).get());
            newCell.setAxis(xPos);
            newCell.setOrdinate(yPos);
            newCell.setUser(userService.getById(userId).get());
            newCell.setShotDown(true);
            newCell.setContainsShip(false);
            cellRepository.save(newCell);
            response = "miss";
        }

        cell.get().setShotDown(true);
        cellRepository.save(cell.get());
        Optional<Session> session = sessionRepository.findById(sessionId);
        session.get().setTargetCellId(cell.get().getId());

        if (response.isEmpty()) {
            response = "catch";
            boolean killed = true;
            for (Cell c : cellRepository.findAllByShipId(cell.get().getShipId())) {
                if (!c.isShotDown()) {
                    killed = false;
                    break;
                }
            }
            if (killed) {
                response = "killed";
            }
        }
        User nextTurnedUser = getNextTurnedUser(session.get());

        if (cellRepository.existsCellByUserIdAndSessionIdAndIsShotDownFalse(nextTurnedUser.getId(), sessionId)) {
            session.get().setTurnUser(nextTurnedUser);
            gameTimer.removeTask(sessionId);
            gameTimer.addTask(taskFactory.createTask(sessionId, GameProcessTask.class), sessionId);
        } else {
            session.get().setWinner(userService.getById(userId).get());
            session.get().setGameState(SessionStatusEnum.STATUS_FINISH.toString());
            gameTimer.removeTask(sessionId);
            gameTimer.addTask(taskFactory.createTask(sessionId, DeleteSessionTask.class), sessionId);
            notifier.sendSessionEnd(sessionId);
        }
        sessionRepository.save(session.get());

        System.out.println(session.get().getTurnUser().getId() + " - user turn");

        return response;
    }

    public User getNextTurnedUser(final Session session) {
        User currentTurnedUser = session.getTurnUser();
        User first = session.getUserFirst();
        User second = session.getUserSecond();

        if (first.getId().equals(currentTurnedUser.getId())) {
            return second;
        } else {
            return first;
        }
    }

    /**
     * state pulling (not use)
     *
     * @param sessionId - session id
     * @return nothing
     */
    public StatePullingResponse statePulling(final Long sessionId) {
        return new StatePullingResponse(sessionRepository.findById(sessionId).get().getGameState());
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

    private User getRandomTurnedUser(
            final User userFirst,
            final User userSecond
    ) {
        Random random = new Random();
        if (Math.abs(random.nextInt(2)) == 0) {
            return userFirst;
        } else {
            return userSecond;
        }
    }


    public boolean tryArrangement(
            final Long sessionId,
            final Long userId,
            final ShipArrangement shipArrangement
    ) {
        Session session = sessionRepository.findById(sessionId).get();

        if (!putShips(session, userId, shipArrangement)) {
            return false;
        }

        User userFirst = session.getUserFirst();
        User userSecond = session.getUserSecond();

        List<Cell> userFirstCells = cellRepository.findCellBySessionIdAndUserId(sessionId, userFirst.getId());
        List<Cell> userSecondCells = cellRepository.findCellBySessionIdAndUserId(sessionId, userSecond.getId());

        if (!userFirstCells.isEmpty() && !userSecondCells.isEmpty()) {
            session.setGameState(SessionStatusEnum.STATUS_GAME.toString());
            User turnedUser = getRandomTurnedUser(userFirst, userSecond);
            session.setTurnUser(turnedUser);
            Date currentDate = new Date();
            session.setPlayerTurnStartDate(new Timestamp(currentDate.getTime()));
            sessionRepository.save(session);
            gameTimer.removeTask(sessionId);
            notifier.sendSessionGame(sessionId);
        }

        return true;
    }

    /**
     * put ships specific user and fill other cells
     *
     * @param userId          - user id
     * @param shipArrangement - list of ships
     * @return bool - true or false
     */
    public boolean putShips(final Session session, final Long userId, final ShipArrangement shipArrangement) {
        Optional<User> user = userService.getById(userId);
        if (arrangementValidator.validate(shipArrangement)) {
            for (List<Coords> coordsList : arrangementValidator.makeShips(shipArrangement)) {
                String currentShipUUID = randomUUID().toString();
                for (Coords coords : coordsList) {
                    Cell cell = new Cell();
                    cell.setSession(session);
                    cell.setUser(user.get());
                    cell.setAxis(coords.getAxis());
                    cell.setOrdinate(coords.getOrdinate());
                    cell.setContainsShip(true);
                    cell.setShotDown(false);
                    cell.setShipId(currentShipUUID);
                    cellRepository.save(cell);
                }
            }
            return true;
        }
        return false;
    }

    public Session letEndGame(
            final User winner,
            final Long sessionId
    ) {
        gameTimer.removeTask(sessionId);
        Session session = sessionRepository.findById(sessionId).orElseThrow(
                () -> new NotFoundException("session not found")
        );
        session.setGameState(SessionStatusEnum.STATUS_FINISH.toString());
        session.setWinner(winner);

        gameTimer.addTask(
                taskFactory.createTask(sessionId, DeleteSessionTask.class),
                sessionId
        );

        return sessionRepository.save(session);
    }
}
