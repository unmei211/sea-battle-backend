package it.sevenbits.seabattle.core.util.session;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;

@Component
public class SessionStatusFactory {
    HashMap<SessionStatusEnum, SessionStatus> statusMap;

    public SessionStatusFactory() {
        statusMap = new HashMap<>();

        Arrays.stream(SessionStatusEnum.values()).forEach(
                sessionStatusEnum ->
                        statusMap.put(sessionStatusEnum, new SessionStatus(sessionStatusEnum))

        );
    }

    public Optional<SessionStatus> getSessionByStatusEnum(final SessionStatusEnum statusEnum) {
        return Optional.of(statusMap.get(statusEnum));
    }
}
