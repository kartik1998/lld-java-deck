package hashmap_design;

import java.util.Arrays;

/**
 * Implementation -> https://www.youtube.com/watch?v=AsAymWn7D40
 * defaultSize -> 16 (Always as a power of 2 i.e. 2^n to ensure better collision management)
 * loadFactor -> 0.75 i.e. if the hashMap if 75% full, then it's maybe time to resize the hashMap
 * In Java 8 there is tree fy, which means that if the linked list size is greater than 8 then convert it into a balanced binary search tree
 * to make it fast
 */

/**
 * Implementation -> https://www.youtube.com/watch?v=AsAymWn7D40
 * This is my own simple implementation of the HashMap
 * loadFactor
 */
public class HashMapImplV2<K, V> implements IMap<K, V> {
    private static int MAX_HASHTABLE_SIZE = (int) Math.pow(2, 30);
    private static int DEFAULT_HASHTABLE_SIZE = (int) Math.pow(2, 4); // 16 is generally used as default hashtable size

    private Node[] hashTable;

    public HashMapImplV2() {
        this.hashTable = new Node[DEFAULT_HASHTABLE_SIZE];
    }

    /**
     * Ideally in this case you should calculate closest Math.pow(2, n) greater than equal to capacity
     *
     * @param capacity
     */
    public HashMapImplV2(int capacity) {
        this.hashTable = new Node[capacity];
    }

    private class Node<K, V> {
        K key;
        V value;
        Node next;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "key=" + key +
                    ", value=" + value +
                    ", next=" + next +
                    '}';
        }
    }

    @Override
    public void put(K key, V val) {
        int hashCode = key.hashCode() % hashTable.length;
        Node curr = hashTable[hashCode];
        if (curr == null) {
            hashTable[hashCode] = new Node<K, V>(key, val);
        } else {
            while (true) {
                if (curr.key.equals(key)) {
                    curr.value = val;
                    break;
                }

                if (curr.next == null) {
                    curr.next = new Node<K, V>(key, val);
                    break;
                }
                curr = curr.next;
            }
        }
    }

    @Override
    public V get(K key) {
        int hashCode = key.hashCode() % hashTable.length;
        Node curr = hashTable[hashCode];
        while (curr != null) {
            if (curr.key.equals(key)) {
                return (V) curr.value;
            }
            curr = curr.next;
        }
        return null;
    }

    @Override
    public void clear() {
        this.hashTable = new Node[hashTable.length];
    }

    @Override
    public String toString() {
        return "HashMapImplV2{" +
                "hashTable=" + Arrays.toString(hashTable) +
                '}';
    }
}
