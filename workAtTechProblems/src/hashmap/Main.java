package hashmap;

import java.util.LinkedList;

/**
 * @link: https://github.com/donnemartin/system-design-primer/blob/master/solutions/object_oriented_design/hash_table/hash_map.ipynb
 */
class HashMap {
    private LinkedList<Integer>[] map;
    private int range = 1000;

    public HashMap() {
        this.map = new LinkedList[range];
    }

    public HashMap(int range) {
        this.range = range;
        this.map = new LinkedList[range];
    }

    public void set(int key, int value) {
        int index = key % range;
        if (map[index] == null) {
            map[index] = new LinkedList<>();
            map[index].add(value);
        }
    }

    public LinkedList get(int key) {
        int index = key % range;
        return map[index];
    }
}

public class Main {
    public static void main(String[] args) {
        HashMap map = new HashMap();
        map.set(1, 2);
        map.set(2, 4);
        map.set(1533, 6);
        System.out.println(map.get(533));
    }
}
