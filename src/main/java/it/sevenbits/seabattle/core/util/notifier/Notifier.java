package it.sevenbits.seabattle.core.util.notifier;

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

    public Notifier(
            final String channelTemplate,
            final SimpMessagingTemplate messagingTemplate,
            final SessionStatusFactory sessionStatusFactory
    ) {
        this.channelTemplate = channelTemplate;
        this.messagingTemplate = messagingTemplate;
        this.sessionStatusFactory = sessionStatusFactory;
    }

    public String getFormattedChannel(Long sessionId) {
        return String.format(channelTemplate, sessionId);
    }

    private void sendJsonMessage(Long sessionId, String message) {
        messagingTemplate.convertAndSend(
                getFormattedChannel(sessionId),
                message
        );
    }

    public void sendSessionPendingReject(Long sessionId) {
        sendJsonMessage(
                sessionId,
                sessionStatusFactory.getSessionStatusJSONByEnum(SessionStatusEnum.STATUS_CANCELED)
        );
    }

    public void sendSessionArrangement(Long sessionId) {
        sendJsonMessage(
                sessionId,
                sessionStatusFactory.getSessionStatusJSONByEnum(SessionStatusEnum.STATUS_ARRANGEMENT)
        );
    }
}

