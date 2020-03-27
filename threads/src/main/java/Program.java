import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.*;

class Production {
    int i = 0;
}

class Producer implements Runnable {
    Lenta lenta;

    Producer(Lenta lenta) {
        this.lenta = lenta;
    }
    @Override
    public void run() {
        for(int i = 0; i < 5; i++)
            lenta.fill();
    }
}

class Consumer implements Runnable {
    Lenta lenta;
    Consumer(Lenta lenta) {
        this.lenta = lenta;
    }
    @Override
    public void run() {
        for(int i = 0; i < 5; i++)
            lenta.consume();
    }
}

class Lenta {
    Production prod = new Production();
    public synchronized void fill() {
        if (prod.i > 4) {
            try {
                notify();
                wait();
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
        }
            prod.i++;
            try {
                sleep(10);
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
            System.out.println("Склад пополнен: " + prod.i);
    }
    public synchronized void consume() {
        if (prod.i < 1) {
            try {
                notify();
                wait();
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
        }
        try {
            sleep(10);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
        prod.i--;
        System.out.println("Товар куплен: " + prod.i);

    }
}

class ThreadInc implements Runnable {
    Value value;
    ThreadInc(Value val) {
        this.value = val;
    }
    @Override
    public void run() {
       synchronized (value) {
            for (int i = 0; i < 500; i++) {
                this.value.v = this.value.v + 1;
            }
       }
    }
}

class ThreadIncLOCK implements Runnable {
    Value value;
    ReentrantLock lock;
    ThreadIncLOCK (Value val, ReentrantLock lock) {
        this.value = val;
        this.lock = lock;
    }
    @Override
    public void run() {
        this.lock.lock();
        for (int i = 0; i < 500; i++) {
            this.value.v = this.value.v + 1;
        }
        this.lock.unlock();
    }
}


class Value {
    int v = 0;
}

public class Program {
    public static void main(String[] args) throws InterruptedException {
       Lenta lenta = new Lenta();
        Thread t2 = new Thread(new Consumer(lenta));
        sleep(1);
        Thread t1 = new Thread(new Producer(lenta));
        t1.start();
        t2.start();
        sleep(1000);
        t1.join();
        t2.join();


        Value value = new Value();  // synchronized time
        long start = System.nanoTime();
        for (int i = 0; i < 30000; i++) {
            new Thread(new ThreadInc(value)).start();
        }
        Thread.sleep(1000);
        long finish = System.nanoTime();
        System.out.println("Value: " + value.v);
        System.out.println("Synchronized-way Time elapsed: " + (finish - start - 1000*1000000)/1000000000f);

        Value value1 = new Value(); // ReentrantLock time
        ReentrantLock lock = new ReentrantLock();
        start = System.nanoTime();
        for (int i = 0; i < 30000; i++) {
            new Thread(new ThreadIncLOCK(value1, lock)).start();
        }
        Thread.sleep(1000);
        finish = System.nanoTime();
        System.out.println("Value: " + value1.v);
        System.out.println("ReentrantLock-way Time elapsed: " + (finish - start - 1000*1000000)/1000000000f);
    }
}
