package com.cw1;

import com.cw1.enums.SimSpeed;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class App {
    public static int NUMBER_OF_VISITORS = 0;
    public static int DINING_HALL_CAPACITY = 0;
    public static int SKATES_NUM = 0;
    public static int HELMETS_NUM = 0;
    public static int GLOVES_NUM = 0;
    public static int PENGUINS_NUM = 0;
    public static SimSpeed processingSpeed = SimSpeed.FAST;
    public static SimSpeed skatingDiningSpeed = SimSpeed.FAST;
    private static List<Visitor> visitors;
    private static Outlet outlet;
    private static SimulationFrame simulationFrame;
    private static final SetupDialog setupDialog = new SetupDialog(new JFrame());

    public static void main(String[] args) {
        // Create and display the setup dialog
        setupDialog.setVisible(true);

        // Retrieve the selected parameters and start the simulation
        NUMBER_OF_VISITORS = setupDialog.getNumVisitors();
        DINING_HALL_CAPACITY = setupDialog.getDiningHallCapacity();
        SKATES_NUM = setupDialog.getNumSkates();
        HELMETS_NUM = setupDialog.getNumHelmets();
        GLOVES_NUM = setupDialog.getNumGloves();
        PENGUINS_NUM = setupDialog.getNumPenguins();


        IceRink iceRink = IceRink.getInstance();
        SkatingArea skatingArea = SkatingArea.getInstance();
        outlet = new Outlet(iceRink);

        OutletThread outletThread = OutletThread.getInstance();
        outletThread.start(); // start the outlet thread to process waiting for orders

        visitors = new ArrayList<>(); // create the visitors
        for (int i = 0; i < NUMBER_OF_VISITORS; i++) {
            Visitor visitor = new Visitor("Visitor " + i);
            visitor.setOutlet(outlet);
            visitor.setIceArena(iceRink);
            visitor.setSkatingArea(skatingArea);
            visitors.add(visitor);

        }

        simulationFrame = new SimulationFrame(iceRink); // start the simulation frame

        // start the visitors
        for (Visitor visitor : visitors) {
            new Thread(visitor).start();
        }

        JFrame frame = new JFrame("Skating Frame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1600, 1000);
        frame.add(simulationFrame);
        frame.setVisible(true);
    }

    public static List<Visitor> getVisitors() {
        return visitors;
    }

    public static Outlet getOutlet() {
        return outlet;
    }

    public static int getDiningHallCapacity() {
        return DINING_HALL_CAPACITY;
    }

    public static void addVisitor() {
        Visitor visitor = new Visitor("Visitor " + visitors.size());
        visitors.add(visitor);
        new Thread(visitor).start(); // add and start a new visitor
    }

    public static SimSpeed getProcessingSpeed() {
        return processingSpeed;
    }

    public static void setProcessingSpeed(SimSpeed processingSpeed) {
        App.processingSpeed = processingSpeed;
    }

    public static void setSkatingDiningSpeed(SimSpeed skatingDiningSpeed) {
        App.skatingDiningSpeed = skatingDiningSpeed;
    }

    public static int processingSleep() { // the time it takes to process an order
        if (processingSpeed == SimSpeed.SLOW) {
            return new Random().nextInt(3000, 5000);
        } else if (processingSpeed == SimSpeed.MEDIUM) {
            return new Random().nextInt(2000, 3000);
        } else {
            return new Random().nextInt(500, 1500);
        }
    }

    public static int skatingDiningSleep() { // the time it takes to skate or dine
        if (skatingDiningSpeed == SimSpeed.SLOW) {
            return new Random().nextInt(15000, 20000);
        } else if (skatingDiningSpeed == SimSpeed.MEDIUM) {
            return new Random().nextInt(10000, 15000);
        } else {
            return new Random().nextInt(5000, 8000);
        }
    }
}
