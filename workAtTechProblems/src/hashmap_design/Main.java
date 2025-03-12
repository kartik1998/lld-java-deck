package hashmap_design;

/**
 * Implementation -> https://www.youtube.com/watch?v=AsAymWn7D40
 * defaultSize -> 16 (Always as a power of 2 i.e. 2^n to ensure better collision management)
 * loadFactor -> 0.75 i.e. if the hashMap if 75% full, then it's maybe time to resize the hashMap
 * In Java 8 there is tree fy, which means that if the linked list size is greater than 8 then convert it into a balanced binary search tree
 * to make it fast
 */
public class Main {
    public static void main(String[] args) {
        IMap<String, Integer> map = new HashMapImplV2<String, Integer>();
        map.put("1", 2);
        map.put("3", 4);
        map.put("19", 4);
        map.put("20", 4);
        map.put("21", 4);
        System.out.println(map);
    }
}
