package it.sevenbits.seabattle.core.model.user;

import it.sevenbits.seabattle.core.model.cell.Cell;
import it.sevenbits.seabattle.core.model.session.Session;
import it.sevenbits.seabattle.core.model.statistic.Statistic;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Integer rating;

    @Column
    private String password;

    @Column(unique = true)
    private String login;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Cell> cells;

//    @OneToMany(mappedBy = "session", fetch = FetchType.LAZY)
//    private List<Session> sessions; ;

    @OneToOne(mappedBy = "user")
    private Statistic statistic;

    @Override
    public String toString() {
        return "User id: " + id + "\t" + "login: " + login;
    }
}
