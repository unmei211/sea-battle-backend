package it.sevenbits.seabattle.web.controllers.session;

import it.sevenbits.seabattle.core.model.cell.Cell;
import it.sevenbits.seabattle.core.model.session.Session;
import it.sevenbits.seabattle.core.service.session.SessionService;
import it.sevenbits.seabattle.web.model.Coords;
import it.sevenbits.seabattle.web.model.SessionModel;
import it.sevenbits.seabattle.web.model.StatePullingRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/session")
@AllArgsConstructor
public class SessionController {

    SessionService sessionService;


    @GetMapping("/{id}")
    public ResponseEntity<?> GetSessionData(@PathVariable long id) {
        try {
            Session session = sessionService.getById(id).get();
            return new ResponseEntity<>(session, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/{sessionId}/turn/{userId}")
    public ResponseEntity<String> makeTurn(
            @PathVariable Long sessionId,
            @PathVariable Long userId,
            @RequestBody Coords coords
    ) {
        try {
            String result = sessionService.makeTurn(sessionId, userId, coords.getAxis(), coords.getOrdinate());

            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}")
    public void updateSession(
            @PathVariable Long id,
            @ModelAttribute(name = "user") Session session
    ) {
        sessionService.update(id, session);
    }

    @PostMapping
    public void saveSession(
            @RequestBody SessionModel session
    ) {
        sessionService.save(session);
    }

    @DeleteMapping("/{id}")
    public void deleteSession(
            @PathVariable Long id
    ) {
        sessionService.remove(id);
    }

    @GetMapping("/{sessionId}/users/{userId}")
    public List<Cell> getUserCells(
            @PathVariable Long sessionId,
            @PathVariable Long userId
    ) {

        return sessionService.getUserCells(sessionId, userId);
    }

    @GetMapping("/{sessionId}/state")
    public ResponseEntity<StatePullingRequest> statePulling(
            @PathVariable Long sessionId
    ) {
        try {
            StatePullingRequest statePulling = sessionService.statePulling(sessionId);
            return new ResponseEntity<>(statePulling, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
