package com.company;

import java.util.Random;

import static com.company.GridLayoutManager.compBoard;
import static com.company.GridLayoutManager.userBoard;

public class Opponent extends ShipPart {

    private Random random = new Random();
    private int shipLength;
    public static String[] chooseOrientation = {"horizontal", "vertical"};
    static ShipPart[] compCarrier = new ShipPart[5];
    static ShipPart[] compBattleship = new ShipPart[4];
    static ShipPart[] compCruiser = new ShipPart[3];
    static ShipPart[] compSubmarine = new ShipPart[3];
    static ShipPart[] compDestroyer = new ShipPart[2];
    static ShipPart[][] compShips = {compCarrier, compBattleship, compCruiser, compSubmarine, compDestroyer};

    Opponent(int row, int column, String orientation) {
        super(row, column, orientation);
    }

    Opponent() {

    }

    int getRandomOrientation() {
        return random.nextInt(2);
    }

    int getCompRow() {
        int row;
        do {
            row = random.nextInt(10);
        } while (row > 9);
        return row;
    }

    int getCompColumn() {
        return random.nextInt(10);

    }

    int getCompColumn(int shipLength) {
        int column;
        do {
            column = getCompColumn();
        } while (column + shipLength > 10);
        return column;
    }

    private void changeShipLength(int shipLength) {
        this.shipLength = shipLength;
    }

    void addInstantiateCompShip(int shipLength, String shipName, ShipPart[] compShip) {
        changeShipLength(shipLength);
        if (checkIfAvailable(shipLength)) {
//            addShipPart(shipLength, shipName);
            instantiateShips(compShip, shipLength);
        }
    }

    @Override
    void attack(ShipPart[][] ships) {
        for (ShipPart firstHitPart : firstHitParts) {
            if (firstHitPart != null) {
                if (isAvailableRight(firstHitPart)) {
                    getNextHorizontalOpponent(getRow(), getColumn())
                            .checkShip(ships, "computer", userBoard);
                }
            }
        }
        if (!isSelected) {
            checkShip(ships, "computer", userBoard);
        }
    }

    static boolean isAvailableRight(ShipPart opponent) {
        if (opponent.getColumn() + 1 > 9) {
            return false;
        } else if (getNextHorizontalOpponent(opponent.getRow(), opponent.getColumn()).isSelected) {
            return false;
        }
        return true;
    }

    static boolean isAvailableLet(ShipPart opponent) {
        if (opponent.getColumn() - 1 < 0) {
            return false;
        } else if (getPreviousHorizontalOpponent(opponent.getRow(), opponent.getColumn()).isSelected) {
            return false;
        }
        return true;
    }

    static boolean isAvailableUpwards(ShipPart opponent) {
        if (opponent.getRow() - 1 < 0) {
            return false;
        } else if (getNextVerticalOpponent(opponent.getRow(), opponent.getColumn()).isSelected) {
            return false;
        }
        return true;
    }

    static boolean isAvailableDownwards(ShipPart opponent) {
        if (opponent.getRow() + 1 > 9) {
            return false;
        } else if (getPreviousVerticalOpponent(opponent.getRow(), opponent.getColumn()).isSelected) {
            return false;
        }
        return true;
    }

    private static ShipPart getNextHorizontalOpponent(int x, int y) {
        return new ShipPart(x, y + 1, null);
    }

    private static ShipPart getPreviousHorizontalOpponent(int x, int y) {
        return new ShipPart(x, y - 1, null);
    }

    private static ShipPart getNextVerticalOpponent(int x, int y) {
        return new ShipPart(x + 1, y, null);
    }

    private static ShipPart getPreviousVerticalOpponent(int x, int y) {
        return new ShipPart(x - 1, y, null);
    }
}
