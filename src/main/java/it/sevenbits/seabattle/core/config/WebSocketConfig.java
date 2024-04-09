package it.sevenbits.seabattle.core.config;

import it.sevenbits.seabattle.core.service.session.SessionService;
import it.sevenbits.seabattle.core.util.notifier.Notifier;
import it.sevenbits.seabattle.core.util.session.SessionStatusFactory;
import it.sevenbits.seabattle.core.util.timer.tasks.session.TaskFactory;
import it.sevenbits.seabattle.core.util.timer.tasks.session.TasksHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/sea").setAllowedOrigins("http://localhost:5173").withSockJS();
        registry.addEndpoint("/sea").setAllowedOrigins("http://localhost:5174").withSockJS();
    }

    @Bean
    public Notifier gameNotifier(
            SimpMessagingTemplate simpMessagingTemplate,
            SessionStatusFactory sessionStatusFactory
    ) {
        return new Notifier(
                "/topic/sea/%d",
                simpMessagingTemplate,
                sessionStatusFactory
        );
    }
}
