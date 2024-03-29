package it.sevenbits.seabattle.core.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.*;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.List;
import java.util.TimerTask;

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
        registry.addEndpoint("/sea").setAllowedOrigins("http://localhost:63343").withSockJS();
    }

//    ObjectMapper objectMapper;
//    public WebSocketConfig(ObjectMapper objectMapper) {
//        this.objectMapper = objectMapper;
//    }
//    @Override
//    public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
//        MappingJackson2MessageConverter converter =
//                new MappingJackson2MessageConverter();
//        converter.setObjectMapper(this.objectMapper);
//        DefaultContentTypeResolver resolver = new DefaultContentTypeResolver();
//        resolver.setDefaultMimeType(MimeTypeUtils.APPLICATION_JSON);
//        converter.setContentTypeResolver(resolver);
//        messageConverters.add(new SimpleMessageConverter());
//        messageConverters.add(new ByteArrayMessageConverter());
//        messageConverters.add(converter);
//        return false;
//    }
}
