package it.sevenbits.seabattle.core.util.timer.tasks.session;

import it.sevenbits.seabattle.core.service.session.SessionService;
import it.sevenbits.seabattle.core.util.notifier.Notifier;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.HashMap;

public class PendingSessionTask extends SeaTask {
    private final SessionService sessionService;
    private static final Long PENDING_SESSION_TASK_DELAY = 90000L;
    private final Notifier notifier;

    public PendingSessionTask(
            final TasksHandler tasks,
            final SessionService sessionService,
            final Long sessionId,
            final Notifier notifier
    ) {
        super(tasks, sessionId, PENDING_SESSION_TASK_DELAY);
        this.notifier = notifier;
        this.sessionService = sessionService;
    }

    @Override
    public boolean cancel() {
        super.cancel();
        return true;
    }

    @Override
    public void run() {
        System.out.println("pending task reject is running for session: " + sessionId);
        sessionService.remove(sessionId);
        notifier.sendSessionPendingReject(sessionId);
        super.run();
    }
}
