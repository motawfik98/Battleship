package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

import static com.company.ButtonHandler.phase;
import static com.company.GridLayoutManager.compBoard;
import static com.company.GridLayoutManager.textField;
import static com.company.GridLayoutManager.userBoard;
import static com.company.Opponent.*;

public class ShipPart extends JButton {

    private int row;
    private int column;
    boolean isSelected;
    private boolean isMarked = false;
    private boolean isUpwardsAvailable = true;
    private boolean isDownwardsAvailable = true;
    private boolean isLeftAvailable = true;
    private boolean isRightAvailable = true;


    static String orientation;
    static ShipPart[] carrier = new ShipPart[5];
    static ShipPart[] battleship = new ShipPart[4];
    static ShipPart[] cruiser = new ShipPart[3];
    static ShipPart[] submarine = new ShipPart[3];
    static ShipPart[] destroyer = new ShipPart[2];
    private static ShipPart[] hitShip;
    private static ShipPart[] hitUserShip;
    static ShipPart firstHitPart = null;
    static ShipPart[][] userShips = {carrier, battleship, cruiser, submarine, destroyer};

    private ShipPart firstCarrierHit = null, firstBattleshipHit = null,
            firstCruiserHit = null, firstSubmarineHit = null, firstDestroyerHit = null;

    ShipPart[] firstHitParts = {firstCarrierHit, firstBattleshipHit,
            firstCruiserHit, firstSubmarineHit, firstDestroyerHit};

    ShipPart(int row, int column, String orientation) {
        this.row = row;
        this.column = column;
        isSelected = false;
        ShipPart.orientation = orientation;
        setBoundsAvailable(row, column);
    }

    ShipPart() {
        isSelected = false;
        orientation = "horizontal";
    }

    private void setBoundsAvailable(int row, int column) {
        if (row == 0)
            isUpwardsAvailable = false;
        if (row == 9)
            isDownwardsAvailable = false;
        if (column == 0)
            isLeftAvailable = false;
        if (column == 9)
            isRightAvailable = false;
    }

    private void addPhoto(String shipName, int shipPart) {
        try {
            Image img = ImageIO.read(getClass().getResource("photos/" + shipName + "/" +
                    this.getOrientation() + "/" + shipName + "_" + shipPart + ".png"));
            img = img.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
            setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    void addShipPart(int shipLength, String shipName) {

        for (int z = 0; z < shipLength; z++) {
            getNextTile(getRow(), getColumn(), z).addPhoto(shipName, z + 1);
        }
    }

    private ShipPart[] getExpectedPlace(int shipLength) {
        ShipPart[] expectedPlaces = new ShipPart[shipLength];
        for (int i = 0; i < shipLength; i++) {
            expectedPlaces[i] = getNextTile(getRow(), getColumn(), i);
        }
        return expectedPlaces;
    }

    boolean checkIfAvailable(int shipLength) {

        int startOfShip = getStartOfShip();

        ShipPart[] expectedShip;
        if (startOfShip + shipLength <= 10) {
            expectedShip = getExpectedPlace(shipLength);
            for (ShipPart expectedPart : expectedShip)
                if (expectedPart.isMarked)
                    return false;

            phase++;
            changeTextField();
            return true;
        }
        return false;
    }

    private void changeTextField() {
        switch (phase) {
            case 6:
                textField.setText("Place Battleship");
                break;
            case 7:
                textField.setText("Place Cruiser");
                break;
            case 8:
                textField.setText("Place Submarine");
                break;
            case 9:
                textField.setText("Place Destroyer");
                break;
            case 10:
                textField.setText("Your turn");
                break;
            case 11:
                textField.setText("Computer's turn");
        }
    }

    private int getStartOfShip() {
        int startOfShip;
        if (getOrientation().equals("horizontal"))
            startOfShip = getColumn();
        else
            startOfShip = getRow();
        return startOfShip;
    }

    void addInstantiateUserShip(int shipLength, String shipName, ShipPart[] userShip) {
        if (checkIfAvailable(shipLength)) {
            addShipPart(shipLength, shipName);
            instantiateShips(userShip, shipLength);
        }
    }

    private ShipPart getNextTile(int x, int y, int z) {

        if (this.getClass().equals(ShipPart.class)) {
            if (getOrientation().equals("horizontal"))
                return userBoard[x][y + z];
            else
                return userBoard[x + z][y];
        } else {
            if (getOrientation().equals("horizontal"))
                return compBoard[x][y + z];
            else
                return compBoard[x + z][y];

        }
    }


    void instantiateShips(ShipPart[] ship, int shipLength) {
        for (int z = 0; z < shipLength; z++) {
            ship[z] = getNextTile(getRow(), getColumn(), z);
            ship[z].isMarked = true;
        }
    }


    int getRow() {
        return row;
    }

    int getColumn() {
        return column;
    }

    private String getOrientation() {
        return orientation;
    }

    @Override
    public String toString() {
        return "ShipPart{}  row: " + getRow() + " column " + getColumn() +
                " Left = " + isLeftAvailable + " Right = " + isRightAvailable + " Upwards = " + isUpwardsAvailable
                + " Downwards = " + isDownwardsAvailable;
    }

    void attack(ShipPart[][] ships) {
        if (!isSelected)
            checkShip(ships, "user", compBoard);
    }

    void checkShip(ShipPart[][] ships, String turn, ShipPart[][] board) {
        if (hitPart(ships, board)) {
            shipDestroyed(compShips);
        } else {
            determineTurn(turn);
        }
    }

    boolean hitPart(ShipPart[][] ships, ShipPart[][] board) {
        ShipPart selectedPart = board[this.getRow()][this.getColumn()];
        for (ShipPart[] ship : ships) {
            for (ShipPart shipPart : ship) {
                if (shipPart.equals(selectedPart)) {
                    selectedPart.setBackground(Color.red);
                    hitShip = ship;
                    if (this.getClass().equals(Opponent.class))
                        isFirstHitInShip(ship, selectedPart);
                    selectedPart.isSelected = true;
                    return true;
                } else {
                    selectedPart.setBackground(Color.gray);
                }
            }
        }
        selectedPart.isSelected = true;
        return false;
    }



    private boolean shipDestroyed(ShipPart[][] ships) {
        for (ShipPart shipPart : hitShip) {
            if (!shipPart.isSelected) {
                return false;
            }
        }
        System.out.println(Arrays.toString(hitShip));
        allShipsDestroyed(ships);
        return true;
    }

    private boolean allShipsDestroyed(ShipPart[][] ships) {
        for (ShipPart[] ship : ships) {
            for (ShipPart shipPart : ship) {
                if (!shipPart.isSelected) {
                    return false;
                }
            }
        }
        phase = 12;
        return true;
    }

    void determineWinner() {
        if (allShipsDestroyed(userShips))
            textField.setText("YOU LOSE!!");
        else if (allShipsDestroyed(compShips)) {
            textField.setText("YOU WIN!!");
        }
    }

    private void determineTurn(String turn) {
        if (turn.equals("user")) {
            phase++;
            textField.setText("Computer's turn");
        } else if (turn.equals("computer")) {
            phase--;
            textField.setText("Your turn");
        }
    }

    private boolean isFirstHitInShip(ShipPart[] ship, ShipPart hitPart) {
        for (ShipPart shipPart : ship) {
            if (shipPart.isSelected) {
                return false;
            }
        }
        for (int i = 0; i < 4; i++) {
            if (Arrays.equals(ship, userShips[i])) {
                firstHitParts[i] = hitPart;
            }
        }
        return true;
    }

}