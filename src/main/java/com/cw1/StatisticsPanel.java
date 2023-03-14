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
    private static JLabel numSkatesLabel;
    private static JLabel numHelmetsLabel;
    private static JLabel numGlovesLabel;
    private static JLabel numPenguinLabel;
    private static JLabel numVisitorsLabel;
    private static JLabel capacityLabel;

    public StatisticsPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Statistics"),
                BorderFactory.createEmptyBorder(30, 0, 0, 0) // add Margin
        ));

        IceRink iceRink = IceRink.getInstance();

        skatesLabel = new JLabel("Skates: " + iceRink.getSkates().size());
        skatesLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        helmetLabel = new JLabel("Helmets: " + iceRink.getHelmets().size());
        helmetLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        glovesLabel = new JLabel("Gloves: " + iceRink.getGloves().size());
        glovesLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        penguinLabel = new JLabel("Penguins: " + iceRink.getPenguins().size());
        penguinLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        waitingVisitorsLabel = new JLabel("Visitors waiting for items: " +
                QueuePanel.getInstance().getVisitors().size());
        waitingVisitorsLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        skatingVisitorsLabel = new JLabel("Skating visitors: " +
                SkatingArea.getSkaters().size());
        skatingVisitorsLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        waitingDiningVisitorsLabel = new JLabel("Visitors waiting for dining hall: "  +
                DiningHall.getInstance().getWaitingVisitorsCount());
        waitingDiningVisitorsLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        diningVisitorsLabel = new JLabel("Visitors in dining hall: " +
                DiningHall.getInstance().getVisitors().size());
        diningVisitorsLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        add(skatesLabel);
        add(helmetLabel);
        add(glovesLabel);
        add(penguinLabel);
        add(skatingVisitorsLabel);
        add(waitingVisitorsLabel);
        add(waitingDiningVisitorsLabel);
        add(diningVisitorsLabel);

        //
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(0, 1, 10, 5));
        inputPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Settings"),
                BorderFactory.createEmptyBorder(10, 0, 10, 0)
        ));

        numVisitorsLabel = new JLabel("Number of Visitors: " + App.getVisitors().size());
        JPanel visitorButtonPanel = new JPanel();
        JButton addVisitorButton = new JButton("add");
        addVisitorButton.addActionListener(e -> {
            App.addVisitor();
            updateNumVisitors();
        });
        visitorButtonPanel.add(addVisitorButton);

        JPanel visitorFieldPanel = new JPanel();
        visitorFieldPanel.setPreferredSize(new Dimension(1500, 40));
        visitorFieldPanel.add(numVisitorsLabel);
        visitorFieldPanel.add(visitorButtonPanel);

        capacityLabel = new JLabel("Dining Hall Capacity: " + App.getDiningHallCapacity());
        JPanel capacityButtonPanel = new JPanel();
        JButton addCapacityButton = new JButton("increase");
        addCapacityButton.addActionListener(e -> {
            App.increaseDiningHallCapacity();
            updateCapacity();
//            DiningHall.getInstance().incrementSemaphore();
//            DiningHall.getInstance().updateSemaphore();
        });
        JButton removeCapacityButton = new JButton("lower");
        removeCapacityButton.addActionListener(e -> {
            App.decreaseDiningHallCapacity();
            updateCapacity();

//                DiningHall.getInstance().decrementSemaphore();
//                DiningHall.getInstance().updateSemaphore();

        });
        capacityButtonPanel.add(addCapacityButton);
        capacityButtonPanel.add(removeCapacityButton);

        JPanel capacityFieldPanel = new JPanel();
        capacityFieldPanel.setPreferredSize(new Dimension(1500, 40));
        capacityFieldPanel.add(capacityLabel);
        capacityFieldPanel.add(capacityButtonPanel);

        JSeparator separator = new JSeparator();

        numSkatesLabel = new JLabel("Number of Skates: " + iceRink.getSkates().size());

        JPanel skatesButtonPanel = new JPanel();
        JButton addSkatesButton = new JButton("add");
        addSkatesButton.addActionListener(e -> {
            IceRink.getInstance().addSkate();
        });
        JButton removeSkatesButton = new JButton("remove");
        removeSkatesButton.addActionListener(e -> {
            IceRink.getInstance().removeSkate();
        });
        skatesButtonPanel.add(addSkatesButton);
        skatesButtonPanel.add(removeSkatesButton);

        JPanel skatesFieldPanel = new JPanel();
        skatesFieldPanel.setPreferredSize(new Dimension(1500, 40));
        skatesFieldPanel.add(numSkatesLabel);
        skatesFieldPanel.add(skatesButtonPanel);

        numHelmetsLabel = new JLabel("Number of Helmets: " + iceRink.getHelmets().size());

        JPanel helmetsButtonPanel = new JPanel();
        JButton addHelmetsButton = new JButton("add");
        addHelmetsButton.addActionListener(e -> {
            IceRink.getInstance().addHelmet();
        });
        JButton removeHelmetsButton = new JButton("remove");
        removeHelmetsButton.addActionListener(e -> {
            IceRink.getInstance().removeHelmet();
        });
        helmetsButtonPanel.add(addHelmetsButton);
        helmetsButtonPanel.add(removeHelmetsButton);

        JPanel helmetsFieldPanel = new JPanel();
        helmetsFieldPanel.setPreferredSize(new Dimension(1500, 40));
        helmetsFieldPanel.add(numHelmetsLabel);
        helmetsFieldPanel.add(helmetsButtonPanel);

        numGlovesLabel = new JLabel("Number of Gloves: " + iceRink.getGloves().size());

        JPanel glovesButtonPanel = new JPanel();
        JButton addGlovesButton = new JButton("add");
        addGlovesButton.addActionListener(e -> {
            IceRink.getInstance().addGlove();
        });
        JButton removeGlovesButton = new JButton("remove");
        removeGlovesButton.addActionListener(e -> {
            IceRink.getInstance().removeGlove();
        });
        glovesButtonPanel.add(addGlovesButton);
        glovesButtonPanel.add(removeGlovesButton);

        JPanel glovesFieldPanel = new JPanel();
        glovesFieldPanel.setPreferredSize(new Dimension(1500, 40));
        glovesFieldPanel.add(numGlovesLabel);
        glovesFieldPanel.add(glovesButtonPanel);

        numPenguinLabel = new JLabel("Number of Penguins: " + iceRink.getPenguins().size());

        JPanel penguinButtonPanel = new JPanel();
        JButton addPenguinButton = new JButton("add");
        addPenguinButton.addActionListener(e -> {
            IceRink.getInstance().addPenguin();
        });
        JButton removePenguinButton = new JButton("remove");
        removePenguinButton.addActionListener(e -> {
            IceRink.getInstance().removePenguin();
        });
        penguinButtonPanel.add(addPenguinButton);
        penguinButtonPanel.add(removePenguinButton);

        JPanel penguinFieldPanel = new JPanel();
        penguinFieldPanel.setPreferredSize(new Dimension(1500, 40));
        penguinFieldPanel.add(numPenguinLabel);
        penguinFieldPanel.add(penguinButtonPanel);

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

    public static void updateNumVisitors() {
        numVisitorsLabel.setText("Number of Visitors: " + App.getVisitors().size());
    }

    public static void updateCapacity() {
        capacityLabel.setText("Dining Hall Capacity: " + App.getDiningHallCapacity());
    }

    public static void updateNumSkates() {
        numSkatesLabel.setText("Number of Skates: " + IceRink.getInstance().getSkates().size());
    }

    public static void updateNumHelmets() {
        numHelmetsLabel.setText("Number of Helmets: " + IceRink.getInstance().getHelmets().size());
    }

    public static void updateNumGloves() {
        numGlovesLabel.setText("Number of Gloves: " + IceRink.getInstance().getGloves().size());
    }

    public static void updateNumPenguins() {
        numPenguinLabel.setText("Number of Penguins: " + IceRink.getInstance().getPenguins().size());
    }


}
