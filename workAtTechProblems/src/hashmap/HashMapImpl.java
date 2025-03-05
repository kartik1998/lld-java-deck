package hashmap;

import java.util.Arrays;

/**
 * Implementation -> https://www.youtube.com/watch?v=AsAymWn7D40
 * defaultSize -> 16 (Always as a power of 2 i.e. 2^n to ensure better collision management)
 * loadFactor
 */
public class HashMapImpl<K, V> implements IMap<K, V> {
    /**
     * set initial size as 16
     */
    private static final int INITIAL_SIZE = 1 << 4;

    /**
     * Maximum capacity is basically Integer.MAX_VALUE which is 2^31 - 1.
     * One Integer has 4 bytes i.e. 32 bits.
     * To account for the sign bit the range is -2^31 to 2^31 - 1
     * <p>
     * So we can use Integer.MAX_VALUE but chose to use 2^30.
     */
    private static final int MAXIMUM_CAPACITY = 1 << 30;

    private Entry[] hashTable;
    private int tableLength;

    /**
     * Entry class for Key val pairs (like a Linked List Node)
     */
    private class Entry<K, V> {
        public K key;
        public V value;
        public Entry next;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }

        @Override
        public String toString() {
            return "Entry{" +
                    "key=" + key +
                    ", value=" + value +
                    ", next=" + next +
                    '}';
        }
    }


    public HashMapImpl() {
        this.hashTable = new Entry[INITIAL_SIZE];
        this.tableLength = INITIAL_SIZE;
    }

    public HashMapImpl(int capacity) {
        int tableLength = tableSizeFor(capacity);
        this.hashTable = new Entry[tableLength];
        this.tableLength = tableLength;
    }

    @Override
    public void put(K key, V val) {
        int hashCode = key.hashCode() % hashTable.length;
        Entry node = hashTable[hashCode];
        if (node == null) {
            hashTable[hashCode] = new Entry<K, V>(key, val);
        } else {
            Entry temp = node;
            while (temp != null) {
                if (temp.key.equals(key)) {
                    temp.value = val;
                    break;
                }

                if (temp.next == null) {
                    temp.next = new Entry<K, V>(key, val);
                    break;
                }
                temp = temp.next;
            }
        }
    }

    @Override
    public V get(K key) {
        int hashCode = key.hashCode() % hashTable.length;
        Entry node = hashTable[hashCode];
        Entry temp = node;
        while (temp != null) {
            if (temp.key.equals(key)) {
                return (V) temp.value;
            }
            temp = temp.next;
        }
        return null;
    }

    @Override
    public void clear() {
        this.hashTable = new Entry[tableLength];
    }

    /**
     * Returns a power of two size for the given target capacity.
     */
    private static int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

    @Override
    public String toString() {
        return "HashMapImpl{" +
                "hashTable=" + Arrays.toString(hashTable) +
                ", tableLength=" + tableLength +
                '}';
    }
}
