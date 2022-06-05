package cache;

import cache.strategies.LeastFrequentlyUsedCacheStrategy;

/**
 * @link https://leetcode.com/discuss/interview-question/1575869/gojek-senior-sde-lld-awaiting
 * Q. Cache in which you can change eviction strategies
 */

public class Main {
    public static void main(String[] args) {
        Cache cache = new Cache(10);
        cache.setCacheStrategy(LeastFrequentlyUsedCacheStrategy.class);
        System.out.println(cache);
    }
}
