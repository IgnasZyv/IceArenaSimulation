package com.cw1;

import javax.swing.*;
import java.awt.*;

public class SimulationFrame extends JPanel {

    private final QueuePanel queuePanel;
    private final IceRinkPanel iceRinkPanel;
    private final DiningHallPanel diningHallPanel;
    private final StatisticsPanel statisticsPanel;

    public SimulationFrame(IceRink iceRink) {
//        this.simulationPanel = new SimulationPanel(visitors);

        setLayout(new GridLayout(0,5));

        queuePanel = QueuePanel.getInstance();
//        queuePanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        add(queuePanel);

        iceRinkPanel = IceRinkPanel.getInstance();
//        iceRinkPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        add(iceRinkPanel);

        DiningHallQueue diningHallQueue = DiningHallQueue.getInstance();
        add(diningHallQueue);

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

//    public IceRinkPanel getIceArenaPanel() {
//        return iceRinkPanel;
//    }

    public StatisticsPanel getStatisticsPanel() {
        return statisticsPanel;
    }
}
