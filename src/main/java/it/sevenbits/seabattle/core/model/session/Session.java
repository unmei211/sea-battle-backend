package it.sevenbits.seabattle.core.model.session;

import it.sevenbits.seabattle.core.model.cell.Cell;
import it.sevenbits.seabattle.core.model.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Calendar;
import java.util.Date;
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

    @Column
    private Date date;

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
    private Calendar createDate;

    @Column(name = "arrangement_time")
    private Calendar arrangementTime;

    @Column(name = "start_game_time")
    private Calendar startGameTime;

    @Column(name = "player_turn_start")
    private Calendar playerTurnStart;

    @Column(name = "target_cell_id")
    private Long targetCellId;  
}
