package keyValueStore;
/**
 * @link: https://workat.tech/machine-coding/practice/design-key-value-store-6gz6cq124k65
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class Pair {
    String key, val;

    public Pair(String key, String val) {
        this.key = key;
        this.val = val;
    }

    @Override
    public String toString() {
        return key + ": " + val;
    }
}

class InMemoryKeyValueStore {
    private HashMap<String, List<Pair>> keyMap; // <key, [Pair(attributeKey, attributeValue)]>
    private HashMap<String, List<Pair>> attributeMap; // <attributeKey, [Pair(key, attributeVal)]>

    public InMemoryKeyValueStore() {
        this.keyMap = new HashMap<>();
        this.attributeMap = new HashMap<>();
    }

    public List<Pair> get(String key) {
        return keyMap.getOrDefault(key, null);
    }

    public void put(String key, String... args) {
        if (args.length % 2 != 0) throw new Error("args length should be even");
        List<Pair> list = new ArrayList<>();
        for (int i = 1; i < args.length; i += 2) {
            list.add(new Pair(args[i - 1], args[i]));
        }
        this.delete(key);
        keyMap.put(key, list);
        for (int i = 1; i < args.length; i += 2) {
            String attributeKey = args[i - 1];
            String attributeVal = args[i];
            if (!attributeMap.containsKey(attributeKey)) {
                List<Pair> temp = new ArrayList<>();
                temp.add(new Pair(key, attributeVal));
                attributeMap.put(attributeKey, temp);
            } else {
                List<Pair> temp = attributeMap.get(attributeKey);
                temp.add(new Pair(key, attributeVal));
                attributeMap.put(attributeKey, temp);
            }
        }
    }

    public void delete(String key) {
        if (!keyMap.containsKey(key)) return;
        List<Pair> attributes = keyMap.get(key);
        for (Pair attributePair : attributes) {
            if (!attributeMap.containsKey(attributePair.key)) continue;
            List<Pair> temp = new ArrayList<>();
            for (Pair p : attributeMap.get(attributePair.key)) {
                if (!(p.key).equals(key)) temp.add(p);
            }
            if (temp.size() == 0) attributeMap.remove(attributePair.key);
            else attributeMap.put(attributePair.key, temp);
        }
        keyMap.remove(key);
    }

    public List<String> search(String attributeKey, String attributeVal) {
        List<String> res = new ArrayList<>();
        List<Pair> attributeMapList = attributeMap.get(attributeKey);
        for (Pair p : attributeMapList) {
            if (p.val.equals(attributeVal)) res.add(p.key);
        }
        return res;
    }

    public List<String> keys() {
        List<String> res = new ArrayList<>();
        for (String s : keyMap.keySet()) res.add(s);
        return res;
    }
}

public class Main {
    public static void main(String[] args) {
        InMemoryKeyValueStore store = new InMemoryKeyValueStore();
        store.put("sde_ninjas", "title", "SDE-ninjas", "price", "300", "enrolled", "true", "estimated_time", "3");
        store.put("sde_bootcamp", "title", "SDE-Bootcamp", "price", "30000.00", "enrolled", "false", "estimated_time", "30");
        System.out.println(store.get("sde_bootcamp"));
        System.out.println(store.keys());
        store.put("sde_kickstart", "title", "SDE-Kickstart", "price", "4000", "enrolled", "true", "estimated_time", "8");
        System.out.println(store.get("sde_kickstart"));
        System.out.println(store.keys());
        store.put("sde_kickstart", "title", "SDE-Kickstart", "price", "4000.00", "enrolled", "true", "estimated_time", "8");
        System.out.println(store.get("sde_kickstart"));
        System.out.println(store.keys());
//        store.delete("sde_bootcamp");
        System.out.println(store.get("sde_bootcamp"));
        System.out.println(store.search("price", "30000.00"));
        System.out.println(store.search("enrolled", "true"));
    }
}
