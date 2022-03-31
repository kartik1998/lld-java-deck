package ratelimiter.slidingWindow;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

class SlidingWindow {
    private Queue<Long> timestamps = new LinkedList<Long>();
    private int duration = 60; // duration in seconds
    private int maxWindowSize = 10; // max window size

    public SlidingWindow() {
    }

    public SlidingWindow(int duration, int maxWindowSize) {
        this.duration = duration;
        this.maxWindowSize = maxWindowSize;
    }

    public boolean grantAccess(long currentTime) {
        while (!timestamps.isEmpty()) {
            long peek = timestamps.peek();
            if (currentTime - peek > duration) {
                timestamps.remove();
            } else {
                break;
            }
        }
        if (timestamps.size() < maxWindowSize) {
            timestamps.add(currentTime);
            return true;
        }
        return false;
    }
}

class RateLimiter {
    private HashMap<String, SlidingWindow> map = new HashMap<>();
    private int duration = 60; // duration in seconds
    private int maxWindowSize = 10; // max window size

    public RateLimiter() {
    }

    public RateLimiter(int duration, int maxWindowSize) {
        this.duration = duration;
        this.maxWindowSize = maxWindowSize;
    }

    public synchronized boolean grantAccess(String ip) {
        long currentTime = System.currentTimeMillis();
        if (!map.containsKey(ip)) {
            SlidingWindow window = new SlidingWindow(duration, maxWindowSize);
            map.put(ip, window);
            return window.grantAccess(currentTime);
        } else {
            SlidingWindow window = map.get(ip);
            return window.grantAccess(currentTime);
        }
    }
}

public class Main {
    public static void main(String[] args) throws InterruptedException {
        RateLimiter rateLimiter = new RateLimiter();
        for (int i = 0; i < 15; i++) {
            System.out.println(rateLimiter.grantAccess("10.0.0.1"));
        }
        Thread.sleep(1000);
        System.out.println(rateLimiter.grantAccess("10.0.0.1"));
        System.out.println(rateLimiter.grantAccess("10.0.0.1"));
        System.out.println(rateLimiter.grantAccess("10.0.0.1"));
    }
}
