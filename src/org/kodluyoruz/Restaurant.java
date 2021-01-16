package org.kodluyoruz;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Restaurant {

    Order order;
    ExecutorService exec = Executors.newCachedThreadPool();
    Waiter waitPerson = new Waiter(this);
    Waiter waitPerson2 = new Waiter(this);
    Waiter waitPerson3 = new Waiter(this);
    Chef chef = new Chef(this);
    Chef chef2 = new Chef(this);
    

    public Restaurant() {
        exec.execute(chef);
        exec.execute(chef2);
        exec.execute(waitPerson);
        exec.execute(waitPerson2);
        exec.execute(waitPerson3);
    }

    public static void main(String[] args) {
        new Restaurant();
    }

}



