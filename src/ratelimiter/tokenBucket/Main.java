package ratelimiter.tokenBucket;

import java.util.HashMap;

class TokenBucket {
    private final long maxBucketSize;
    private final long refillRate;
    private double currentBucketSize;
    private long lastRefillTimestamp;

    public TokenBucket(long maxBucketSize, long refillRate) {
        this.maxBucketSize = maxBucketSize;
        this.refillRate = refillRate;
        this.currentBucketSize = maxBucketSize;
        this.lastRefillTimestamp = System.nanoTime();
    }

    public synchronized boolean grantAccess(int tokens) {
        refill();
        if (this.currentBucketSize > tokens) {
            this.currentBucketSize -= tokens;
            return true;
        }
        return false;
    }

    public void refill() {
        long now = System.nanoTime();
        double tokensToAdd = (now - lastRefillTimestamp) * (refillRate / 1e9);
        this.currentBucketSize = Math.min(currentBucketSize + tokensToAdd, maxBucketSize);
        this.lastRefillTimestamp = now;
    }
}

class TokenBucketRateLimiter {
    private HashMap<String, TokenBucket> ipWindowMap = new HashMap<>();
    private int refillRate = 4;
    private int maxBucketSize = 10;

    public synchronized boolean grantAccess(String ip) {
        boolean flag = false;
        if (!ipWindowMap.containsKey(ip)) {
            TokenBucket bucket = new TokenBucket(maxBucketSize, refillRate);
            ipWindowMap.put(ip, bucket);
            flag = bucket.grantAccess(1);
        } else {
            TokenBucket bucket = ipWindowMap.get(ip);
            flag = bucket.grantAccess(1);
        }
        return flag;
    }
}

public class Main {
    public static void main(String[] args) throws InterruptedException {
        TokenBucketRateLimiter rateLimiter = new TokenBucketRateLimiter();
        for (int i = 0; i < 15; i++) {
            System.out.println(rateLimiter.grantAccess("10.0.0.1"));
        }
        System.out.println("initiating thread sleep");
        Thread.sleep(1000);
        System.out.println(rateLimiter.grantAccess("10.0.0.1"));
        System.out.println(rateLimiter.grantAccess("10.0.0.1"));
        System.out.println(rateLimiter.grantAccess("10.0.0.1"));
    }
}
