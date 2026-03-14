package stripe.atlas_name_check;

import java.util.HashMap;
import java.util.Map;

public class StripeManager {
    private Map<String, Integer> map = new HashMap<>();
    private StripeNormalizer normalizer = new StripeNormalizer();

    public boolean registerAttempt(int accountId, String name) {
        String key = normalizer.normalize(name);
        if(key == null) {
            System.out.println(accountId + " | " + "Name Not Available");
            return false;
        }
        if(!map.containsKey(key)) {
            map.put(key, accountId);
            System.out.println(accountId + " | " + "Name Available");
            return true;
        } else {
            System.out.println(accountId + " | " + "Name Not Available");
            return false;
        }
    }

    public boolean reclaim(int accountId, String name) {
        String key = normalizer.normalize(name);
        if(!map.containsKey(key)) {
            // you can go ahead and take
            System.out.println(String.format("RECLAIM SUCCESS %s, %s, No map entry present", accountId, name));
            return true;
        } else {
            if(map.get(key) == accountId) {
                System.out.println(String.format("RECLAIM SUCCESS %s, %s", accountId, name));
                map.remove(key);
                return true;
            } else {
                System.out.println(String.format("RECLAIM FAILURE %s, %s, no access", accountId, name));
                return false;
            }
        }
    }
}
