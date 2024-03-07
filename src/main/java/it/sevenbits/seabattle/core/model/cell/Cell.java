package it.sevenbits.seabattle.core.model.cell;

import it.sevenbits.seabattle.core.model.session.Session;
import it.sevenbits.seabattle.core.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cell")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cell {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id")
    Session session;

    @Column(name = "is_shot_down")
    boolean isShotDown;

    @Column(name = "x_pos")
    int xPos;

    @Column(name = "y_pos")
    int yPos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;

    @Column
    boolean containsShip;
}
