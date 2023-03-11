package com.cw1;

import javax.swing.*;
import java.util.HashMap;

public class StatisticsPanel extends JPanel {
    private static JLabel stockLeftLabel;
    private static JLabel skatingVisitorsLabel;
    private static JLabel waitingVisitorsLabel;
    private static JLabel skatesLabel;
    private static JLabel helmetLabel;
    private static JLabel glovesLabel;
    private static JLabel penguinLabel;

    public StatisticsPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Statistics"),
                BorderFactory.createEmptyBorder(30, 50, 0, 0) // add Margin
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

        skatingVisitorsLabel = new JLabel("Skating visitors: 0");
        skatingVisitorsLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        waitingVisitorsLabel = new JLabel("Waiting visitors: 0");
        waitingVisitorsLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        add(skatesLabel);
        add(helmetLabel);
        add(glovesLabel);
        add(penguinLabel);
        add(skatingVisitorsLabel);
        add(waitingVisitorsLabel);
    }

    public static void updateItems(HashMap<ItemType, Integer> itemQuantity) {
        skatesLabel.setText("Skates: " + itemQuantity.get(ItemType.SKATES));
        helmetLabel.setText("Helmets: " + itemQuantity.get(ItemType.HELMET));
        glovesLabel.setText("Gloves: " + itemQuantity.get(ItemType.GLOVES));
        penguinLabel.setText("Penguins: " + itemQuantity.get(ItemType.PENGUIN));

    }

}
