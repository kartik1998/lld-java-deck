package stripe.store_closing_time_penalty;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class StoreManager {
    public int computePenalty(String logString, int closingTime) {
        String logs[] = logString.split(" ");
        int penalty = 0;
        for(int i = 0; i < closingTime && i < logs.length; i++) {
            if(logs[i].equals("N")) penalty++;
        }
        for(int i = closingTime; i < logs.length; i++) {
            if(logs[i].equals("Y")) penalty++;
        }
        return penalty;
    }

    public int findMinPenalty(String logString) {
        int n = logString.split(" ").length;
        int min = Integer.MAX_VALUE;
        for(int i = 0; i <= n; i++) {
            min = Math.min(computePenalty(logString, i), min);
        }
        return min;
    }

    public int findBestClosingTime(String logString) {
        int n = logString.split(" ").length;
        int min = Integer.MAX_VALUE;
        int res = 0;
        for(int i = 0; i <= n; i++) {
            int penalty = computePenalty(logString, i);
            if(min > penalty) {
                min = penalty;
                res = i;
            }
        }
        return res;
    }

    public List<Integer> getBestClosingTimes(String aggregateLog) {
        List<String> tokens = new ArrayList<>();
        boolean recordMode = false;
        String arr[] = aggregateLog.split(" ");
        String provisionalStr = "";
        for(String str : arr) {
            if(str.equals("BEGIN")) {
                recordMode = true;
                provisionalStr = "";
            } else if(str.equals("END")) {
                recordMode = false;
                if(!provisionalStr.isEmpty()) {
                    tokens.add(provisionalStr.trim());
                }
            } else {
                if(recordMode && (str.equals("Y") || str.equals("N"))) {
                    provisionalStr += " " + str;
                    provisionalStr = provisionalStr.trim();
                }
            }
        }
        List<Integer> retval = new ArrayList<>();
        for(String str : tokens) {
            System.out.println(String.format("%s, %s", str, findBestClosingTime(str)));
            retval.add(findBestClosingTime(str));
        }
        return retval;
    }
}
