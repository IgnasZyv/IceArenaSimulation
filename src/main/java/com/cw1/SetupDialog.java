package com.cw1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SetupDialog extends JDialog implements ActionListener {

    private final JTextField numVisitorsField;
    private final JTextField diningHallCapacityField;
    private final JTextField numSkatesField;
    private final JTextField numHelmetsField;
    private final JTextField numGlovesField;
    private final JTextField numPenguinsField;

    private final JButton startButton;

    public SetupDialog(JFrame parent) {
        super(parent, "Setup Simulation", true);

        // Create the necessary components for the dialog
        JLabel numVisitorsLabel = new JLabel("Number of Visitors:");
        numVisitorsField = new JTextField(10);
        JLabel diningHallCapacityLabel = new JLabel("Dining Hall Capacity:");
        diningHallCapacityField = new JTextField(10);
        JLabel numSkatesLabel = new JLabel("Number of Skates:");
        numSkatesField = new JTextField(10);
        JLabel numHelmetsLabel = new JLabel("Number of Helmets:");
        numHelmetsField = new JTextField(10);
        JLabel numGlovesLabel = new JLabel("Number of Gloves:");
        numGlovesField = new JTextField(10);
        JLabel numPenguinLabel = new JLabel("Number of Penguins:");
        numPenguinsField = new JTextField(10);

        startButton = new JButton("Start");
        startButton.addActionListener(this);
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(this);

        // Create the layout for the dialog
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(5, 5, 5, 5);

        // Add the components to the layout
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(numVisitorsLabel, constraints);

        constraints.gridx = 1;
        panel.add(numVisitorsField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(diningHallCapacityLabel, constraints);

        constraints.gridx = 1;
        panel.add(diningHallCapacityField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(numSkatesLabel, constraints);

        constraints.gridx = 1;
        panel.add(numSkatesField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        panel.add(numHelmetsLabel, constraints);

        constraints.gridx = 1;
        panel.add(numHelmetsField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        panel.add(numGlovesLabel, constraints);

        constraints.gridx = 1;
        panel.add(numGlovesField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 5;
        panel.add(numPenguinLabel, constraints);

        constraints.gridx = 1;
        panel.add(numPenguinsField, constraints);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startButton);
        buttonPanel.add(cancelButton);

        // Add the layout to the dialog
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.PAGE_END);

        // Set the dialog size and make it visible
        pack();
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            setVisible(false);
            return;
        } else {
            System.exit(0);
        }
    }

    public int getNumVisitors() {
        String input = numVisitorsField.getText();
        if (input.isEmpty()) {
            return 0;
        } else if (!input.matches("\\d+")) { // if not a number
            return 0;
        } else {
            return Integer.parseInt(input);
        }
    }

    public int getDiningHallCapacity() {
        String input = diningHallCapacityField.getText();
        if (input.isEmpty()) {
            return 0;
        } else if (!input.matches("\\d+")) { // if not a number
            return 0;
        } else {
            return Integer.parseInt(input);
        }
    }

    public int getNumSkates() {
        String input = numSkatesField.getText();
        if (input.isEmpty()) {
            return 0;
        } else if (!input.matches("\\d+")) { // if not a number
            return 0;
        } else {
            return Integer.parseInt(input);
        }
    }

    public int getNumHelmets() {
        String input = numHelmetsField.getText();
        if (input.isEmpty()) {
            return 0;
        } else if (!input.matches("\\d+")) { // if not a number
            return 0;
        } else {
            return Integer.parseInt(input);
        }
    }

    public int getNumGloves() {
        String input = numGlovesField.getText();
        if (input.isEmpty()) {
            return 0;
        } else if (!input.matches("\\d+")) { // if not a number
            return 0;
        } else {
            return Integer.parseInt(input);
        }
    }

    public int getNumPenguins() {
        String input = numPenguinsField.getText();
        if (input.isEmpty()) {
            return 0;
        } else if (!input.matches("\\d+")) { // if not a number
            return 0;
        } else {
            return Integer.parseInt(input);
        }
    }

}
