package com.cw1;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class QueuePanel extends JPanel {
    private static QueuePanel instance = null;
    private List<Visitor> visitors;

    private QueuePanel() {
        this.visitors = new ArrayList<>();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Ice Arena Queue"),
                BorderFactory.createEmptyBorder(30, 0, 0, 0) // add Margin
        ));
    }

    public static synchronized QueuePanel getInstance() {
        if (instance == null) {
            instance = new QueuePanel();
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
        g.drawString("Queue", xCenter - 100, yStart - 20);

        IceRinkPanel.getInstance().drawVisitors(g, xCenter, yStart, yStep, visitors);
    }

    public void addQueue(Visitor visitor) {
        visitors.add(visitor);
        StatisticsPanel.updateWaitingVisitors(visitors.size());
        repaint();
    }

    public void removeVisitor(Visitor visitor) {
        visitors.remove(visitor);
        StatisticsPanel.updateWaitingVisitors(visitors.size());
        repaint();
    }

    public void setVisitors(List<Visitor> visitors) {
        this.visitors = visitors;
        repaint(); // repaint the panel
    }

    public List<Visitor> getVisitors() {
        return visitors;
    }

    public void redraw() {
        repaint();
    }
}
