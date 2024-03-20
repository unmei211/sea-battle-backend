package it.sevenbits.seabattle.web.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * list of ships
 */
@Getter
@Setter
public class ShipArrangement {
    private List<Coords> ships;

    /**
     * constructor
     */
    public ShipArrangement() {
        ships = new ArrayList<>();
    }
}
