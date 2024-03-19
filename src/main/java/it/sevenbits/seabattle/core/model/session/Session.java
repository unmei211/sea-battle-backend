package it.sevenbits.seabattle.core.model.session;

import it.sevenbits.seabattle.core.model.cell.Cell;
import it.sevenbits.seabattle.core.model.user.User;
import it.sevenbits.seabattle.web.model.session.SessionPendingDTO;
import jakarta.persistence.*;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Column(name = "target_cell_id")
    private Long targetCellId;

    public static final String STATUS_PENDING = "pending";
    public static final String STATUS_ARRANGEMENT = "arrangement";
    public static final String STATUS_GAME = "game";
    public static final String STATUS_FINISH = "finish";

    //    public static <T> T toDto(Class<T> SessionDTO, Session session) {
//
//    }
    public static SessionPendingDTO toPendingDTO(final Session session) {
        SessionPendingDTO sessionPendingDTO = new SessionPendingDTO();
        sessionPendingDTO.setId(session.getId());
        sessionPendingDTO.setCreateDate(session.getCreateDate());
        sessionPendingDTO.setGameState(session.getGameState());
        sessionPendingDTO.setUserFirst(session.getUserFirst());
        sessionPendingDTO.setArrangementStartDate(session.getArrangementStartDate());
        sessionPendingDTO.setUserSecond(session.getUserSecond());
        return sessionPendingDTO;
    }
}
