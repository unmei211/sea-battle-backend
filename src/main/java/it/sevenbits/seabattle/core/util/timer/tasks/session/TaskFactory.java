package it.sevenbits.seabattle.core.util.timer.tasks.session;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.sevenbits.seabattle.core.service.session.SessionService;
import it.sevenbits.seabattle.core.util.session.SessionStatusWebSocketMessage;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TaskFactory {
    private final SessionService sessionService;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final SessionStatusWebSocketMessage sessionStatusWebSocketMessage;
    private final ObjectMapper objectMapper;

    public PendingSessionTask createPendingSessionTask(Long sessionId) {
        return new PendingSessionTask(

        );
    }
}
