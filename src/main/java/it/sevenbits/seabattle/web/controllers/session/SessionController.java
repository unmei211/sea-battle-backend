package it.sevenbits.seabattle.web.controllers.session;

import it.sevenbits.seabattle.core.model.cell.Cell;
import it.sevenbits.seabattle.core.model.session.Session;
import it.sevenbits.seabattle.core.service.session.SessionService;
import it.sevenbits.seabattle.core.util.notifier.Notifier;
import it.sevenbits.seabattle.core.util.session.SessionStatusEnum;
import it.sevenbits.seabattle.web.model.Coords;
import it.sevenbits.seabattle.web.model.SessionModel;
import it.sevenbits.seabattle.web.model.ShipArrangement;
import it.sevenbits.seabattle.web.model.StatePullingResponse;
import it.sevenbits.seabattle.web.model.session.SessionPendingDTO;
import it.sevenbits.seabattle.web.model.user.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * session rest controller
 */
@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/session")
@AllArgsConstructor
public class SessionController {

    private SessionService sessionService;
    private Notifier notifier;

    /**
     * Get Session by id
     *
     * @param id - session id
     * @return - session entity
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getSessionData(@PathVariable final Long id) {
        try {
            Session session = sessionService.getById(id).get();
            return new ResponseEntity<>(session, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * calls when player make a turn
     *
     * @param sessionId - session id
     * @param userId    - player id
     * @param coords    - coordinates where player shoot
     * @return - result (shoot or miss)
     */

    @PostMapping("/{sessionId}/turn/{userId}")
    public ResponseEntity<String> makeTurn(
            @PathVariable final Long sessionId,
            @PathVariable final Long userId,
            @RequestBody final Coords coords
    ) {
        try {
            String result = sessionService.makeTurn(sessionId, userId, coords.getAxis(), coords.getOrdinate());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * update session
     *
     * @param id      - session id
     * @param session - session
     */
    @PatchMapping("/{id}")
    public void updateSession(
            @PathVariable final Long id,
            @ModelAttribute(name = "user") final Session session
    ) {
        sessionService.update(id, session);
    }

    /**
     * save session in database
     *
     * @param userDTO - DTO object of user
     */
    @PostMapping
    public ResponseEntity<?> createOrJoinSession(
            @RequestBody final UserDTO userDTO
    ) {
        try {
            Session session = sessionService.getActualSession(userDTO.getId());
            ResponseEntity<SessionPendingDTO> response;
            if (session.getGameState().equals(SessionStatusEnum.STATUS_PENDING.toString())) {
                response = new ResponseEntity<>(Session.toPendingDTO(session), HttpStatus.CREATED);
            } else {
                response = new ResponseEntity<>(Session.toPendingDTO(session), HttpStatus.OK);
            }
            return response;
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    /**
     * delete session by id
     *
     * @param id - session id
     */
    @DeleteMapping("/{id}")
    public void deleteSession(
            @PathVariable final Long id
    ) {
        sessionService.remove(id);
    }

    /**
     * takes the cells of a specific user
     *
     * @param sessionId - session id
     * @param userId    - user id
     * @return - list of cells
     */
    @GetMapping("/{sessionId}/users/{userId}")
    public List<Cell> getUserCells(
            @PathVariable final Long sessionId,
            @PathVariable final Long userId
    ) {
        return sessionService.getUserCells(sessionId, userId);
    }

    // TODO: Будет сокет

    /**
     * to do method calls
     *
     * @param sessionId - session id
     */
    @GetMapping("/{sessionId}/state")
    public ResponseEntity<StatePullingResponse> statePulling(
            @PathVariable final Long sessionId
    ) {
        return new ResponseEntity<>(sessionService.statePulling(sessionId), HttpStatus.OK);
    }

    /**
     * calls when session ended and returns winner
     *
     * @param sessionId - session id
     * @return - winner id
     */
    @GetMapping("{sessionId}/end")
    public ResponseEntity<Long> getWinnerId(
            @PathVariable final Long sessionId
    ) {
        try {
            Long winnerId = sessionService.getWinnerId(sessionId);
            return new ResponseEntity<>(winnerId, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * get arrangement specific user
     *
     * @param sessionId       - session id
     * @param userId          - user id
     * @param shipArrangement - list of ships
     * @return - http status
     */
    @PostMapping("{sessionId}/arrangement/{userId}")
    public ResponseEntity<?> userShipArrangement(
            @PathVariable final Long sessionId,
            @PathVariable final Long userId,
            @RequestBody final ShipArrangement shipArrangement
    ) {
        try {
            if (sessionService.tryArrangement(sessionId, userId, shipArrangement)) {
                return new ResponseEntity<>(HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
