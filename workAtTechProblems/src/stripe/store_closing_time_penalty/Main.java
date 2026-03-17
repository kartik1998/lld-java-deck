package stripe.store_closing_time_penalty;

public class Main {
    public static void main(String[] args) {
        StoreManager manager = new StoreManager();
//        System.out.println(manager.findMinPenalty("Y Y N Y"));
        manager.getBestClosingTimes("garbage BEGIN Y N Y END junk BEGIN BEGIN Y END leftover");
    }
}
