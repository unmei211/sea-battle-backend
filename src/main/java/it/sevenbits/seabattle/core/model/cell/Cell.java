package it.sevenbits.seabattle.core.model.cell;

import it.sevenbits.seabattle.core.model.session.Session;
import it.sevenbits.seabattle.core.model.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cells")
@Getter
@Setter
public class Cell {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cell_id")
    private Long id;

    @ManyToOne
    Session session;

    @Column(name = "is_shot_down")
    boolean isShotDown;

    @ManyToOne
    User user;


    @Column(name = "x_pos")
    int x;

    @Column(name = "y_pos")
    int y;
}
