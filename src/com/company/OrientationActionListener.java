package com.company;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.company.ShipPart.orientation;
import static com.company.GridLayoutManager.orientationButton;


public class OrientationActionListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (orientation.equals("horizontal")) {
            orientation = "vertical";
            orientationButton.setText("Vertical");
        }
        else if (orientation.equals("vertical")) {
            orientation = "horizontal";
            orientationButton.setText("Horizontal");
        }

    }
}
