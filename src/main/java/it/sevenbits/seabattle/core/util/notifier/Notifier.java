package it.sevenbits.seabattle.core.util.notifier;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.sevenbits.seabattle.core.util.session.SessionStatus;
import it.sevenbits.seabattle.core.util.session.SessionStatusEnum;
import it.sevenbits.seabattle.core.util.session.SessionStatusFactory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

public class Notifier {
    private final String channelTemplate;
    private final SimpMessagingTemplate messagingTemplate;
    private final SessionStatusFactory sessionStatusFactory;
    private final ObjectMapper objectMapper;

    public Notifier(
            final String channelTemplate,
            final SimpMessagingTemplate messagingTemplate,
            final SessionStatusFactory sessionStatusFactory,
            final ObjectMapper objectMapper
    ) {
        this.channelTemplate = channelTemplate;
        this.messagingTemplate = messagingTemplate;
        this.sessionStatusFactory = sessionStatusFactory;
        this.objectMapper = objectMapper;
    }

    public String getFormattedChannel(Long sessionId) {
        return String.format(channelTemplate, sessionId);
    }

    private void sendJsonMessage(Long sessionId, Object message) {
        String json;
        try {
            json = objectMapper.writeValueAsString(message);
        } catch (Exception e) {
            return;
        }
        messagingTemplate.convertAndSend(
                getFormattedChannel(sessionId),
                json
        );
    }

    public void sendSessionPendingReject(Long sessionId) {
        sendJsonMessage(
                sessionId,
                sessionStatusFactory.getSessionStatus(SessionStatusEnum.STATUS_CANCELED)
        );
    }

    public void sendSessionArrangementReject(Long sessionId) {
        sendJsonMessage(
                sessionId,
                sessionStatusFactory.getSessionStatus(SessionStatusEnum.STATUS_CANCELED)
        );
    }

    public void sendSessionArrangementSuccess(Long sessionId) {
        sendJsonMessage(
                sessionId,
                sessionStatusFactory.getSessionStatus(SessionStatusEnum.STATUS_ARRANGEMENT)
        );
    }

    public void sendSessionGame(Long sessionId) {
        sendJsonMessage(
                sessionId,
                sessionStatusFactory.getSessionStatus(SessionStatusEnum.STATUS_GAME)
        );
    }

    public void sendUserTurn(Long sessionId) {
        sendJsonMessage(
                sessionId,
                sessionStatusFactory.getSessionStatus(SessionStatusEnum.STATUS_GAME)
        );
    }

    public void sendSessionEnd(Long sessionId) {
        sendJsonMessage(
                sessionId,
                sessionStatusFactory.getSessionStatus(SessionStatusEnum.STATUS_FINISH)
        );
    }
}

