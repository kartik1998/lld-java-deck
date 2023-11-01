package cache.strategies;

public class LeastRecentlyUsedCacheStrategy extends CacheStrategy {
    public LeastRecentlyUsedCacheStrategy(int capacity) {
        super(capacity);
    }

    @Override
    public void set(String key, Object value) {

    }

    @Override
    public Object get(String key) {
        return null;
    }
}
