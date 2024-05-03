package it.sevenbits.seabattle.core.validator.session;

import it.sevenbits.seabattle.web.model.Coords;
import it.sevenbits.seabattle.web.model.ShipArrangement;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Component
public class ArrangementValidator {
    private HashMap<Integer, Integer> shipMap;

    public ArrangementValidator() {
        shipMap = new HashMap<>();
    }


    private boolean areAdjacent(Coords coord1, Coords coord2) {
        return Math.abs(coord1.getAxis() - coord2.getAxis()) <= 1 && Math.abs(coord1.getOrdinate() - coord2.getOrdinate()) <= 1;
    }

    public List<List<Coords>> makeShips(final ShipArrangement shipArrangement) {
        List<List<Coords>> ships = new ArrayList<>();
        for (List<Coords> coords : shipArrangement.getShipCoords()) {
            if (coords.get(0).getAxis() != coords.get(1).getAxis()) {
                int min = Math.min(coords.get(0).getAxis(), coords.get(1).getAxis());
                int max = Math.max(coords.get(0).getAxis(), coords.get(1).getAxis());
                List<Coords> shipCoords = new ArrayList<>();
                for (int i = min; i <= max; i++) {
                    shipCoords.add(new Coords(i, coords.get(0).getOrdinate()));
                }
                ships.add(shipCoords);
            } else if (coords.get(0).getOrdinate() != coords.get(1).getOrdinate()) {
                int min = Math.min(coords.get(0).getOrdinate(), coords.get(1).getOrdinate());
                int max = Math.max(coords.get(0).getOrdinate(), coords.get(1).getOrdinate());
                List<Coords> shipCoords = new ArrayList<>();
                for (int i = min; i <= max; i++) {
                    shipCoords.add(new Coords(coords.get(0).getAxis(), i));
                }
                ships.add(shipCoords);
            } else {
                List<Coords> shipCoords = new ArrayList<>();
                shipCoords.add(new Coords(coords.get(0).getAxis(), coords.get(0).getOrdinate()));
                ships.add(shipCoords);
            }
        }
        return ships;
    }

    public boolean validateArrangementPosition(final ShipArrangement shipArrangement) {
        List<List<Coords>> ships = makeShips(shipArrangement);
        for (List<Coords> coords : shipArrangement.getShipCoords()) {
            if (coords.get(0).getOrdinate() != coords.get(1).getOrdinate() && coords.get(0).getAxis() != coords.get(1).getAxis()) {
                return false;
            }
        }

        for (int i = 0; i < ships.size(); i++) {
            List<Coords> currentShip = ships.get(i);
            for (int j = i + 1; j < ships.get(i).size(); j++) {
                List<Coords> nextShip = ships.get(j);
                for (Coords currentCoord : currentShip) {
                    for (Coords nextCoord : nextShip) {
                        if (areAdjacent(currentCoord, nextCoord)) {
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }


    public boolean validateArrangementCount(final ShipArrangement shipArrangement) {
        shipMap.put(ShipCount.SINGLE_DECK, 0);
        shipMap.put(ShipCount.DOUBLE_DECK, 0);
        shipMap.put(ShipCount.THREE_DECK, 0);
        shipMap.put(ShipCount.FOUR_DECK, 0);
        ArrayList<Integer> distanceArray = new ArrayList<>();
        for (List<Coords> shipCoords : shipArrangement.getShipCoords()) {
            if (!validateCoordinate(shipCoords)) {
                return false;
            }
            distanceArray.add((int) Math.sqrt(
                    Math.pow(
                            shipCoords.get(0).getAxis() - shipCoords.get(1).getAxis(), 2
                    ) + (Math.pow(
                            shipCoords.get(0).getOrdinate() - shipCoords.get(1).getOrdinate(), 2)
                    )
            ) + 1);
        }
        for (Integer distance : distanceArray) {
            switch (distance) {
                case 1:
                    shipMap.put(4, shipMap.get(4) + 1);
                    break;
                case 2:
                    shipMap.put(3, shipMap.get(3) + 1);
                    break;
                case 3:
                    shipMap.put(2, shipMap.get(2) + 1);
                    break;
                case 4:
                    shipMap.put(1, shipMap.get(1) + 1);
                    break;
            }
        }
        for (Integer el : shipMap.keySet()) {
            if (!Objects.equals(el, shipMap.get(el))) {
                return false;
            }
        }
        return true;
    }

    public boolean validate(final ShipArrangement shipArrangement) {
        return validateArrangementPosition(shipArrangement) && validateArrangementCount(shipArrangement);
    }

    private boolean validateCoordinate(final List<Coords> coords) {
        if (coords.get(0).getAxis() < 1 || coords.get(0).getAxis() > 10 || coords.get(0).getOrdinate() < 1 || coords.get(0).getOrdinate() > 10) {
            return false;
        } else if (coords.get(1).getAxis() < 1 || coords.get(1).getAxis() > 10 || coords.get(1).getOrdinate() < 1 || coords.get(1).getOrdinate() > 10) {
            return false;
        }
        return true;
    }
}

