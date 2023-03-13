package com.cw1;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class StatisticsPanel extends JPanel {
    private static JLabel stockLeftLabel;
    private static JLabel skatingVisitorsLabel;
    private static JLabel waitingVisitorsLabel;
    private static JLabel waitingDiningVisitorsLabel;
    private static JLabel diningVisitorsLabel;
    private static JLabel skatesLabel;
    private static JLabel helmetLabel;
    private static JLabel glovesLabel;
    private static JLabel penguinLabel;

    public StatisticsPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Statistics"),
                BorderFactory.createEmptyBorder(30, 0, 0, 0) // add Margin
        ));

        IceArena iceArena = IceArena.getInstance();

        skatesLabel = new JLabel("Skates: " + iceArena.getSkates().size());
        skatesLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        helmetLabel = new JLabel("Helmets: " + iceArena.getHelmets().size());
        helmetLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        glovesLabel = new JLabel("Gloves: " + iceArena.getGloves().size());
        glovesLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        penguinLabel = new JLabel("Penguins: " + iceArena.getPenguins().size());
        penguinLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        waitingVisitorsLabel = new JLabel("Visitors waiting for items: " +
                QueuePanel.getInstance().getVisitors().size());
        waitingVisitorsLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        skatingVisitorsLabel = new JLabel("Skating visitors: " +
                SkatingArea.getSkaters().size());
        skatingVisitorsLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        waitingDiningVisitorsLabel = new JLabel("Visitors waiting for dining hall: "  +
                DiningHall.getInstance(App.DINING_HALL_CAPACITY).getWaitingVisitorsCount());
        waitingDiningVisitorsLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        diningVisitorsLabel = new JLabel("Visitors in dining hall: " +
                DiningHall.getInstance(App.DINING_HALL_CAPACITY).getVisitors().size());
        diningVisitorsLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        add(skatesLabel);
        add(helmetLabel);
        add(glovesLabel);
        add(penguinLabel);
        add(skatingVisitorsLabel);
        add(waitingVisitorsLabel);
        add(waitingDiningVisitorsLabel);
        add(diningVisitorsLabel);

        // Add input fields for user input
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(0, 1, 10, 5));
        inputPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Settings"),
                BorderFactory.createEmptyBorder(10, 0, 10, 0)
        ));

        JLabel numVisitorsLabel = new JLabel("Number of Visitors:");
        JSpinner numVisitorsField = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
        JPanel visitorFieldPanel = new JPanel();
        visitorFieldPanel.setPreferredSize(new Dimension(1500, 40));
        visitorFieldPanel.add(numVisitorsLabel);
        visitorFieldPanel.add(numVisitorsField);

        JLabel capacityLabel = new JLabel("Capacity of Dining Hall:");
        JSpinner capacityField = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
        JPanel capacityFieldPanel = new JPanel();
        capacityFieldPanel.setPreferredSize(new Dimension(1500, 40));
        capacityFieldPanel.add(capacityLabel);
        capacityFieldPanel.add(capacityField);

        JSeparator separator = new JSeparator();

        JLabel numSkatesLabel = new JLabel("Number of Skates:");
        JSpinner skatesField = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
        JPanel skatesFieldPanel = new JPanel();
        skatesFieldPanel.setPreferredSize(new Dimension(1500, 40));
        skatesFieldPanel.add(numSkatesLabel);
        skatesFieldPanel.add(skatesField);

        JLabel numHelmetsLabel = new JLabel("Number of Helmets:");
        JSpinner helmetsField = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
        JPanel helmetsFieldPanel = new JPanel();
        helmetsFieldPanel.setPreferredSize(new Dimension(1500, 40));
        helmetsFieldPanel.add(numHelmetsLabel);
        helmetsFieldPanel.add(helmetsField);

        JLabel numGlovesLabel = new JLabel("Number of Gloves:");
        JSpinner glovesField = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
        JPanel glovesFieldPanel = new JPanel();
        glovesFieldPanel.setPreferredSize(new Dimension(1500, 40));
        glovesFieldPanel.add(numGlovesLabel);
        glovesFieldPanel.add(glovesField);

        JLabel numPenguinLabel = new JLabel("Number of Penguins:");
        JSpinner penguinField = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
        JPanel penguinFieldPanel = new JPanel();
        penguinFieldPanel.setPreferredSize(new Dimension(1500, 40));
        penguinFieldPanel.add(numPenguinLabel);
        penguinFieldPanel.add(penguinField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(20, 10));
        JButton startButton = new JButton("Start");
        buttonPanel.add(startButton);


        inputPanel.add(visitorFieldPanel);
        inputPanel.add(capacityFieldPanel);

        inputPanel.add(separator);

        inputPanel.add(skatesFieldPanel);
        inputPanel.add(helmetsFieldPanel);
        inputPanel.add(glovesFieldPanel);
        inputPanel.add(penguinFieldPanel);
        inputPanel.add(buttonPanel);

        add(inputPanel);

    }

    public static void updateItems(HashMap<ItemType, Integer> itemQuantity) {
        skatesLabel.setText("Skates: " + itemQuantity.get(ItemType.SKATES));
        helmetLabel.setText("Helmets: " + itemQuantity.get(ItemType.HELMET));
        glovesLabel.setText("Gloves: " + itemQuantity.get(ItemType.GLOVES));
        penguinLabel.setText("Penguins: " + itemQuantity.get(ItemType.PENGUIN));
    }

    public static void updateWaitingVisitors(int waitingVisitors) {
        waitingVisitorsLabel.setText("Visitors waiting for items: " + waitingVisitors);
    }

    public static void updateSkatingVisitors(int skatingVisitors) {
        skatingVisitorsLabel.setText("Skating visitors: " + skatingVisitors);
    }

    public static void updateWaitingDiningVisitors(int waitingDiningVisitors) {
        waitingDiningVisitorsLabel.setText("Visitors waiting for dining hall: " + waitingDiningVisitors);
    }

    public static void updateDiningVisitors(int diningVisitors) {
        diningVisitorsLabel.setText("Visitors in dining hall: " + diningVisitors);
    }

}
