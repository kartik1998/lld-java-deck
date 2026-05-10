package multi_threading;

public class SemaphoreProblem {
    static class Semaphore {
        private Object lock = new Object();
        int capacity;
        int acquired = 0;

        public Semaphore(int capacity) {
            this.capacity = capacity;
        }

        public void acquire() throws InterruptedException {
            synchronized (lock) {
                while (acquired == capacity) {
                    lock.wait();
                }

                acquired++;
            }
        }

        public void release() {
            synchronized (lock) {
                if(acquired == 0) {
                    throw new IllegalStateException("No acquired lock to release");
                }
                acquired--;
                lock.notifyAll();
            }
        }
    }
}
