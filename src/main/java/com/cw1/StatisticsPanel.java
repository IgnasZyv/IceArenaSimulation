package com.cw1;

import com.cw1.enums.ItemType;
import com.cw1.enums.SimSpeed;

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
    private static JLabel numVisitorsLabel;
    private static JLabel capacityLabel;

    public StatisticsPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Statistics"),
                BorderFactory.createEmptyBorder(10, 0, 0, 0) // add Margin
        ));

        IceRink iceRink = IceRink.getInstance();

        skatesLabel = new JLabel("Skates: " + iceRink.getSkates().size());

        helmetLabel = new JLabel("Helmets: " + iceRink.getHelmets().size());

        glovesLabel = new JLabel("Gloves: " + iceRink.getGloves().size());

        penguinLabel = new JLabel("Penguins: " + iceRink.getPenguins().size());

        waitingVisitorsLabel = new JLabel("Visitors waiting for items: " +
                QueuePanel.getInstance().getVisitors().size());

        skatingVisitorsLabel = new JLabel("Skating visitors: " +
                SkatingArea.getSkaters().size());

        waitingDiningVisitorsLabel = new JLabel("Waiting for dining hall: " +
                DiningHall.getInstance().getWaitingVisitorsCount());

        diningVisitorsLabel = new JLabel("Visitors in dining hall: " +
                DiningHall.getInstance().getVisitors().size());


        JPanel stockPanel = new JPanel();
        stockPanel.setAlignmentX(Component.LEFT_ALIGNMENT);


        stockPanel.add(skatesLabel);
        stockPanel.add(Box.createVerticalStrut(10));
        stockPanel.add(helmetLabel);
        stockPanel.add(Box.createVerticalStrut(10));
        stockPanel.add(glovesLabel);
        stockPanel.add(Box.createVerticalStrut(10));
        stockPanel.add(penguinLabel);
        stockPanel.add(Box.createVerticalStrut(10));
        stockPanel.add(skatingVisitorsLabel);
        stockPanel.add(Box.createVerticalStrut(10));
        stockPanel.add(waitingVisitorsLabel);
        stockPanel.add(Box.createVerticalStrut(10));
        stockPanel.add(waitingDiningVisitorsLabel);
        stockPanel.add(Box.createVerticalStrut(10));
        stockPanel.add(diningVisitorsLabel);

        add(stockPanel);

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

        JSeparator separator = new JSeparator();

        JLabel numSkatesLabel = new JLabel("Number of Skates");

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

        JLabel numHelmetsLabel = new JLabel("Number of Helmets");

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

        JLabel numGlovesLabel = new JLabel("Number of Gloves");

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

        JLabel numPenguinLabel = new JLabel("Number of Penguins");

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

        inputPanel.add(visitorFieldPanel);

        inputPanel.add(separator);

        inputPanel.add(skatesFieldPanel);
        inputPanel.add(helmetsFieldPanel);
        inputPanel.add(glovesFieldPanel);
        inputPanel.add(penguinFieldPanel);

        JPanel notifyPanel = new JPanel();
        notifyPanel.setPreferredSize(new Dimension(10, 10));
        JButton notifyButton = new JButton("Notify");
        notifyButton.addActionListener(e -> {
            App.getOutlet().notifyOutlet();
        });
        notifyPanel.add(notifyButton);

        inputPanel.add(notifyPanel);

        JPanel speedLabelPanel = new JPanel();
//        speedLabelPanel.setPreferredSize(new Dimension(1500, 40));
        JLabel speedLabel = new JLabel("Simulation Speed: " + App.getProcessingSpeed());
        speedLabelPanel.add(speedLabel);

        JPanel speedPanel = new JPanel();
        speedPanel.setLayout(new GridLayout(0, 3));
        speedPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Speed"),
                BorderFactory.createEmptyBorder(0, 0, 0, 0)
        ));

        JPanel slowButtonPanel = new JPanel();
        slowButtonPanel.setPreferredSize(new Dimension(10, 10));
        JButton slowButton = new JButton("Slow");
        slowButton.addActionListener(e -> {
            App.setProcessingSpeed(SimSpeed.SLOW);
            App.setSkatingDiningSpeed(SimSpeed.SLOW);
            speedLabel.setText("Simulation Speed: " + App.getProcessingSpeed());
        });
        slowButtonPanel.add(slowButton);
        speedPanel.add(slowButtonPanel);

        JPanel normalButtonPanel = new JPanel();
        normalButtonPanel.setPreferredSize(new Dimension(10, 10));
        JButton normalButton = new JButton("Normal");
        normalButton.addActionListener(e -> {
            App.setProcessingSpeed(SimSpeed.MEDIUM);
            App.setSkatingDiningSpeed(SimSpeed.MEDIUM);
            speedLabel.setText("Simulation Speed: " + App.getProcessingSpeed());
        });
        normalButtonPanel.add(normalButton);
        speedPanel.add(normalButtonPanel);

        JPanel fastButtonPanel = new JPanel();
        fastButtonPanel.setPreferredSize(new Dimension(10, 10));
        JButton fastButton = new JButton("Fast");
        fastButton.addActionListener(e -> {
            App.setProcessingSpeed(SimSpeed.FAST);
            App.setSkatingDiningSpeed(SimSpeed.FAST);
            speedLabel.setText("Simulation Speed: " + App.getProcessingSpeed());
        });
        fastButtonPanel.add(fastButton);
        speedPanel.add(fastButtonPanel);

        add(inputPanel);
        add(speedLabelPanel);
        add(speedPanel);

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

}
