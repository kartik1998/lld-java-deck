package hashmap_design;

/**
 * Implementation -> https://www.youtube.com/watch?v=AsAymWn7D40
 * defaultSize -> 16 (Always as a power of 2 i.e. 2^n to ensure better collision management)
 * loadFactor -> 0.75 i.e. if the hashMap if 75% full, then it's maybe time to resize the hashMap
 * In Java 8 there is tree fy, which means that if the linked list size is greater than 8 then convert it into a balanced binary search tree
 * to make it fast
 */
public interface IMap<K, V> {
    public void put(K key, V val);
    public V get(K key);
    public void clear();
}
