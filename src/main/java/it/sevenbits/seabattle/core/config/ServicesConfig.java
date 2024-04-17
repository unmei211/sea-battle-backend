package it.sevenbits.seabattle.core.config;

import it.sevenbits.seabattle.core.repository.cell.CellRepository;
import it.sevenbits.seabattle.core.repository.session.SessionRepository;
import it.sevenbits.seabattle.core.service.processing.GameProcessService;
import it.sevenbits.seabattle.core.service.session.SessionService;
import it.sevenbits.seabattle.core.service.user.UserService;
import it.sevenbits.seabattle.core.util.notifier.Notifier;
import it.sevenbits.seabattle.core.util.timer.GameTimer;
import it.sevenbits.seabattle.core.util.timer.tasks.session.TaskFactory;
import it.sevenbits.seabattle.core.validator.session.ArrangementValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
public class ServicesConfig extends CorsConfiguration {
    @Bean
    public SessionService sessionService(
            SessionRepository sessionRepository,
            GameTimer gameTimer,
            CellRepository cellRepository,
            UserService userService,
            ArrangementValidator arrangementValidator,
            TaskFactory taskFactory,
            Notifier notifier
    ) {
        SessionService service = new SessionService(
                sessionRepository,
                gameTimer,
                cellRepository,
                userService,
                arrangementValidator,
                taskFactory,
                notifier,
                gameProcessService(sessionRepository, taskFactory)
        );
        taskFactory.setSessionService(service);
        return service;
    }

    @Bean
    public GameProcessService gameProcessService(SessionRepository sessionRepository, TaskFactory taskFactory) {
        GameProcessService gameProcessService = new GameProcessService(sessionRepository);
        taskFactory.setGameProcessService(gameProcessService);
        return gameProcessService;
    }
}
