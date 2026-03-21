package stripe.subscription_notification_scheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        NotificationHandler handler = new NotificationHandler();

        // Part 3: Renewal example
        // Alice on Pro (day 1, duration 30) → changes to Enterprise on day 20
        // → renews with +20 days on day 25
        // Expected:
        //   1: [Welcome] Subscription for Alice (Pro)
        //  20: [Changed] Subscription for Alice (Enterprise)
        //  25: [Renewed] Subscription for Alice (Enterprise)  ← new_end = 31 + 20 = 51
        //  44: [Expiring Soon] Subscription for Alice (Enterprise)  ← 51 - 7 = 44
        //  51: [Expired] Subscription for Alice (Enterprise)
        UserAccount alice = new UserAccount("Alice", "Pro", 1, 30);
        PlanChange changeToPro = new PlanChange("Alice", "Enterprise", 20);
        PlanChange renewal = new PlanChange("Alice", 20, 25);  // extend by 20 days on day 25

        handler.printEvents(
                List.of(alice),
                Map.of("start", "Welcome", "-7", "Expiring Soon", "end", "Expired"),
                List.of(changeToPro, renewal)
        );
    }
}
