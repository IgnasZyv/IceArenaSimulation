package com.cw1;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class IceArenaPanel extends JPanel {
    private List<Visitor> visitors;
    private int skatingVisitors;

    public IceArenaPanel(List<Visitor> visitors) {
        this.visitors = visitors;

        setBorder(BorderFactory.createTitledBorder("Ice Arena"));
        setPreferredSize(new Dimension(200, 400));


        skatingVisitors = 0;
    }

    public void updateSkatingVisitors(int skatingVisitors) {
        this.skatingVisitors = skatingVisitors;
        repaint();
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = getWidth() - 200;
        int height = getHeight() - 200;
        int ovalSize = 5;

        // Draw ice rink
        g.setColor(Color.BLUE);
        g.fillRect(0, 0, width, height);

        // Draw skaters
        g.setColor(Color.RED);
        for (int i = 0; i < skatingVisitors; i++) {
            int x = (int) (Math.random() * width);
            int y = (int) (Math.random() * height);
            g.fillRoundRect(x - ovalSize / 2, y - ovalSize / 2, ovalSize, ovalSize, 10, 10);
        }
    }

}
