package it.sevenbits.seabattle.core.validator.session;

import it.sevenbits.seabattle.web.model.Coords;
import it.sevenbits.seabattle.web.model.ShipArrangement;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Component
public class ArrangementValidator {

    private final ShipCount shipCount;
    private HashMap<Integer, Integer> shipMap;

    public ArrangementValidator() {
        this.shipCount = new ShipCount();
        shipMap = new HashMap<>();
    }

    public boolean validateArrangementPosition(final ShipArrangement shipArrangement) {

        return true;
    }


    public boolean validateArrangementCount(final ShipArrangement shipArrangement) {
        shipMap.put(shipCount.getSINGLE_DECK(), 0);
        shipMap.put(shipCount.getDOUBLE_DECK(), 0);
        shipMap.put(shipCount.getTHREE_DECK(), 0);
        shipMap.put(shipCount.getFOUR_DECK(), 0);
        ArrayList<Integer> distanceArray = new ArrayList<>();
        for (List<Coords> shipCoords : shipArrangement.getShipCoords()) {
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
}



