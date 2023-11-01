package cache.strategies;

public abstract class CacheStrategy {
    private int capacity;

    public CacheStrategy(int capacity) {
        this.capacity = capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public abstract void set(String key, Object value);

    public abstract Object get(String key);
}
