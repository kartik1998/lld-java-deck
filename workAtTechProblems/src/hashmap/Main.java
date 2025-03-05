package hashmap;

public class Main {
    public static void main(String[] args) {
        IHashMap<String, Integer> map = new HashMapImpl<String, Integer>();
        map.put("1", 2);
        map.put("3", 4);
    }
}
