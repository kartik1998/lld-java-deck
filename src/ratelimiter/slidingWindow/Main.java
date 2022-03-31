package ratelimiter.slidingWindow;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.TreeMap;

/*
    Implement a class that when passed an IP Address String returns boolean for request allowed.
    A request will be allowed if it's count is less than equal to 10 in past 1 minute.
*/
/*
   Bar raiser:
   With every request that comes one. You should also clean up the cache for old requests (To save space)
 */
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
    private HashMap<String, SlidingWindow> ipWindowMap = new HashMap<>();
    private int duration = 60; // duration in seconds
    private int maxWindowSize = 10; // max window size
    private TreeMap<String, Long> latestTimeStampMap = new TreeMap<>();
    public int cleanupCount = 100;

    public RateLimiter() {
    }

    public RateLimiter(int duration, int maxWindowSize) {
        this.duration = duration;
        this.maxWindowSize = maxWindowSize;
    }

    public synchronized boolean grantAccess(String ip) {
        long currentTime = System.currentTimeMillis();
        cleanupOldRequests(currentTime);
        boolean flag = false;
        if (!ipWindowMap.containsKey(ip)) {
            SlidingWindow window = new SlidingWindow(duration, maxWindowSize);
            ipWindowMap.put(ip, window);
            flag = window.grantAccess(currentTime);
        } else {
            SlidingWindow window = ipWindowMap.get(ip);
            flag = window.grantAccess(currentTime);
        }
        if (flag) latestTimeStampMap.put(ip, currentTime);
        return flag;
    }

    private void cleanupOldRequests(long currentTimeStamp) {
        int count = cleanupCount;
        for (String ip : latestTimeStampMap.keySet()) {
            if (currentTimeStamp - latestTimeStampMap.get(ip) > duration) {
                latestTimeStampMap.remove(ip);
                ipWindowMap.remove(ip);
            } else break;
            count--;
            if (count <= 0) break;
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
