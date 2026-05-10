package multi_threading;

import java.util.LinkedList;
import java.util.Queue;

public class BoundedBufferQueueProblem {
    static class BoundedQueue<T> {
        private Queue<T> q = new LinkedList<>();
        private int capacity;
        private Object lock = new Object();

        public BoundedQueue(int capacity) {
            this.capacity = capacity;
        }

        public void enqueue(T msg) throws InterruptedException {
            synchronized (lock) {
                while (capacity == q.size()) {
                    lock.wait();
                }

                q.add(msg);
                lock.notifyAll();
                System.out.println("ENQUEUE msg=" + msg.toString());
            }
        }

        public void deque() throws InterruptedException {
            synchronized (lock) {
                while (q.isEmpty()) {
                    lock.wait();
                }

                T msg = q.remove();
                lock.notifyAll();
                System.out.println("DEQUE msg=" + msg);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        BoundedQueue<Integer> q = new BoundedQueue<>(3);
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    q.enqueue(i);
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    q.enqueue(i);
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        Thread t3 = new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                try {
                    q.deque();
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();
    }
}
