package it.sevenbits.seabattle.core.util.timer.tasks.session;

import it.sevenbits.seabattle.core.service.session.SessionService;
import it.sevenbits.seabattle.core.util.notifier.Notifier;

public class ArrangementTask extends SeaTask {
    private final Long sessionId;
    private final SessionService sessionService;
    private final Notifier notifier;

    private static final Long ARRANGEMENT_TASK_DELAY = 30000L;

    public ArrangementTask(TasksHandler tasks, Long sessionId, SessionService sessionService,
                           Notifier notifier) {
        super(tasks, sessionId, ARRANGEMENT_TASK_DELAY);
        this.sessionId = sessionId;
        this.sessionService = sessionService;
        this.notifier = notifier;
    }

    @Override
    public boolean cancel() {
        super.cancel();
        return true;
    }

    @Override
    public void run() {
        System.out.println("arrangement task reject is running for session: " + sessionId);
        sessionService.arrangementReject(sessionId);
        notifier.sendSessionArrangementReject(sessionId);
        super.run();
    }
}
