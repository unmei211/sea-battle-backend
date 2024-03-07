package it.sevenbits.sea_battle.controller;

import it.sevenbits.sea_battle.entity.Cell;
import it.sevenbits.sea_battle.entity.Session;
import it.sevenbits.sea_battle.services.SessionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/session")
@AllArgsConstructor
public class SessionController {

    SessionService sessionService;

    @GetMapping("/{id}")
    public Session GetSessionData(@PathVariable long id) {
        return sessionService.getById(id).get();

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
    ) {
        Session session = new Session();
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
