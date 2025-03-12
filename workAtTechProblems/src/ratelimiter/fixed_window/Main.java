package ratelimiter.fixed_window;

interface IRateLimiter {
    boolean grantAccess();
}

class FixedWindowRateLimiter implements IRateLimiter {
    private final Long windowInMs;
    private final Integer requestCap;

    private Long rateLimiterStartedAt = null;
    private Long windowId = null;
    private Integer requestsServedInWindow = 0;
    private Integer requestsUnServedInWindow = 0;

    FixedWindowRateLimiter(Long windowInMs, Integer requestCap) {
        this.windowInMs = windowInMs;
        this.requestCap = requestCap;
    }

    @Override
    public synchronized boolean grantAccess() {
        Long currentTime = System.currentTimeMillis();
        rateLimiterStartedAt = (rateLimiterStartedAt == null) ? currentTime : rateLimiterStartedAt;
        windowId = (windowId == null) ? 0 : windowId;

        // check if the windowId has changed
        Long newWindowId = getWindowId(currentTime);
        if (newWindowId != windowId) {
            this.windowId = newWindowId;
            this.requestsServedInWindow = 0;
            this.requestsUnServedInWindow = 0;
        }

        if (requestsServedInWindow < requestCap) {
            requestsServedInWindow++;
            System.out.println(String.format("GrantAccess success - windowId=%s, requestsServedInWindow=%s, requestCap=%s", windowId, requestsServedInWindow, requestCap));
            return true;
        }

        requestsUnServedInWindow++;
        System.out.println(String.format("GrantAccess failure - windowId=%s, requestsUnServedInWindow=%s, requestCap=%s", windowId, requestsUnServedInWindow, requestCap));
        return false;
    }

    private Long getWindowId(Long currentTime) {
        Double difference = (double) (currentTime - rateLimiterStartedAt);
        return (long) Math.ceil(difference / windowInMs);
    }
}

public class Main {

    public static void main(String[] args) throws InterruptedException {
        // 1 second window
        IRateLimiter rateLimiter = new FixedWindowRateLimiter(1000L, 3);
        rateLimiter.grantAccess();
        rateLimiter.grantAccess();
        rateLimiter.grantAccess();
        rateLimiter.grantAccess();
        rateLimiter.grantAccess();
        rateLimiter.grantAccess();
        rateLimiter.grantAccess();
        Thread.sleep(1000);
        rateLimiter.grantAccess();
        rateLimiter.grantAccess();
        rateLimiter.grantAccess();
        rateLimiter.grantAccess();

    }
}
