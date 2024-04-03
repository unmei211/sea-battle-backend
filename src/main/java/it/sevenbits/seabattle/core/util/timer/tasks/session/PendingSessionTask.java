package it.sevenbits.seabattle.core.util.timer.tasks.session;

import it.sevenbits.seabattle.core.service.session.SessionService;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.HashMap;

public class PendingSessionTask extends SeaTask {
    private SessionService sessionService;
    private final SimpMessagingTemplate messagingTemplate;
    /**
     * JSON of SessionStatus
     */
    private final String statusIfRun;
    private static final Long PENDING_SESSION_TASK_DELAY = 30000L;

    public PendingSessionTask(
            final TasksHandler tasks,
            final SessionService sessionService,
            final Long sessionId,
            final SimpMessagingTemplate simpMessagingTemplate,
            final String statusIfRun
    ) {
        super(tasks, sessionId, PENDING_SESSION_TASK_DELAY);
        this.messagingTemplate = simpMessagingTemplate;
        this.statusIfRun = statusIfRun;
    }

    @Override
    public boolean cancel() {
        super.cancel();
        return true;
    }

    @Override
    public void run() {
        sessionService.remove(sessionId);
        messagingTemplate.convertAndSend(
                channel,
                statusIfRun
        );
        super.run();
    }
}
