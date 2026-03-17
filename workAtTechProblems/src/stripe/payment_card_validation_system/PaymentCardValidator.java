package stripe.payment_card_validation_system;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PaymentCardValidator {
    private int lulnDouble(int num) {
        int x = num * 2;
        return x > 9 ? x - 9 : x;
    }

    public Map<String, Integer> fetchRedactedCards(String str) {
        if(str.length() != 15 && str.length() != 16) {
            return Map.of("UNKNOWN_NETWORK", 1);
        }
        Set<String> cardNums = new HashSet<>();
        populateAllCardNums(str, "", cardNums);
        Map<String, Integer> map = new HashMap<>();
        for(String num : cardNums) {
            String card = validateAndFetch(num);
            if(card.equals("INVALID_CHECKSUM") || card.equals("UNKNOWN_NETWORK")) continue;
            map.put(card, map.getOrDefault(card, 0) + 1);
        }
        return map;
    }

    public void populateAllCardNums(String input, String output, Set<String> res) {
        if(input.isEmpty()) {
            res.add(output);
            return;
        }
        if(input.charAt(0) == '*') {
            for(int i = 0; i <= 9; i++) {
                populateAllCardNums(input.substring(1), output + String.valueOf(i), res);
            }
        } else {
            populateAllCardNums(input.substring(1),  output + input.charAt(0), res);
        }
    }

    public String validateAndFetch(String num) {
        if(num.length() != 15 && num.length() != 16) {
            return "UNKNOWN_NETWORK";
        }

        String lulnStr = "" + num.charAt(num.length() - 1);
        int sum = num.charAt(num.length() - 1) - '0';
        int count = 1;
        for (int i = num.length() - 2; i >= 0; i--) {
            count++;
            int lulnNum = num.charAt(i) - '0';
            if (count % 2 == 0) {
                lulnNum = lulnDouble(lulnNum);
            }
            lulnStr = String.valueOf(lulnNum) + lulnStr;
            sum += lulnNum;
        }

//        System.out.println(String.format("lulnStr=%s", lulnStr));
        if (sum % 10 == 0) {
            int val = Integer.parseInt(num.substring(0, 2));
            if (num.charAt(0) == '4' && num.length() == 16) {
                return "VISA";
            } else if (val >= 51 && val <= 55 && num.length() == 16) {
                return "MASTERCARD";
            } else if ((val == 34 || val == 37) && num.length() == 15) {
                return "AMEX";
            } else {
                return "UNKNOWN_NETWORK";
            }
        }
        return "INVALID_CHECKSUM";
    }
}
