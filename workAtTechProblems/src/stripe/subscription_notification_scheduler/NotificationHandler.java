package stripe.subscription_notification_scheduler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

class UserAccount {
    String name;
    String plan;
    int accountDate;
    int duration;
    private int endDate;
    List<PlanChange> planChanges = new ArrayList<>();

    public UserAccount(String name, String plan, int accountDate, int duration) {
        this.name = name;
        this.plan = plan;
        this.accountDate = accountDate;
        this.duration = duration;
        this.endDate = duration + accountDate;
    }

    public int getEndDate() {
        return endDate;
    }

    public void addPlanChangeEvent(PlanChange planChange) {
        planChanges.add(planChange);
        Collections.sort(planChanges, (a, b) -> {
            return Integer.compare(a.changeDate, b.changeDate);
        });
    }

    public Collection<? extends Event> getEventList(Map<String, String> schedule) {
        List<Event> eventList = new ArrayList<>();
        eventList.add(createEvent(accountDate, schedule.get("start"), plan));
        for (PlanChange change : planChanges) {
            String eventPlan = change.newPlan != null ? change.newPlan : this.plan;
            eventList.add(createEvent(change.changeDate, change.type, eventPlan));
            if(change.type.equalsIgnoreCase("Changed")) {
                this.duration = getEndDate() - change.changeDate;
                this.endDate = change.changeDate + duration;
                this.plan = change.newPlan;
            } else {
                this.endDate = this.endDate + change.extension;
                this.duration = this.endDate - accountDate;
            }
        }

        for (String key : schedule.keySet()) {
            if (key.equalsIgnoreCase("start") || key.equalsIgnoreCase("end")) continue;
            int date = getEndDate() + Integer.parseInt(key);
            if (date > accountDate) {
                eventList.add(createEvent(date, schedule.get(key), plan));
            }
        }
        eventList.add(createEvent(getEndDate(), schedule.get("end"), plan));
        return eventList;
    }

    private Event createEvent(int time, String prefix, String plan) {
        return new Event(time,
                String.format("[%s] Subscription for %s (%s)", prefix, name, plan)
        );
    }
}

class PlanChange {
    String name;
    String newPlan = null;
    Integer extension = null;
    int changeDate;
    public String type = null;

    public PlanChange(String name, String newPlan, int changeDate) {
        this.name = name;
        this.newPlan = newPlan;
        this.changeDate = changeDate;
        this.type = "Changed";
    }

    // renewal case
    public PlanChange(String name, Integer extension, int changeDate) {
        this.name = name;
        this.extension = extension;
        this.changeDate = changeDate;
        this.type = "Renewed";
    }
}

class Event {
    int time;
    String message;

    public Event(int time, String message) {
        this.time = time;
        this.message = message;
    }

    @Override
    public String toString() {
        return String.format("%s: %s", time, message);
    }
}

public class NotificationHandler {
    public void printEvents(List<UserAccount> userAccounts, Map<String, String> schedule, List<PlanChange> planChanges) {
        List<Event> eventList = new ArrayList<>();
        for (PlanChange change : planChanges) {
            UserAccount userAccount = findUserAccountByName(change.name, userAccounts);
            if (userAccount != null) {
                userAccount.addPlanChangeEvent(change);
            }
        }

        for (UserAccount userAccount : userAccounts) {
            eventList.addAll(userAccount.getEventList(schedule));
        }
        Collections.sort(eventList, (a, b) -> {
            return Integer.compare(a.time, b.time);
        });
        for (Event event : eventList) {
            System.out.println(event);
        }
    }

    private UserAccount findUserAccountByName(String name, List<UserAccount> userAccounts) {
        for (UserAccount userAccount : userAccounts) {
            if (userAccount.name.equalsIgnoreCase(name)) {
                return userAccount;
            }
        }
        return null;
    }
}
