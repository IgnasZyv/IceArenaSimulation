package com.cw1;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SimulationPanel extends JPanel {

    private List<Visitor> visitors;


    public SimulationPanel(List<Visitor> visitors) {
        this.visitors = visitors;

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//
//        int x = 0;
//        int y = 300;
//
//        for (Visitor visitor : visitors) {
//            g.setColor(Color.BLACK);
//            g.fillOval(400, y += 23, 20, 20);
//        }

    }
}
