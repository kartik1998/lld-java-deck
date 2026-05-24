package uber_problems.hit_counter;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class Main {
    interface Counter {
        void recordClick(String page, int timestamp);

        int getRecentClicks(String page, int timestamp);

        int getAllRecentClicks(int timestamp);
    }

    static class CounterImpl implements Counter {
        Map<String, Queue<Integer>> map = new HashMap<>();
        Object recordLock = new Object();
        private int totalCount = 0;

        @Override
        public void recordClick(String page, int timestamp) {
            synchronized (recordLock) {
                if (!map.containsKey(page)) map.put(page, new LinkedList<>());
            }
            evictStaleEntries(page, timestamp);
            synchronized (recordLock) {
                map.get(page).add(timestamp);
                totalCount++;
            }
        }

        @Override
        public int getRecentClicks(String page, int timestamp) {
            evictStaleEntries(page, timestamp);
            return map.getOrDefault(page, new LinkedList<>()).size();
        }

        @Override
        public int getAllRecentClicks(int timestamp) {
            evictAllStaleEntries(timestamp);
            return totalCount;
        }

        private void evictStaleEntries(String page, int timestamp) {
            Queue<Integer> q = map.get(page);
            if (q == null) return;
            synchronized (q) {
                while (!q.isEmpty() && timestamp - q.peek() > 300) {
                    q.remove();
                    totalCount--;
                }
            }
        }

        private void evictAllStaleEntries(int timestamp) {
            for (String page : map.keySet()) {
                evictStaleEntries(page, timestamp);
            }
        }
    }

    public static void main(String[] args) {
    }
}
