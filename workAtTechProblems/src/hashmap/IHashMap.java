package hashmap;

public interface IHashMap<K, V> {
    public void put(K key, V val);
    public V get(K key);
    public void clear();
}
