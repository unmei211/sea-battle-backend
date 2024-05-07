package it.sevenbits.seabattle.core.util.timer.tasks.session;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.TimerTask;

/**
 * TasksHandler
 */
@Component
public class TasksHandler {
    private final HashMap<Long, SeaTask> tasks = new HashMap<>();

    /**
     * get task from handler
     * @param sessionId sessionId
     * @return TimerTask
     */
    public TimerTask get(final Long sessionId) {
        return tasks.get(sessionId);
    }

    /**
     * delete from handler by room id
     * @param sessionId sessionId
     */
    public void delete(final Long sessionId) {
        if (tasks.containsKey(sessionId)) {
            tasks.remove(sessionId).cancel();
        }
    }

    public boolean contains(final Long sessionId) {
        return tasks.containsKey(sessionId);
    }

    /**
     * add to tasks handler
     * @param sessionId sessionId
     * @param task task
     */
    public void add(final Long sessionId, final SeaTask task) {
        tasks.put(sessionId, task);
    }
}
