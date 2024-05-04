package it.sevenbits.seabattle.core.util.timer.tasks.session;

import it.sevenbits.seabattle.core.service.session.SessionService;
import it.sevenbits.seabattle.core.util.notifier.Notifier;

public class DeleteSessionTask extends SeaTask {
    private final SessionService sessionService;
    private static final Long DELETE_SESSION_TASK_DELAY = 30000L;

    public  DeleteSessionTask(
            TasksHandler tasks,
            Long sessionId,
            SessionService sessionService
    ) {
        super(tasks, sessionId, DELETE_SESSION_TASK_DELAY);
        this.sessionService = sessionService;
    }

    @Override
    public boolean cancel() {
        return super.cancel();
    }

    @Override
    public void run() {
        sessionService.remove(sessionId);
        super.run();
    }
}
