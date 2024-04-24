package it.sevenbits.seabattle.core.util.timer.tasks.session;

import it.sevenbits.seabattle.core.model.user.User;
import it.sevenbits.seabattle.core.service.processing.GameProcessService;
import it.sevenbits.seabattle.core.service.session.SessionService;
import it.sevenbits.seabattle.core.service.user.UserService;
import it.sevenbits.seabattle.core.util.exceptions.NotFoundException;
import it.sevenbits.seabattle.core.util.notifier.Notifier;

public class GameProcessTask extends SeaTask {
    private static final Long GAME_PROCESS_TASK_DELAY = 3000000000L;
    private final Notifier notifier;
    private final SessionService sessionService;
    private final GameProcessService gameProcessService;
    private final UserService userService;

    private final int RATING = 25;

    public GameProcessTask(
            final TasksHandler tasks,
            final SessionService sessionService,
            final Long sessionId,
            final Notifier notifier,
            final GameProcessService gameProcessService,
            final TaskFactory taskFactory,
            final UserService userService
    ) {
        super(tasks, sessionId, GAME_PROCESS_TASK_DELAY);
        this.sessionService = sessionService;
        this.notifier = notifier;
        this.gameProcessService = gameProcessService;
        this.userService = userService;
    }

    @Override
    public void run() {
        try {
            User currentTurnUser = gameProcessService.getCurrentUser(sessionId);
            User enemyUser = gameProcessService.getEnemy(currentTurnUser.getId(), sessionId);
            sessionService.letEndGame(enemyUser, sessionId);
            userService.changeRating(currentTurnUser.getId(), -RATING);
            notifier.sendSessionEnd(sessionId);
        } finally {
            super.run();
        }
    }
}
