package it.sevenbits.seabattle.core.util.timer.tasks.session;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.sevenbits.seabattle.core.service.processing.GameProcessService;
import it.sevenbits.seabattle.core.service.session.SessionService;
import it.sevenbits.seabattle.core.service.user.UserService;
import it.sevenbits.seabattle.core.util.notifier.Notifier;
import it.sevenbits.seabattle.core.util.session.SessionStatusEnum;
import it.sevenbits.seabattle.core.util.session.SessionStatusFactory;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskFactory {
    private final Notifier notifier;
    private final TasksHandler tasks;
    @Setter
    private UserService userService;
    @Setter
    private SessionService sessionService;
    @Setter
    private GameProcessService gameProcessService;

    public TaskFactory(
            final Notifier notifier,
            TasksHandler tasks) {
        this.notifier = notifier;
        this.tasks = tasks;
    }

    public <T> SeaTask createTask(final Long sessionId, final Class<T> clazz) {
        if (clazz.equals(PendingSessionTask.class)) {
            return new PendingSessionTask(
                    tasks,
                    sessionService,
                    sessionId,
                    notifier
            );
        } else if (clazz.equals(ArrangementTask.class)) {
            return new ArrangementTask(tasks, sessionId, sessionService, notifier);
        } else if (clazz.equals(GameProcessTask.class)) {
            return new GameProcessTask(tasks, sessionService, sessionId, notifier, gameProcessService, this, userService);
        } else if (clazz.equals(DeleteSessionTask.class)) {
            return new DeleteSessionTask(tasks, sessionId, sessionService);
        }
        return null;
    }
}
