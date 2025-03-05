package hashmap;

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
