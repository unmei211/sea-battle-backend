package it.sevenbits.seabattle.core.model.ship;

import jakarta.persistence.*;

@Entity
@Table(name = "SHIPS")
public class Ship {
    private int size;
    @Id
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
