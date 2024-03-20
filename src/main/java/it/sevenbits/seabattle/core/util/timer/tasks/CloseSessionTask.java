package it.sevenbits.seabattle.core.util.timer.tasks;

import it.sevenbits.seabattle.core.model.session.Session;
import it.sevenbits.seabattle.core.service.session.SessionService;
import it.sevenbits.seabattle.core.util.session.SessionStatus;

import it.sevenbits.seabattle.core.util.session.SessionStatusWebSocketMessage;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.TimerTask;
public class CloseSessionTask extends TimerTask {
    private SessionService sessionService;
    private SimpMessagingTemplate template;
    private SessionStatusWebSocketMessage sessionStatusWebSocketMessage;
    private final Long sessionId;

    public CloseSessionTask(
            final SessionService sessionService,
            final Long sessionId,
            final SimpMessagingTemplate simpMessagingTemplate,
            final SessionStatusWebSocketMessage sessionStatusWebSocketMessage
    ) {
        super();
        this.sessionService = sessionService;
        this.sessionId = sessionId;
        this.template = simpMessagingTemplate;
        this.sessionStatusWebSocketMessage = sessionStatusWebSocketMessage;
    }

    @Override
    public void run() {
        sessionService.remove(sessionId);
        String pending = String.format("/topic/%d/pending", sessionId);
        template.convertAndSend(pending, sessionStatusWebSocketMessage.getJson(Session.STATUS_CANCELED));
    }
}
