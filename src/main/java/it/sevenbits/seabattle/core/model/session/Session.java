package it.sevenbits.seabattle.core.model.session;

import it.sevenbits.seabattle.core.model.cell.Cell;
import it.sevenbits.seabattle.core.model.user.User;
import it.sevenbits.seabattle.core.service.cell.CellService;
import it.sevenbits.seabattle.web.model.Coords;
import it.sevenbits.seabattle.web.model.session.SessionDataResponse;
import it.sevenbits.seabattle.web.model.session.SessionPendingDTO;
import jakarta.persistence.*;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Session model
 */
@Entity
@Table(name = "session")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "winner_id")
    private User winner;

    @ManyToOne
    @JoinColumn(name = "turn_player_id")
    private User turnUser;

    @Column(name = "game_state")
    private String gameState;

    @ManyToOne
    @JoinColumn(name = "user_first")
    private User userFirst;

    @ManyToOne
    @JoinColumn(name = "user_second")
    private User userSecond;

    @OneToMany(mappedBy = "session")
    private List<Cell> cells;

    @Column(name = "create_date")
    private Timestamp createDate;

    @Column(name = "arrangement_start_date")
    private Timestamp arrangementStartDate;

    @Column(name = "start_game_date")
    private Timestamp startGameDate;

    @Column(name = "player_turn_start_date")
    private Timestamp playerTurnStartDate;

    @Column(name = "target_cell_axis")
    private Integer targetCellAxis;
    @Column(name = "target_cell_ordinatine")
    private Integer targetCellOrdinate;


    public static SessionPendingDTO toPendingDTO(final Session session) {
        SessionPendingDTO sessionPendingDTO = new SessionPendingDTO();
        sessionPendingDTO.setId(session.getId());
        sessionPendingDTO.setCreateDate(session.getCreateDate());
        sessionPendingDTO.setGameState(session.getGameState());
        sessionPendingDTO.setUserFirst(session.getUserFirst().getId());
        sessionPendingDTO.setArrangementStartDate(session.getArrangementStartDate());
        if (session.getUserSecond() != null) {
            sessionPendingDTO.setUserSecond(session.getUserSecond().getId());
        }
        return sessionPendingDTO;
    }

    public SessionDataResponse toDataResponse() {
        SessionDataResponse sessionDataResponse = new SessionDataResponse();
        sessionDataResponse.setId(this.getId());
        sessionDataResponse.setCreateData(this.getCreateDate());
        sessionDataResponse.setWinnerId(this.getWinner() != null ? this.getWinner().getId() : null);
        sessionDataResponse.setTurnPlayerId(this.getTurnUser() != null ? this.getTurnUser().getId() : null);
        sessionDataResponse.setGameState(this.getGameState());
        sessionDataResponse.setUserFirst(this.getUserFirst().getId());
        sessionDataResponse.setUserSecond(this.getUserSecond() != null ? this.getUserSecond().getId() : null);
        sessionDataResponse.setArrangementStartDate(this.getArrangementStartDate());
        sessionDataResponse.setStartGameDate(this.getStartGameDate());
        sessionDataResponse.setPlayerTurnStartDate(this.getPlayerTurnStartDate());
        sessionDataResponse.setPlayerTurnCoords(new Coords(this.getTargetCellAxis(), this.getTargetCellOrdinate()));
        return sessionDataResponse;
    }
}
