package stripe.payment_card_validation_system;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PaymentCardValidator {
    private int lulnDouble(int num) {
        int x = num * 2;
        return x > 9 ? x - 9 : x;
    }

    public void handleCorruptedCard(String input) {
        if(input.charAt(input.length() - 1) != '?') {
            System.out.println("Not a corrupted card");
            return;
        }
        Set<String> cards = new HashSet<>();
        cards.addAll(getSwaps(input.substring(0, input.length() - 1)));
        cards.addAll(getPossibleOutComes(input.substring(0, input.length() - 1)));
        List<String> list = new ArrayList<>(cards);
        Collections.sort(list);
        for(String str : list) {
           String card = validateAndFetch(str);
            if(card.equals("INVALID_CHECKSUM") || card.equals("UNKNOWN_NETWORK")) continue;
            System.out.println(String.format("%s,%s", str, card));
        }
    }

    public List<String> getSwaps(String input) {
        List<String> retval = new ArrayList<>();
        for(int i = 1; i < input.length(); i++) {
            if(i == 1 && input.charAt(i) == '0') {
                continue;
            }
            String str = input.substring(0, i - 1) + input.charAt(i) + input.charAt(i - 1) + input.substring(i + 1);
            retval.add(str);
        }
        return retval;
    }
    public List<String> getPossibleOutComes(String input) {
        List<String> retval = new ArrayList<>();
        for(int i = 0; i < input.length(); i++) {
            for(int j = 0; j <= 9; j++) {
                if(i == 0 && j == 0 || j == input.charAt(i) - '0') continue;
                String str = input.substring(0, i) + String.valueOf(j) + input.substring(i + 1);
                retval.add(str);
            }
        }
        return retval;
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
