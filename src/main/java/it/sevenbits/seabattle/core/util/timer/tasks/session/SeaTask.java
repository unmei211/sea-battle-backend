package it.sevenbits.seabattle.core.util.timer.tasks.session;

import lombok.Getter;

import java.util.HashMap;
import java.util.TimerTask;

public class SeaTask extends TimerTask {
    private final TasksHandler tasks;
    @Getter
    protected final Long sessionId;

//    @Getter
//    protected final String channel;
    @Getter
    private final Long delayBeforeExecution;
    protected SeaTask(
            final TasksHandler tasks,
            final Long sessionId,
            final Long delayBeforeExecution
    ) {
        super();
        this.tasks = tasks;
        this.sessionId = sessionId;
        this.delayBeforeExecution = delayBeforeExecution;
//        channel = String.format("topic/sea/%d", sessionId);
    }

    @Override
    public boolean cancel() {
        return super.cancel();
    }

    @Override
    public void run() {
        if (tasks.contains(sessionId)) {
            tasks.get(sessionId).cancel();
            tasks.delete(sessionId);
        }
    }
}
