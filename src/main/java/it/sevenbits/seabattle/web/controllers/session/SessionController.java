package it.sevenbits.seabattle.web.controllers.session;

import it.sevenbits.seabattle.core.model.cell.Cell;
import it.sevenbits.seabattle.core.model.session.Session;
import it.sevenbits.seabattle.core.service.session.SessionService;
import it.sevenbits.seabattle.web.model.SessionModel;
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
    public void makeTurn(
            @PathVariable Long sessionId,
            @PathVariable Long userId,
            @RequestBody int xPos,
            @RequestBody int yPos
    ) {
        sessionService.makeTurn(sessionId, userId, xPos, yPos);
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

}
