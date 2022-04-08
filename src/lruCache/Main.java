package lruCache;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

/**
 * @link: https://github.com/donnemartin/system-design-primer/tree/master/solutions/object_oriented_design/lru_cache
 * ref: https://practice.geeksforgeeks.org/problems/lru-cache/1
 */
class LRUCache {
    private static int capacity = 3;
    private static Deque<Integer> q = new LinkedList<>();
    private static HashMap<Integer, Integer> map = new HashMap<>();

    public LRUCache(int capacity) {
        this.capacity = capacity;
    }

    public static void set(int key, int value) {
        if (!map.containsKey(key)) {
            q.addFirst(key);
            map.put(key, value);
            if (q.size() > capacity) {
                int k = q.removeLast();
                map.remove(k);
            }
        } else {
            update(key);
            map.put(key, value);
        }
    }

    public static int get(int key) {
        if (!map.containsKey(key)) return -1;
        update(key);
        return map.get(key);
    }

    private static void update(int key) {
        Stack<Integer> stack = new Stack<>();
        while (q.peekLast() != key) stack.push(q.removeLast());
        q.removeLast();
        while (!stack.isEmpty()) q.addLast(stack.pop());
        q.addFirst(key);
    }
}

public class Main {
    public static void main(String[] args) {
        LRUCache.set(1, 2);
        LRUCache.set(2, 10);
        System.out.println(LRUCache.get(1));
    }
}
