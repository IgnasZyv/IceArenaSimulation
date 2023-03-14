package com.cw1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SetupDialog extends JDialog implements ActionListener {

    private JTextField numVisitorsField;
    private JTextField diningHallCapacityField;
    private JTextField numSkatesField;
    private JTextField numHelmetsField;
    private JTextField numGlovesField;
    private JTextField numPenguinsField;

    private JButton startButton;
    private JButton cancelButton;

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
        cancelButton = new JButton("Cancel");
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
            synchronized (this) {
                notifyAll();
            }
            return;
        }
    }

    public JTextField getNumVisitorsField() {
        return numVisitorsField;
    }

    public int getNumVisitors() {
        return Integer.parseInt(numVisitorsField.getText());
    }

    public JTextField getDiningHallCapacityField() {
        return diningHallCapacityField;
    }

    public int getDiningHallCapacity() {
        return Integer.parseInt(diningHallCapacityField.getText());
    }

    public JTextField getNumSkatesField() {
        return numSkatesField;
    }

    public int getNumSkates() {
        return Integer.parseInt(numSkatesField.getText());
    }

    public JTextField getNumHelmetsField() {
        return numHelmetsField;
    }

    public int getNumHelmets() {
        return Integer.parseInt(numHelmetsField.getText());
    }

    public JTextField getNumGlovesField() {
        return numGlovesField;
    }

    public int getNumGloves() {
        return Integer.parseInt(numGlovesField.getText());
    }

    public JTextField getNumPenguinsField() {
        return numPenguinsField;
    }

    public int getNumPenguins() {
        return Integer.parseInt(numPenguinsField.getText());
    }

    public JButton getStartButton() {
        return startButton;
    }

    public void setStartButton(JButton startButton) {
        this.startButton = startButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }

    public void setCancelButton(JButton cancelButton) {
        this.cancelButton = cancelButton;
    }
}
