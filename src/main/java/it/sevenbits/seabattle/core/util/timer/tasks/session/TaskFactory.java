package it.sevenbits.seabattle.core.util.timer.tasks.session;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.sevenbits.seabattle.core.service.session.SessionService;
import it.sevenbits.seabattle.core.util.session.SessionStatusEnum;
import it.sevenbits.seabattle.core.util.session.SessionStatusFactory;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class TaskFactory {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final TasksHandler tasks;
    private final SessionStatusFactory sessionStatusFactory;

    public <T> SeaTask createTask(final Long sessionId, final Class<T> clazz, SessionService sessionService) {
        if (clazz.equals(PendingSessionTask.class)) {
            return new PendingSessionTask(
                    tasks,
                    sessionService,
                    sessionId,
                    simpMessagingTemplate,
                    sessionStatusFactory.getSessionStatusJSONByEnum(SessionStatusEnum.STATUS_CANCELED)
            );
        }
        return null;
    }
}
