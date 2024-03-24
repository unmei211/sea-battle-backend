package it.sevenbits.seabattle.web.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * coordinates x and y
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Coords {
    private int axis;
    private int ordinate;
}
