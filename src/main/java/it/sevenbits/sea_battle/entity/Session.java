package it.sevenbits.sea_battle.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Session")
@Getter
@Setter
public class Session {

    @Id
    private Long id;

    private Long winnerId;
    private Long turnPlayerId;
    private String gameState;
    private Date date;
    @OneToMany (mappedBy = "session")
    List<Cell> cells;
}
