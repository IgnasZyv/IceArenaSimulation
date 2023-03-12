package com.cw1;

import javax.swing.*;
import java.awt.*;

public class SimulationFrame extends JPanel {

    private final QueuePanel queuePanel;
    private final IceArenaPanel iceArenaPanel;
    private final DiningHallPanel diningHallPanel;
    private final StatisticsPanel statisticsPanel;

    public SimulationFrame(IceArena iceArena) {
//        this.simulationPanel = new SimulationPanel(visitors);

        setLayout(new GridLayout(0,4));

        queuePanel = QueuePanel.getInstance();
//        queuePanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        add(queuePanel);

        iceArenaPanel = IceArenaPanel.getInstance();
//        iceArenaPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        add(iceArenaPanel);

        diningHallPanel = DiningHallPanel.getInstance();
        add(diningHallPanel);

        statisticsPanel = new StatisticsPanel();
//        statisticsPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        add(statisticsPanel);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    public QueuePanel getQueuePanel() {
        return queuePanel;
    }

//    public IceArenaPanel getIceArenaPanel() {
//        return iceArenaPanel;
//    }

    public StatisticsPanel getStatisticsPanel() {
        return statisticsPanel;
    }
}
