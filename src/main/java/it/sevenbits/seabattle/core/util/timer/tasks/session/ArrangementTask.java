package it.sevenbits.seabattle.core.util.timer.tasks.session;

import it.sevenbits.seabattle.core.model.session.Session;
import it.sevenbits.seabattle.core.model.user.User;
import it.sevenbits.seabattle.core.service.session.SessionService;
import it.sevenbits.seabattle.core.util.notifier.Notifier;

public class ArrangementTask extends SeaTask {
    private final Long sessionId;
    private final SessionService sessionService;
    private final Notifier notifier;

    private static final Long ARRANGEMENT_TASK_DELAY = 10000L;

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
        Session session = sessionService.getById(sessionId).get();
        User userFirst = session.getUserFirst();
        User userSecond = session.getUserSecond();
        Boolean firstCells = sessionService.isUserHaveShips(userFirst.getId(), sessionId);
        Boolean secondCells = sessionService.isUserHaveShips(userSecond.getId(), sessionId);
        System.out.println("arrangement task reject is running for session: " + sessionId);
        if (
                sessionService.isUserHaveShips(userFirst.getId(), sessionId) &&
                        sessionService.isUserHaveShips(userSecond.getId(), sessionId)
        ) {
            sessionService.draw(sessionId);
        } else if (!sessionService.isUserHaveShips(userFirst.getId(), sessionId)) {
            sessionService.letEndGame(sessionService.getById(sessionId).get().getUserSecond(), sessionId);
        } else {
            sessionService.letEndGame(sessionService.getById(sessionId).get().getUserFirst(), sessionId);
        }
        notifier.sendSessionArrangementReject(sessionId);
        super.run();
    }
}
