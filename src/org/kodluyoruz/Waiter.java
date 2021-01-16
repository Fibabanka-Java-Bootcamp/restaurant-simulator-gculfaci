package org.kodluyoruz;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Waiter implements Runnable {

    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();

    private Restaurant restaurant;

    public Waiter(Restaurant r) {
        restaurant = r;
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                lock.lock();
                try {
                    while (restaurant.order == null) {
                        condition.await(); 
                    }
                } finally {
                    lock.unlock();
                }
                System.out.println("Waiter got " + restaurant.order + " --- " + Thread.currentThread().getName());

                restaurant.chef.lock.lock();
                restaurant.chef2.lock.lock();
                try {
                    restaurant.order = null;
                    System.out.println("Meal taken by the waitperson!");
                    restaurant.chef.condition.signalAll(); 
                    restaurant.chef2.condition.signalAll();
                } finally {
                    restaurant.chef.lock.unlock();
                }

            }
        } catch (InterruptedException e) {
            System.out.println("Waiter Interrupted");
        }
    }
}
