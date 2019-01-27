package learning;

import cn.hutool.core.thread.NamedThreadFactory;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author guyue
 * @date 2019-01-26
 */
public class ThreadTest {
    private static final int CAPACITY = 100;
    private static Queue<Goods> queue = new LinkedList<>();

    static boolean full() {
        return queue.size() >= CAPACITY;
    }

    static boolean empty() {
        return queue.isEmpty();
    }

    static void product() {
        queue.offer(Goods.product());
        System.out.println("product " + queue.size());
    }

    static void consume() {
        queue.poll();
        System.out.println("consume " + queue.size());
    }

    public static void main(String... args) {
        int pSize = 1;
        int cSize = 10;
        for (int i = 0; i < CAPACITY; i++) {
            product();
        }
        var producer = new ThreadPoolExecutor(1, pSize, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(), new NamedThreadFactory("Producer-", false));
        var consumer = new ThreadPoolExecutor(1, cSize, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(), new NamedThreadFactory("consumer-", false));
        for (int i = 0; i < cSize; i++) {
            consumer.execute(new Consumer());
        }
        for (int i = 0; i < pSize; i++) {
            producer.execute(new Producer());
        }
    }
}

class Goods {
    static Goods product() {
        return new Goods();
    }
}

class Producer implements Runnable {
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(500L);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
            if (ThreadTest.full()) {
                //block it
                try {
                    System.out.println(Thread.currentThread().getName() + " sleeping.");
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                ThreadTest.product();
            }
        }
    }
}

class Consumer implements Runnable {
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(500L);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
            if (ThreadTest.empty()) {
                try {
                    System.out.println(Thread.currentThread().getName() + " sleeping.");
                    Thread.sleep(100L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                ThreadTest.consume();
            }
        }
    }
}
