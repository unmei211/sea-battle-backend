package it.sevenbits.seabattle.core.util.timer;

import it.sevenbits.seabattle.core.util.timer.tasks.session.GameTask;
import it.sevenbits.seabattle.core.util.timer.tasks.session.SeaTask;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
@AllArgsConstructor
public class GameTimer {
    private Timer timer;
    private HashMap<Long, SeaTask> tasksMap;
    private SimpMessagingTemplate simpMessagingTemplate;

    public void closeTask(Long sessionId) {
        if (tasksMap.containsKey(sessionId)) {
            tasksMap.remove(sessionId).cancel();
        }
    }

    public void addTask(GameTask task, Timestamp shedule) {
        timer.schedule(task, shedule);
        tasksMap.put(task);
    }
}
