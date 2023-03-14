package com.cw1;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class IceRinkPanel extends JPanel {
    private static IceRinkPanel instance = null;
    private List<Visitor> visitors;
    private List<Visitor> skatingVisitors;

    private IceRinkPanel() {
        this.skatingVisitors = new ArrayList<>();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Ice Arena"),
                BorderFactory.createEmptyBorder(30, 50, 0, 0) // add Margin
        ));

    }

    public static synchronized IceRinkPanel getInstance() {
        if (instance == null) {
            instance = new IceRinkPanel();
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
        int ovalSize = 5;
//
//        // Draw ice rink
//        g.setColor(Color.BLUE);
//        g.fillRect(0, 0, width, height);

        // Draw skaters
        g.setColor(Color.RED);


        int xCenter = (getWidth() / 2) - 100;
        int yStart = 50;
        int yStep = 50;


        drawVisitors(g, xCenter, yStart, yStep, skatingVisitors);
    }

    void drawVisitors(Graphics g, int xCenter, int yStart, int yStep, List<Visitor> visitors) {
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

            g.fillOval(xCenter, y - 10, 15, 15);
            g.drawString(visitor.getId(), xCenter + 20, y + 5);
            if (visitor.getOrder() != null && visitor.getOrder().getStatus() == OrderStatus.Returning) {
                g.drawString(visitor.getOrder().getItemList().toString() + " Returning...", xCenter, y + 25);
            } else if (visitor.getOrder() != null) {
                g.drawString(visitor.getOrder().getItemList().toString(), xCenter, y + 25);

            }
        }
    }

    public void redraw() {
        repaint();
    }

}
