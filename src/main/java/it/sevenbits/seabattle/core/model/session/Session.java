package it.sevenbits.seabattle.core.model.session;

import it.sevenbits.seabattle.core.model.cell.Cell;
import it.sevenbits.seabattle.core.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

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

    @OneToMany (mappedBy = "session")
    private List<Cell> cells;
}
