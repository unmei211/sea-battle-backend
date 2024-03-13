package it.sevenbits.seabattle.core.util.timer;

import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.Timer;
import java.util.TimerTask;

public class GameTimer {
    private Timer timer = new Timer();
    private SimpMessagingTemplate simpMessagingTemplate;

    public GameTimer (final SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
//        simpMessagingTemplate.
    }

    //    public void addTask()
    private class AwaitTurn extends TimerTask {

        @Override
        public void run() {
//            simpMessagingTemplate.convertAndSendToUser("/path/{sessionId}");
        }
    }

}
