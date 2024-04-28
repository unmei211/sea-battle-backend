package it.sevenbits.seabattle.web.model.session;

import it.sevenbits.seabattle.web.model.Coords;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SessionDataResponse {
    private Long id;
    private Timestamp createData;
    private Long winnerId;
    private Long turnPlayerId;
    private String gameState;
    private Long userFirst;
    private Long userSecond;
    private Timestamp arrangementStartDate;
    private Timestamp startGameDate;
    private Timestamp playerTurnStartDate;
    private Coords playerTurnCoords;
    private Long targetPlayer;
}
