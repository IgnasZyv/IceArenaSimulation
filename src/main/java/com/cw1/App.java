package com.cw1;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;


public class App {
    public static final int NUMBER_OF_VISITORS = 20;
    public static final int DINING_HALL_CAPACITY = 3;
    private static List<Visitor> visitors;
    private static Outlet outlet;
    private static SimulationFrame simulationFrame;

    public static void main(String[] args) {

        SkatingArea skatingArea = SkatingArea.getInstance();
        IceArena iceArena = IceArena.getInstance();
        simulationFrame = new SimulationFrame(iceArena);
        outlet = new Outlet(iceArena);
        visitors = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_VISITORS; i++) {
            Visitor visitor = new Visitor("Visitor " + i);
            visitor.setOutlet(outlet);
            visitor.setIceArena(iceArena);
            visitor.setSkatingArea(skatingArea);
            visitors.add(visitor);
            new Thread(visitor).start();
        }
        skatingArea.setSimulationFrame(simulationFrame);
//        QueuePanel.getInstance().updateQueue(visitors);

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

    public static SimulationFrame getSimulationFrame() {
        return simulationFrame;
    }


}
