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
    private final ObjectMapper objectMapper;

    public SessionStatusFactory(ObjectMapper objectMapper) {
        statusMap = new HashMap<>();
        this.objectMapper = objectMapper;

        Arrays.stream(SessionStatusEnum.values()).forEach(
                sessionStatusEnum ->
                        statusMap.put(sessionStatusEnum, new SessionStatus(sessionStatusEnum))

        );
    }

    public Optional<SessionStatus> getSessionStatusByEnum(final SessionStatusEnum statusEnum) {
        return Optional.of(statusMap.get(statusEnum));
    }

    public String getSessionStatusJSONByEnum(final SessionStatusEnum statusEnum) {
        String status;
        try {
            status = objectMapper.writeValueAsString(statusMap.get(statusEnum));
        } catch (Exception e) {
            status = String.format("{ status: %s }", SessionStatusEnum.STATUS_UNEXPECTED);
        }
        return status;
    }
}
