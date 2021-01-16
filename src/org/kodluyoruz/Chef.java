package org.kodluyoruz;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Chef implements Runnable {

    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();
    private Restaurant restaurant;
    private int count = 0;
    int numberOfOrders = 10;

    public Chef(Restaurant r) {
        restaurant = r;
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                lock.lock();
                try {

                    while (restaurant.order != null) {
                        condition.await();
                    }
                } finally {
                    lock.unlock();
                }

                if (++count == numberOfOrders) {
                    System.out.println("Restaurant closed");
                    restaurant.exec.shutdownNow();
                    return;
                }
                System.out.println("Order up ----- "+Thread.currentThread().getName());
                restaurant.waitPerson.lock.lock();
                //restaurant.waitPerson2.lock.lock();
                //restaurant.waitPerson3.lock.lock();
                try {
                    restaurant.order = new Order(count,5);
                    restaurant.waitPerson.condition.signalAll();
                    //restaurant.waitPerson2.condition.signalAll();
                    //restaurant.waitPerson3.condition.signalAll();
                } finally {
                    restaurant.waitPerson.lock.unlock();
                    //restaurant.waitPerson2.lock.unlock();
                    //restaurant.waitPerson3.lock.unlock();
                }
                TimeUnit.MILLISECONDS.sleep(100);
            }
        } catch (InterruptedException e) {
            System.out.println("Chef interrupted!");
        }
    }
}