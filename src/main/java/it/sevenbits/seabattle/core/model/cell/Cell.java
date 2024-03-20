package it.sevenbits.seabattle.core.model.cell;

import it.sevenbits.seabattle.core.model.session.Session;
import it.sevenbits.seabattle.core.model.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Cell model for database
 */
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
    private Session session;

    @Column(name = "is_shot_down")
    private boolean isShotDown;

    @Column
    private int axis;

    @Column
    private int ordinate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private boolean containsShip;
}
