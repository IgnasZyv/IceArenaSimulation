package com.cw1;

import javax.swing.*;
import java.awt.*;

public class SimulationFrame extends JPanel {

//    private final SimulationPanel simulationPanel;
    private final QueuePanel queuePanel;
//    private final IceArenaPanel iceArenaPanel;
    private final StatisticsPanel statisticsPanel;

    public SimulationFrame(IceArena iceArena) {
//        this.simulationPanel = new SimulationPanel(visitors);

        setLayout(new GridLayout(1,3));

//        iceArenaPanel = new IceArenaPanel(visitors);
//        iceArenaPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
//        add(iceArenaPanel, BorderLayout.EAST);

        queuePanel = QueuePanel.getInstance();
//        queuePanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        add(queuePanel, BorderLayout.NORTH);

        statisticsPanel = new StatisticsPanel();
//        statisticsPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        add(statisticsPanel, BorderLayout.WEST);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        simulationPanel.paintComponent(g);
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
