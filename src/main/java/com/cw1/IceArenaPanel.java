package com.cw1;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class IceArenaPanel extends JPanel {
    private static IceArenaPanel instance = null;
    private List<Visitor> visitors;
    private List<Visitor> skatingVisitors;

    private IceArenaPanel() {
        this.skatingVisitors = new ArrayList<>();
        setBorder(BorderFactory.createTitledBorder("Ice Arena"));
//        setPreferredSize(new Dimension(200, 400));

    }

    public static synchronized IceArenaPanel getInstance() {
        if (instance == null) {
            instance = new IceArenaPanel();
        }
        return instance;
    }

    public void updateSkatingVisitors(List<Visitor> skatingVisitors) {
        this.skatingVisitors = skatingVisitors;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = getWidth() - 200;
        int height = getHeight() - 200;
        int ovalSize = 5;
//
//        // Draw ice rink
//        g.setColor(Color.BLUE);
//        g.fillRect(0, 0, width, height);

        // Draw skaters
        g.setColor(Color.RED);


        int xCenter = getWidth() / 2;
        int yStart = 50;
        int yStep = 50;


        drawVisitors(g, xCenter, yStart, yStep, skatingVisitors);
    }

    static void drawVisitors(Graphics g, int xCenter, int yStart, int yStep, List<Visitor> visitors) {
        if (visitors == null) {
            return;
        }
        for (int i = 0; i < visitors.size(); i++) {
            Visitor visitor = visitors.get(i);
            int y = yStart + i * yStep;

            if (visitor.getInQueue()) {
                g.setColor(Color.RED);
            } else {
                g.setColor(Color.BLACK);
            }
            g.fillOval(xCenter - 10, y - 10, 20, 20);
            g.drawString(visitor.getId(), xCenter + 20, y + 5);
        }
    }

}
