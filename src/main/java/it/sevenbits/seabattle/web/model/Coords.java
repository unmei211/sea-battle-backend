package it.sevenbits.seabattle.web.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coords coords = (Coords) o;
        return axis == coords.axis &&
                ordinate == coords.ordinate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(axis, ordinate);
    }
}
