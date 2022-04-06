package ratelimiter.slidingWindow;

/*
    Implement a class that when passed an IP Address String returns boolean for request allowed.
    A request will be allowed if it's count is less than equal to 10 in past 1 minute.
*/
/*
   Bar raiser:
   With every request that comes one. You should also clean up the cache for old requests (To save space)
 */

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

class SlidingWindow {
    private long windowLength = 60; // 60 seconds by default
    private long maxRequests = 10; // 10 requests in windowLength by default
    private Queue<Long> timestampQueue = new LinkedList<>();

    SlidingWindow() {
    }

    public SlidingWindow(long windowLength, long maxRequests) {
        this.windowLength = windowLength;
        this.maxRequests = maxRequests;
    }

    public boolean add(long currentTime) {
        while (!timestampQueue.isEmpty()) {
            long oldestTimeStamp = timestampQueue.peek();
            if (currentTime - oldestTimeStamp > windowLength * 1000) {
                timestampQueue.remove();
            } else break;
        }
        if (timestampQueue.size() < maxRequests) {
            timestampQueue.add(currentTime);
            return true;
        }
        return false;
    }
}

class SlidingWindowRateLimiter {
    private long windowLength = 60;
    private long maxRequests = 10; // 10 requests in windowLength by default
    private HashMap<String, SlidingWindow> requestMap = new HashMap<>();

    public SlidingWindowRateLimiter(long windowLength, long maxRequests) {
        this.windowLength = windowLength;
        this.maxRequests = maxRequests;
    }

    public synchronized boolean grantAccess(String ip) {
        if (!requestMap.containsKey(ip)) requestMap.put(ip, new SlidingWindow(windowLength, maxRequests));
        long currentTime = System.currentTimeMillis();
        return requestMap.get(ip).add(currentTime);
    }
}

public class Main {
    public static void main(String[] args) throws InterruptedException {
        SlidingWindowRateLimiter rateLimiter = new SlidingWindowRateLimiter(12, 10);  // 10 requests in 12 seconds
        for (int i = 0; i < 15; i++) {
            System.out.println(rateLimiter.grantAccess("10.0.0.1"));
        }
        System.out.println("initiating thread sleep for 12 seconds");
        Thread.sleep(12000);
        System.out.println(rateLimiter.grantAccess("10.0.0.1"));
        System.out.println(rateLimiter.grantAccess("10.0.0.1"));
        System.out.println(rateLimiter.grantAccess("10.0.0.1"));
    }
}
