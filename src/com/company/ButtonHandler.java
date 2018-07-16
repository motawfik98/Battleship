package com.company;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.company.GridLayoutManager.compBoard;
import static com.company.GridLayoutManager.userBoard;
import static com.company.GridLayoutManager.textField;
import static com.company.Opponent.*;

public class ButtonHandler implements ActionListener {

    static int phase = 0;
    private ShipPart currentPart;
    private int placeX;
    private int placeY;
    private Opponent opponent = new Opponent();

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        for (placeX = 0; placeX < 10; placeX++) {
            for (placeY = 0; placeY < 10; placeY++) {
                if (source == userBoard[placeX][placeY]) {
                    // Make your action when clicked
                    addShipsOnClick();
                    return;
                } else if (source == compBoard[placeX][placeY]) {
                    attack();
                }
            }
        }
    }

    private void addShipsOnClick() {
        currentPart = userBoard[placeX][placeY];
        switch (phase) {
            case 5:
                currentPart.addInstantiateUserShip(5, "carrier", carrier);
                break;
            case 6:
                currentPart.addInstantiateUserShip(4, "battleship", battleship);
                break;
            case 7:
                currentPart.addInstantiateUserShip(3, "cruiser", cruiser);
                break;
            case 8:
                currentPart.addInstantiateUserShip(3, "submarine", submarine);
                break;
            case 9:
                currentPart.addInstantiateUserShip(2, "destroyer", destroyer);
                break;
        }
    }

    private void attack() {
        if (phase == 10) {
            currentPart = compBoard[placeX][placeY];
            currentPart.attack(compShips);
            while (phase == 11) {
                do {
                    getOpponent();
                } while (userBoard[opponent.getRow()][opponent.getColumn()].isSelected);
                opponent.attack(userShips);
            }
            currentPart.determineWinner();
        }
    }

    private void getOpponent() {
        int row = (opponent.getCompRow());
        int column = (opponent.getCompColumn());
        String orientation = Opponent.chooseOrientation[opponent.getRandomOrientation()];
        opponent = new Opponent(row, column, orientation);
    }
}
