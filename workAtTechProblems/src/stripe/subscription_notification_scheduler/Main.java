package stripe.subscription_notification_scheduler;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        NotifcationManager manager = new NotifcationManager(
                List.of(new UserAccount("Alice", "Pro", 1, 30),
                        new UserAccount("Bob", "Basic", 10, 10)),
                Map.of("start", "Start", "-7", "7 Days left", "-1", "1 Day left", "end", "Expired")
        );
        manager.listNotifications();
    }
}
