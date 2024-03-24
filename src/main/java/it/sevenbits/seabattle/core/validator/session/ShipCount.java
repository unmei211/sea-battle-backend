package it.sevenbits.seabattle.core.validator.session;

import lombok.Data;

@Data
public class ShipCount {
    private final int SINGLE_DECK = 4;
    private final int DOUBLE_DECK = 3;
    private final int THREE_DECK = 2;
    private final int FOUR_DECK = 1;
}
