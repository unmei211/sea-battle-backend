package it.sevenbits.seabattle.core.util.timer;

import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.sql.Timestamp;
import java.util.Timer;
import java.util.TimerTask;

public class GameTimer {
    private Timer timer = new Timer();
    private SimpMessagingTemplate simpMessagingTemplate;

    public GameTimer (final SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    public void addTask(TimerTask task, Timestamp shedule) {
        timer.schedule(task, shedule);
    }

}
