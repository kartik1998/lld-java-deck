package multi_threading;

import java.util.PriorityQueue;

public class ThreadSafeDeferredCallbackProblem {
    static class Callback<T> {
        T msg;
        long executionTime;

        public Callback(T msg, long executionTime) {
            this.msg = msg;
            this.executionTime = executionTime;
        }
    }

    static class ThreadSafeDeferredCallback<T> {
        private Object lock = new Object();

        private PriorityQueue<Callback> q = new PriorityQueue<>((a, b) -> {
            return Long.compare(a.executionTime, b.executionTime);
        });

        public ThreadSafeDeferredCallback() {
            Thread t1 = new Thread(() -> {
                while (true) {
                    synchronized (lock) {
                        while (q.isEmpty()) {
                            try {
                                lock.wait();
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }

                        Callback callback = q.peek();
                        long diff = callback.executionTime - System.currentTimeMillis();
                        if (diff <= 0) {
                            // execute now
                            q.remove();
                            System.out.println(String.format("Executed %s at %s", callback.msg, callback.executionTime));
                        } else {
                            // wait for diff?
                            try {
                                lock.wait(diff);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }
            });
            t1.start();
        }

        public void enqueue(T msg, long executionTime) {
            synchronized (lock) {
                q.add(new Callback(msg, executionTime));
                lock.notifyAll();
            }
        }
    }
}
