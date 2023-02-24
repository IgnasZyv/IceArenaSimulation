package com.cw1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.cw1.Visitor.getItems;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
//        System.out.println("Hello World!");

//        Counter counter = new Counter();
//        Thread t1 = new Thread(counter);
//        t1.start();
        IceArena iceArena = IceArena.getInstance(10);

        List<Visitor> visitors = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Visitor visitor = new Visitor("Visitor " + i);
            visitor.setOutlet(new Outlet(iceArena, visitor));
            visitor.setIceArena(iceArena);
            visitors.add(visitor);
            new Thread(visitor).start();
        }

//        List<Thread> threads = new ArrayList<>();
//        for (Visitor visitor : visitors) {
//            Outlet outlet = new Outlet(iceArena, visitor);
//            visitor.setOutlet(outlet);
//            Runnable runnable = outlet::processOrders;
//            Thread thread = new Thread(runnable);
//            threads.add(thread);
//        }
//
//        for (Visitor visitor : visitors) {
//            List<Item> items = getItems();
//            Order order = new Order(items, visitor);
//            visitor.placeOrder(order);
//        }
//
//        for (Thread thread : threads) {
//            thread.start();
//        }

    }

}
