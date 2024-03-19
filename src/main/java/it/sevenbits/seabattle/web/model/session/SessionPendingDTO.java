package it.sevenbits.seabattle.web.model.session;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.sevenbits.seabattle.core.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionPendingDTO {
    private Long id;
    private Timestamp createDate;
    private String gameState;
    private User userFirst;
    private User userSecond;
    private Timestamp arrangementStartDate;
}
