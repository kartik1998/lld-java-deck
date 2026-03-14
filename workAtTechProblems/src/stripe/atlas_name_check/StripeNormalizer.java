package stripe.atlas_name_check;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class StripeNormalizer {
    private Set<String> suffixesToRemove = new HashSet<>(Arrays.asList("inc.", "corp.", "llc", "l.l.c.", "llc."));
    private Set<String> prefixesToRemove = new HashSet<>(Arrays.asList("the", "an", "a"));

    private String collapse(String str) {
        String arr[] = str.trim().split(" ");
        String retval = "";
        for (String s : arr) {
            if (s.isEmpty() || s.equals(" ")) continue;
            retval += s + " ";
        }
        return retval.trim();
    }

    private String trimUtil(String str) {
        String arr[] = str.split(" ");
        if (arr.length == 0) return str;
        if (suffixesToRemove.contains(arr[arr.length - 1])) {
            arr[arr.length - 1] = "";
        }

        if (prefixesToRemove.contains(arr[0])) {
            arr[0] = "";
        }

        String res = "";
        for (int i = 0; i < arr.length; i++) {
            String s = arr[i];
            if(i > 0 && s.equals("and")) {
                continue;
            }
            if (!s.isEmpty() && !s.equals(" ")) {
                res += s;
            }
        }
        return res.trim();
    }

    public String normalize(String input) {
        input = input.toLowerCase();
        input = input.replaceAll("&", " ");
        input = input.replaceAll(",", " ");
        input = collapse(input);
        input = trimUtil(input);
        if(input.isEmpty()) {
            // this is unavailable
            return null;
        }
        return new String(input);
    }
}
