package com.cw1;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class QueuePanel extends JPanel {
    private static QueuePanel instance = null;
    private List<Visitor> visitors;

    private QueuePanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Queue"),
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

        IceArenaPanel.getInstance().drawVisitors(g, xCenter, yStart, yStep, visitors);
    }

    public void updateQueue(List<Visitor> skaters) {
        setVisitors(skaters);
    }

    public void addQueue(List<Visitor> skaters) {
        for (Visitor visitor : skaters) {
            if (visitor.getSkating()) { // if visitor is already skating, skip
                continue;
            }
            if (!visitors.contains(visitor)) {
                visitors.add(visitor); // add non-skating visitors to queue
            }
        }
        System.out.println("Queue size: " + visitors.size());
        setVisitors(visitors);
    }

    public void setVisitors(List<Visitor> visitors) {
        this.visitors = visitors;
        repaint(); // repaint the panel
    }

    public List<Visitor> getVisitors() {
        return visitors;
    }
}
