package com.cw1;

import javax.swing.*;
import java.awt.*;

public class SimulationFrame extends JPanel {

    private final QueuePanel queuePanel;
    private final StatisticsPanel statisticsPanel;

    public SimulationFrame(IceRink iceRink) {
        GridLayout gridLayout = new GridLayout(0, 5);

        setLayout(gridLayout);

        // Add panels to the frame
        queuePanel = QueuePanel.getInstance();
        add(queuePanel);

        IceRinkPanel iceRinkPanel = IceRinkPanel.getInstance();
        add(iceRinkPanel);

        DiningHallQueue diningHallQueue = DiningHallQueue.getInstance();
        add(diningHallQueue);

        DiningHallPanel diningHallPanel = DiningHallPanel.getInstance();
        add(diningHallPanel);

        statisticsPanel = new StatisticsPanel();
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(statisticsPanel);
        add(panel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

}
