package hashmap;

public interface IMap<K, V> {
    public void put(K key, V val);
    public V get(K key);
    public void clear();
}
