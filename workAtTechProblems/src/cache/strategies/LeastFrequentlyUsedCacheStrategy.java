package cache.strategies;

public class LeastFrequentlyUsedCacheStrategy extends CacheStrategy {
    public LeastFrequentlyUsedCacheStrategy(int capacity) {
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
