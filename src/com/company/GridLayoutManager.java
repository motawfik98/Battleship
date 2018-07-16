package com.company;

import javax.swing.*;
import java.awt.*;

class GridLayoutManager extends JFrame {

    private JPanel userPanel = new JPanel();
    private JPanel opponentPanel = new JPanel();
    private JPanel emptySpacePanel = new JPanel();
    static ShipPart[][] userBoard = new ShipPart[10][10];
    static ShipPart[][] compBoard = new ShipPart[10][10];

    static JTextField textField;
    static JButton orientationButton;


    //Colors
    private Color colorBlack = Color.black;
    private Color colorBlue = Color.blue;

    GridLayoutManager() {
        ButtonHandler buttonHandler = new ButtonHandler();
        makeGrid(buttonHandler);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout());
        userPanel.setLayout(new GridLayout(10,10));
        opponentPanel.setLayout(new GridLayout(10,10));
        emptySpacePanel.setLayout(new BoxLayout(emptySpacePanel, BoxLayout.Y_AXIS));

//        emptySpacePanel.setBorder(new EmptyBorder(0,75,0,75));

        emptySpacePanel.setPreferredSize(new Dimension(150, 500));
        mainPanel.add(userPanel);
        mainPanel.add(emptySpacePanel);
        mainPanel.add(opponentPanel);

        add(mainPanel);
        setTitle("Battleship");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setVisible(true);

    }


    private void makeGrid(ButtonHandler buttonHandler) {
        int i, j;
        //Create and add board components
        for (i = 0; i < 10; i++) {
            for (j = 0; j < 10; j++) {
                getFinalButton(buttonHandler, i, j, userBoard);
                getFinalButton(buttonHandler, i, j, compBoard);
                userPanel.add(userBoard[i][j]);
                opponentPanel.add(compBoard[i][j]);

            }
        }
        addToEmptyPanel();
    }

    private void getFinalButton(ButtonHandler buttonHandler, int i, int j, ShipPart[][] board) {
        board[i][j] = makeShipPart(i, j);
        setButtonColor(i, j, board);
        board[i][j].setPreferredSize(new Dimension(50, 50));
        board[i][j].addActionListener(buttonHandler);
    }

    private void addToEmptyPanel() {
        textField = new JTextField("BATTLESHIP");
        textField.setEditable(false);
        emptySpacePanel.add(textField);
        orientationButton = new JButton();
        orientationButton.setText("Horizontal");
        orientationButton.setMinimumSize(new Dimension(140, 30));
        orientationButton.setPreferredSize(new Dimension(140, 30));
        orientationButton.setMaximumSize(new Dimension(140, 30));
        orientationButton.addActionListener(new OrientationActionListener());
        emptySpacePanel.add(orientationButton);
    }

    private ShipPart makeShipPart(int row, int column) {
        return new ShipPart(row, column, null);
    }

    private void setButtonColor(int i, int j, ShipPart[][] board) {
        if ((i + j) % 2 != 0) {
            board[i][j].setBackground(colorBlack);
        } else {
            board[i][j].setBackground(colorBlue);
        }
    }
}