package it.sevenbits.seabattle.core.util.timer;

import it.sevenbits.seabattle.core.util.timer.tasks.session.SeaTask;
import it.sevenbits.seabattle.core.util.timer.tasks.session.TasksHandler;

import java.util.Timer;


import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * GameTimer
 */
@Component
public class GameTimer {
    private final Timer timer;
    private final TasksHandler tasks;

    /**
     * GameTimer
     * @param tasksHandler taskHandler
     */
    public GameTimer(final TasksHandler tasksHandler) {
        this.timer = new Timer();
        tasks = tasksHandler;
    }
    /**
     * add Task to timer
     * @param timerTask TimerTask
     * @param sessionId sessionId
     */
    public void addTask(final SeaTask timerTask, final Long sessionId) {
        System.out.println("run time task is: " + new Date(System.currentTimeMillis() + timerTask.getDelayBeforeExecution()));
        timer.schedule(timerTask, timerTask.getDelayBeforeExecution());
        tasks.add(sessionId, timerTask);
    }

    /**
     * Remove and cancel task from timer
     * @param sessionId sessionId
     */
    public void removeTask(final Long sessionId) {
        System.out.println("Bundled task with session Id" + sessionId + " removed");
        tasks.get(sessionId).cancel();
        tasks.delete(sessionId);
    }

    public TasksHandler getTaskHandler() {
        return tasks;
    }
}
