package com.cw1;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class QueuePanel extends JPanel {
    private static QueuePanel instance = null;
    private List<Visitor> visitors;

    private QueuePanel() {
        // TOOD: Actually store vistors in a queue in outlet which will just render the list and forget about it. More modular and shit for memory.
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

        int xCenter = getWidth() / 2;
        int yStart = 50;
        int yStep = 50;

        g.setColor(Color.BLACK);
        g.drawString("Queue", xCenter - 20, yStart - 20);

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
