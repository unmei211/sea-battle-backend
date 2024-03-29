package it.sevenbits.seabattle.core.util.timer.tasks.session;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.HashMap;
import java.util.TimerTask;

public class SeaTask extends TimerTask {
    protected final HashMap<Long, SeaTask> tasks;
    protected final Long sessionId;

    protected final String channel;
    @Getter
    protected final Long delayBeforeExecution;
    protected SeaTask(
            final HashMap<Long, SeaTask> tasks,
            final Long sessionId,
            final Long delayBeforeExecution
    ) {
        super();
        this.tasks = tasks;
        this.sessionId = sessionId;
        this.delayBeforeExecution = delayBeforeExecution;
        channel = String.format("topic/sea/%d", sessionId);
    }

    @Override
    public boolean cancel() {
        return super.cancel();
    }

    @Override
    public void run() {
        if (tasks.containsKey(sessionId)) {
            tasks.get(sessionId).cancel();
            tasks.remove(sessionId);
        }
    }
}
