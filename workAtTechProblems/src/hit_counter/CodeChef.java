package hit_counter;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


class Impl {
    ConcurrentHashMap<Character, Queue<Integer>> map = new ConcurrentHashMap<>();
    AtomicInteger count = new AtomicInteger(0);

    void record(char ch, int timestamp) {
        Queue<Integer> q = map.computeIfAbsent(ch, k -> new LinkedList<>());
        synchronized (q) {
            while (!q.isEmpty() && q.peek() < timestamp - 300) {
                q.remove();
                count.decrementAndGet();
            }
            q.add(timestamp);
            count.incrementAndGet();
        }
    }

    int getCount(char ch) {
        Queue<Integer> q = map.get(ch);
        if (q == null) return 0;
        synchronized (q) {
            return q.size();
        }
    }

    int getTotalCount() {
        return count.get();
    }
}

class Codechef {
    public static void main(String[] args) throws java.lang.Exception {
        // your code goes here

    }
}
