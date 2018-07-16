package com.company;

import static com.company.ButtonHandler.phase;
import static com.company.GridLayoutManager.textField;
import static com.company.Opponent.*;

public class Main {

    public static void main(String[] args) {
	// write your code here
        GridLayoutManager grid = new GridLayoutManager();
        Opponent opponent = new Opponent();
        do {
            switch (phase) {
                case 0:
                    opponent = getOpponent(opponent, 5);
                    opponent.addInstantiateCompShip(5, "carrier", compCarrier);
                    break;
                case 1:
                    opponent = getOpponent(opponent, 4);
                    opponent.addInstantiateCompShip(4, "battleship", compBattleship);
                    break;
                case 2:
                    opponent = getOpponent(opponent, 3);
                    opponent.addInstantiateCompShip(3, "cruiser", compCruiser);
                    break;
                case 3:
                    opponent = getOpponent(opponent, 3);
                    opponent.addInstantiateCompShip(3, "submarine", compSubmarine);
                    break;
                case 4:
                    opponent = getOpponent(opponent, 2);
                    opponent.addInstantiateCompShip(2, "destroyer", compDestroyer);
                    break;
                case 5:
                    orientation = "horizontal";
                    textField.setText("Place Carrier");
            }
        } while (phase <= 5);



    }

    private static Opponent getOpponent(Opponent opponent, int shipLength) {
        int row = opponent.getCompRow();
        int column = opponent.getCompColumn(shipLength);
        String orientation = Opponent.chooseOrientation[opponent.getRandomOrientation()];
        opponent = new Opponent(row, column, orientation);
        return opponent;
    }
}
