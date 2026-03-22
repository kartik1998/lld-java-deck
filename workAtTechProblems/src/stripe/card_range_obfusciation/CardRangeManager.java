package stripe.card_range_obfusciation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Interval {
    long start, end;
    String type;

    public Interval(long start, long end, String type) {
        this.start = start;
        this.end = end;
        this.type = type;
    }

    public static Interval from(String str) {
        String arr[] = str.split(",");
        return new Interval(Long.parseLong(arr[0]), Long.parseLong(arr[1]), arr[2]);
    }

    public String construct(String bin) {
        String startSuffix = String.valueOf(start);
        String startStr = "";
        if (start == (long) 1e9) {
            startStr = String.valueOf(Long.parseLong(bin) * start) + "0";
        } else {
            startStr = bin + startSuffix;
        }
        String endStr = bin + String.valueOf(end);
        return String.format("%s,%s,%s", startStr, endStr, type);
    }
}

public class CardRangeManager {

    public void fillUpGaps(List<String> inputIntervals, String BIN) {
        if (inputIntervals.size() == 0) return;

        List<Interval> list = new ArrayList<>();
        for (String inputInterval : inputIntervals) {
            list.add(Interval.from(inputInterval));
        }

        Collections.sort(list, (a, b) -> {
            return Long.compare(a.start, b.start);
        });

        list.get(0).start = (long) 1e9;
        for (int i = 1; i < list.size(); i++) {
            Interval prev = list.get(i - 1);
            Interval curr = list.get(i);
            // edge case prev.end >= curr.start -- not handling as of now\
            // because that means that input was incorrect
            if (curr.start - 1 != prev.end) {
                prev.end = curr.start - 1;
            }
        }
        list.get(list.size() - 1).end = ((long) 1e10 - 1);
        for (Interval interval : list) {
            System.out.println(interval.construct(BIN));
        }
    }
}
