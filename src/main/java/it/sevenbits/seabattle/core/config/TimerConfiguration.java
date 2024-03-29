package it.sevenbits.seabattle.core.config;

import it.sevenbits.seabattle.core.util.timer.GameTimer;
import it.sevenbits.seabattle.core.util.timer.tasks.session.SeaTask;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.HashMap;
import java.util.Timer;

@Configuration
public class TimerConfiguration {
    @Bean
    public HashMap<Long, SeaTask> tasksMap() {
        return new HashMap<>();
    }

    @Bean
    public GameTimer gameTimer(final SimpMessagingTemplate simpMessagingTemplate) {
        return new GameTimer(new Timer(), tasksMap() ,simpMessagingTemplate);
    }

}
