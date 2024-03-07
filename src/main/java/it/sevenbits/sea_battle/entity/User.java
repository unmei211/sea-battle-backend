package it.sevenbits.sea_battle.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    public User() {}
    public User(Long rating, String password, String login) {
        this.login = login;
        this.rating = rating;
        this.password = password;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rating")
    private Long rating;

    @Column(name = "password")
    private String password;

    @Column(name = "login", unique = true)
    private String login;

    @Override
    public String toString() {
        return "User id: " + id + "\t" + "login: " + login;
    }
}
