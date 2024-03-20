package it.sevenbits.seabattle.core.util.session;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.sevenbits.seabattle.core.model.session.Session;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


import java.util.HashMap;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Component
public class SessionStatusWebSocketMessage {
    private final HashMap<String, String> sessionStatusJsonMap;
    private final ObjectMapper objectMapper;
    @Autowired
    public SessionStatusWebSocketMessage(final ObjectMapper objectMapper) throws JsonProcessingException {
        sessionStatusJsonMap = new HashMap<>();
        this.objectMapper = objectMapper;

        List<String> statusList = List.of(
                Session.STATUS_GAME,
                Session.STATUS_FINISH,
                Session.STATUS_PENDING,
                Session.STATUS_CANCELED,
                Session.STATUS_ARRANGEMENT
        );
        SessionStatus sessionStatus = new SessionStatus();

        for(String status: statusList) {
            sessionStatus.setStatus(status);
            sessionStatusJsonMap.put(status,objectMapper.writeValueAsString(sessionStatus));
        }
    }
    public String getJson(final String status) {
        return sessionStatusJsonMap.get(status);
    }
}
