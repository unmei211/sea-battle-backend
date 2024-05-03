package it.sevenbits.seabattle.core.util.ship;

import it.sevenbits.seabattle.core.util.exceptions.ConflictException;
import it.sevenbits.seabattle.core.validator.session.ShipCount;
import it.sevenbits.seabattle.web.model.Coords;
import it.sevenbits.seabattle.web.model.ShipArrangement;
import org.springframework.stereotype.Component;

import java.sql.SQLOutput;
import java.util.*;

@Component
public class ShipRandomizer {
    private final static Integer BOARD_SIZE = 10;
    private final Random random = new Random();

    private void appendShipType(
            List<Integer> requiredShips, int count, int shipSize
    ) {
        for (int i = 0; i < count; i++) {
            requiredShips.add(shipSize);
        }
    }

    private List<Integer> getRequiredShips() {
        List<Integer> requiredShips = new ArrayList<>();

        appendShipType(requiredShips, ShipCount.FOUR_DECK, 4);
        appendShipType(requiredShips, ShipCount.THREE_DECK, 3);
        appendShipType(requiredShips, ShipCount.DOUBLE_DECK, 2);
        appendShipType(requiredShips, ShipCount.SINGLE_DECK, 1);

        return requiredShips;
    }

    public void out(Set<Coords> backed) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (backed.contains(new Coords(j, i))) {
                    System.out.print("X");
                } else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }
    }

    public void rawOut(ShipArrangement arrangement) {
        Set<Coords> backed = new HashSet<>();
        for (List<Coords> ship : arrangement.getShipCoords()) {
            backed.addAll(ship);
        }

        out(backed);
    }

    public ShipArrangement randomize() {
        ShipArrangement rawArrangement = new ShipArrangement();
        List<Integer> requiredShips = getRequiredShips();

        final Set<Coords> bakedPull = new HashSet<>();
        final Set<Coords> blockedCoords = new HashSet<>();

        for (Integer shipSize : requiredShips) {
            placeShip(shipSize, bakedPull, rawArrangement, blockedCoords);
        }
//        out(bakedPull);
//        System.out.println("\nraw\n");
//        rawOut(rawArrangement);
        return rawArrangement;
    }

    private void placeShip(Integer shipSize, Set<Coords> bakedPull, ShipArrangement raw, Set<Coords> blockedCoords) {
        boolean placed = false;
        int count = 0;
        while (!placed) {
            if (count > 40) {
                throw new ConflictException("infinity cycle in place");
            }
            int row = random.nextInt(BOARD_SIZE - 1) + 1;
            int col = random.nextInt(BOARD_SIZE - 1) + 1;

            boolean horizontal = random.nextBoolean();
//            System.out.println("iteration " + count);
            if (canPlaceShip(shipSize, bakedPull, col, row, horizontal, blockedCoords)) {
                List<Coords> ship = buildPotentialShip(shipSize, col, row, horizontal);
                place(ship, delimit(col, row, shipSize, horizontal), bakedPull, raw, blockedCoords);
                placed = true;
            }
            count++;
        }
    }

    private void place(
            List<Coords> filledShip,
            List<Coords> delimitShip,
            Set<Coords> bakedPull,
            ShipArrangement raw,
            Set<Coords> blockedCoords
    ) {
        bakedPull.addAll(filledShip);
        raw.getShipCoords().add(delimitShip);
        letBlockCells(filledShip, bakedPull, blockedCoords);
    }

    private List<Coords> delimit(int col, int row, int shipSize, boolean horizontal) {
        List<Coords> limits = new ArrayList<>();
        limits.add(new Coords(col, row));
        if (horizontal) {
            limits.add(new Coords(col + shipSize - 1, row));
        } else {
            limits.add(new Coords(col, row + shipSize - 1));
        }
        return limits;
    }

    private boolean intersection(List<Coords> potentialShip, Set<Coords> pull) {
        for (Coords potentialShipCell : potentialShip) {
            if (pull.contains(potentialShipCell)) {
                return true;
            }
        }
        return false;
    }

    private List<Coords> buildPotentialShip(int shipSize, int col, int row, boolean horizontal) {
        List<Coords> potentialShip = new ArrayList<>();

        for (int i = 0; i < shipSize; i++) {
            if (horizontal) {
                potentialShip.add(new Coords(col++, row));
            } else {
                potentialShip.add(new Coords(col, row++));
            }
        }

        return potentialShip;
    }

    private boolean limit(int coordinate) {
        return coordinate > BOARD_SIZE || coordinate < 1;
    }

    private void checkAndBlock(
            int toLimit,
            boolean axis,
            Coords shipCell,
            Set<Coords> baked,
            Set<Coords> blocked
    ) {
        if (!limit(toLimit)) {
            Coords coord;
            if (axis) {
                coord = new Coords(shipCell.getAxis(), toLimit);
            } else {
                coord = new Coords(toLimit, shipCell.getOrdinate());
            }
            if (!baked.contains(coord)) {
                blocked.add(coord);
            }
        }
    }

    private void checkAndBlock(
            int x,
            int y,
            Set<Coords> baked,
            Set<Coords> blocked
    ) {
        if (!limit(x) || !limit(y)) {
            Coords coord = new Coords(y, x);
            if (!baked.contains(coord)) {
                blocked.add(coord);
            }
        }
    }

    private void letBlockCells(List<Coords> ship, Set<Coords> baked, Set<Coords> blocked) {

        for (Coords shipCell : ship) {
            int nextDownY = shipCell.getAxis() + 1;
            int nextRightX = shipCell.getOrdinate() + 1;
            int nextUpY = shipCell.getAxis() - 1;
            int nextLeftX = shipCell.getOrdinate() - 1;

            checkAndBlock(nextLeftX, true, shipCell, baked, blocked);
            checkAndBlock(nextRightX, true, shipCell, baked, blocked);
            checkAndBlock(nextUpY, false, shipCell, baked, blocked);
            checkAndBlock(nextDownY, false, shipCell, baked, blocked);

            checkAndBlock(nextLeftX, nextDownY, baked, blocked);
            checkAndBlock(nextLeftX, nextUpY, baked, blocked);
            checkAndBlock(nextRightX, nextDownY, baked, blocked);
            checkAndBlock(nextRightX, nextUpY, baked, blocked);
        }
    }

    private boolean canPlaceShip(
            Integer shipSize,
            Set<Coords> bakedPull,
            int col,
            int row,
            boolean horizontal,
            Set<Coords> blockedCoords
    ) {
        if (horizontal && col + shipSize - 1 > BOARD_SIZE) {
            return false;
        } else if (!horizontal && row + shipSize - 1 > BOARD_SIZE) {
            return false;
        }

        List<Coords> potentialShip = buildPotentialShip(shipSize, col, row, horizontal);

        return !intersection(potentialShip, bakedPull) && !intersection(potentialShip, blockedCoords);
    }
}
