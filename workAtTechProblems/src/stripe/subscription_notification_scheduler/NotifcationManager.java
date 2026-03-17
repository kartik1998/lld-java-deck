package stripe.subscription_notification_scheduler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

class UserAccount {
    public String name;
    public String plan;
    public int accountDate;
    public int duration;

    public UserAccount(String name, String plan, int accountDate, int duration) {
        this.name = name;
        this.plan = plan;
        this.accountDate = accountDate;
        this.duration = duration;
    }

    public int lastDate() {
        return accountDate + duration - 1;
    }
}

class Event {
    public int time;
    public String message;

    public Event(int time, String message) {
        this.time = time;
        this.message = message;
    }

    @Override
    public String toString() {
        return String.format("%s %s", time, message);
    }
}

public class NotifcationManager {
    private final List<UserAccount> accountList;
    private final Map<String, String> schedule;

    public NotifcationManager(List<UserAccount> accountList, Map<String, String> schedule) {
        this.accountList = accountList;
        this.schedule = schedule;
    }

    public void listNotifications() {
        List<Event> list = new ArrayList<>();
        for (UserAccount account : accountList) {
            list.addAll(getEventList(account, schedule));
        }
        Collections.sort(list, (a, b) -> {
            return a.time - b.time;
        });
        for (Event event : list) {
            System.out.println(event);
        }
    }

    public List<Event> getEventList(UserAccount account, Map<String, String> schedule) {
        List<Event> list = new ArrayList<>();
        for (String key : schedule.keySet()) {
            if (key.equalsIgnoreCase("start")) {
                list.add(new Event(account.accountDate, String.format("[%s] Subscription for %s (%s)", schedule.get(key), account.name, account.plan)));
            } else if (key.equalsIgnoreCase("end")) {
                list.add(new Event(account.lastDate() + 1, String.format("[%s] Subscription for %s (%s)", schedule.get(key), account.name, account.plan)));
            } else {
                int dayDelta = Integer.parseInt(key);
                int day = dayDelta + account.lastDate();
                list.add(new Event(day, String.format("[%s] Subscription for %s (%s)", schedule.get(key), account.name, account.plan)));
            }
        }
        return list;
    }
}
