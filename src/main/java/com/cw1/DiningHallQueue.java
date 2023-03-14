package com.cw1;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DiningHallQueue extends JPanel {
    private static DiningHallQueue instance = null;
    private final List<Visitor> visitors;

    private DiningHallQueue() {
        this.visitors = new ArrayList<>();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Dining Hall Queue"),
                BorderFactory.createEmptyBorder(30, 0, 0, 0) // add Margin
        ));
    }

    public static DiningHallQueue getInstance() {
        if (instance == null) {
            instance = new DiningHallQueue();
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

        IceRinkPanel.getInstance().drawVisitors(g, xCenter, yStart, yStep, visitors);
    }

    public void addVisitor(Visitor visitor) {
        visitors.add(visitor);
        StatisticsPanel.updateWaitingDiningVisitors(visitors.size());
        repaint();
    }

    public void removeQueue(Visitor visitor) {
        visitors.remove(visitor);
        StatisticsPanel.updateWaitingDiningVisitors(visitors.size());
        repaint();
    }

}
