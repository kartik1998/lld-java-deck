package cache;

import cache.strategies.CacheStrategy;
import cache.strategies.LeastFrequentlyUsedCacheStrategy;
import cache.strategies.LeastRecentlyUsedCacheStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * The pro of this solution is that it performs gets and sets for ALL of the implemented strategies
 * The con of this solution is that each strategy is taking up space, while only one is being actually used
 */
public class Cache {
    private List<CacheStrategy> strategiesList = new ArrayList<>();
    private CacheStrategy cacheStrategy;
    private int capacity;

    public Cache(int capacity) {
        this.capacity = capacity;
        strategiesList.add(new LeastRecentlyUsedCacheStrategy(capacity));
        strategiesList.add(new LeastFrequentlyUsedCacheStrategy(capacity));
        this.setCacheStrategy(LeastRecentlyUsedCacheStrategy.class); // LRU by default
    }

    public boolean setCacheStrategy(Class strategyClass) {
        for (CacheStrategy strategy : strategiesList) {
            if (strategy.getClass().equals(strategyClass)) {
                this.cacheStrategy = strategy;
                return true;
            }
        }
        return false;
    }

    public CacheStrategy getCacheStrategy() {
        return cacheStrategy;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
        for (CacheStrategy strategy : strategiesList) {
            strategy.setCapacity(capacity);
        }
    }

    public void set(String key, Object value) {
        for (CacheStrategy strategy : strategiesList) {
            strategy.set(key, value);
        }
    }

    public Object get(String key) {
        for (CacheStrategy strategy : strategiesList) {
            if (!strategy.equals(cacheStrategy)) strategy.get(key);
        }
        return cacheStrategy.get(key);
    }
}
