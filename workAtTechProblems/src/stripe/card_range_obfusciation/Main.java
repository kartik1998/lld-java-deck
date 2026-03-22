package stripe.card_range_obfusciation;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String BIN = "777777";
        List<String> intervals = Arrays.asList("1000000000,3999999999,VISA", "4000000000,5999999999,MASTERCARD");
        CardRangeManager manager = new CardRangeManager();
        manager.fillUpGaps(intervals, BIN);
    }
}