package it.sevenbits.seabattle.core.model.user;

import it.sevenbits.seabattle.core.model.cell.Cell;
import it.sevenbits.seabattle.core.model.statistic.Statistic;
import it.sevenbits.seabattle.web.model.user.UserDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * user model in database
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private Integer rating;

    @Column
    private String password;

    @Column(unique = true)
    private String login;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Cell> cells;

//    @OneToMany(mappedBy = "session", fetch = FetchType.LAZY)
//    private List<Session> sessions; ;

    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    private Statistic statistic;

    @Override
    public String toString() {
        return "User id: " + id + "\t" + "login: " + login;
    }

    public UserDTO toDTO() {
        return new UserDTO(id, login, rating);
    }
}
