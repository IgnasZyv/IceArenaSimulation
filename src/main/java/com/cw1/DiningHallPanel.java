package com.cw1;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DiningHallPanel extends JPanel {
    private static DiningHallPanel instance = null;
    private final List<Visitor> visitorList;

    private DiningHallPanel() {
        visitorList = new ArrayList<>();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Dining Hall"),
                BorderFactory.createEmptyBorder(30, 0, 0, 0) // add Margin
        ));
    }

    public static DiningHallPanel getInstance() {
        if (instance == null) {
            instance = new DiningHallPanel();
        }
        return instance;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int xCenter = (getWidth() / 2) - 100;
        int yStart = 50;
        int yStep = 50;

        g.setColor(Color.BLACK);
        g.drawString("Dining Hall", xCenter - 100, yStart - 20);

        IceRinkPanel.getInstance().drawVisitors(g, xCenter, yStart, yStep, visitorList);
    }

    public void addVisitor(Visitor visitor) {
        visitorList.add(visitor);
        StatisticsPanel.updateDiningVisitors(visitorList.size());
        repaint();
    }

    public void removeQueue(Visitor visitor) {
        visitorList.remove(visitor);
        StatisticsPanel.updateDiningVisitors(visitorList.size());
        repaint();
    }


}
