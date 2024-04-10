package it.sevenbits.seabattle.core.util.session;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;

@Component
public class SessionStatusFactory {
    HashMap<SessionStatusEnum, SessionStatus> statusMap;

    public SessionStatusFactory(ObjectMapper objectMapper) {
        statusMap = new HashMap<>();

        Arrays.stream(SessionStatusEnum.values()).forEach(
                sessionStatusEnum ->
                        statusMap.put(sessionStatusEnum, new SessionStatus(sessionStatusEnum))

        );
    }

    public Optional<SessionStatus> getSessionStatus(final SessionStatusEnum statusEnum) {
        return Optional.of(statusMap.get(statusEnum));
    }
}
