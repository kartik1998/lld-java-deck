package citiesAndStates;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class Manager {
    private HashMap<String, List<String>> map;

    public Manager() {
        this.map = new HashMap<>();
    }

    public void addPlace(String place1, String place2) {
        if (!map.containsKey(place1)) map.put(place1, new ArrayList<>());
        map.get(place1).add(place2);
    }

    public boolean isChild(String place) {
        for (String key : map.keySet()) {
            List<String> list = map.get(key);
            for (String s : list) {
                if (s.equals(place)) return true;
            }
        }
        return false;
    }

    // n-ary tree level wise traversal type of problem
    public List<List<String>> getPlacesUnderAPlace(String place) {
        if (!map.containsKey(place)) return null;
        Queue<String> queue = new LinkedList<>();
        List<List<String>> res = new ArrayList<>();
        queue.add(place);
        while (!queue.isEmpty()) {
            List<String> nextElems = new ArrayList<>();
            List<String> temp = new ArrayList<>();
            while (!queue.isEmpty()) {
                String currentPlace = queue.remove();
                temp.add(currentPlace);
                if (!map.containsKey(currentPlace)) continue;
                for (String s : map.get(currentPlace)) nextElems.add(s);
            }
            res.add(new ArrayList<>(temp));
            for (String s : nextElems) queue.add(s);
        }
        return res;
    }
}

public class main {
    public static void main(String[] args) throws IOException {
        String filePath = "/Users/kartik/Desktop/lld-java-deck/src/citiesAndStates/data.csv";
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        String line = "";
        Manager manager = new Manager();
        while ((line = bufferedReader.readLine()) != null) {
            String[] input = line.split(",");
            if (!input[0].equals("place_1")) manager.addPlace(input[1], input[0]);
        }
        List<List<String>> res = manager.getPlacesUnderAPlace("India");
        System.out.println(res);
        System.out.println(manager.isChild("Bangalore"));
    }
}
