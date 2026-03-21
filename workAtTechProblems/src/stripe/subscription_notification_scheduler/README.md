# Problem 5: Subscription Notification Scheduler

## Overview

Implement a notification scheduler for subscription plans. Users subscribe to a plan with a start date and duration; you must schedule and print email notifications on the correct days based on configurable rules.

---

## Part 1: Basic Email Scheduling

### Inputs

**`send_schedule`** — Maps relative day offsets to email message types:

| Key | Meaning |
|-----|---------|
| `"start"` | Send on the subscription start day |
| `-15`, `-7`, etc. | Send N days *before* the subscription end date |
| `"end"` | Send on the subscription end date |

**`user_accounts`** — List of user records:

| Field | Type | Description |
|-------|------|-------------|
| `name` | string | User's name |
| `plan` | string | Subscription plan name |
| `account_date` | int | Day number the subscription started |
| `duration` | int | Subscription length in days |

### Output

Print one line per email event, sorted in **ascending order of day**:

```
<day>: [<Email Type>] Subscription for <name> (<plan>)
```

### Example

Given `send_schedule = { "start": "Welcome", -7: "Expiring Soon", "end": "Expired" }` and a user `Alice` on plan `Pro` starting day `1` with duration `30`:

```
1: [Welcome] Subscription for Alice (Pro)
24: [Expiring Soon] Subscription for Alice (Pro)
31: [Expired] Subscription for Alice (Pro)
```

> End date = `account_date + duration` = day 31. The `-7` offset fires on day `31 - 7 = 24`.

---

## Part 2: Plan Changes

Extend Part 1 to handle mid-subscription plan changes. When a user changes their plan:

1. Print a `[Changed]` event on the change date.
2. Cancel any future notifications scheduled under the old plan.
3. Recalculate the remaining duration: `remaining = original_end_date - change_date`.
4. Reschedule all future notifications (negative-offset and `"end"`) based on the new end date: `new_end_date = change_date + remaining`.

> The `"start"` email is never re-sent on a plan change.

### Additional Input

**`changes`** — List of plan-change events:

| Field | Type | Description |
|-------|------|-------------|
| `name` | string | User whose plan is changing |
| `new_plan` | string | Name of the new plan |
| `change_date` | int | Day number the change takes effect |

### Output

Same format as Part 1, with `[Changed]` events interleaved in chronological order:

```
<day>: [Changed] Subscription for <name> (<new_plan>)
```

### Example

Alice on `Pro` (day 1, duration 30) changes to `Enterprise` on day 20:

```
1: [Welcome] Subscription for Alice (Pro)
20: [Changed] Subscription for Alice (Enterprise)
24: [Expiring Soon] Subscription for Alice (Enterprise)   ← new_end = 20 + 11 = 31, so 31 - 7 = 24
31: [Expired] Subscription for Alice (Enterprise)
```

---

## Part 3: Renewals (Bonus)

Extend Part 2 so that the `changes` list may also contain **renewal events**. A renewal extends the subscription duration without changing the plan.

When a renewal is processed:

1. Print a `[Renewed]` event on the renewal date.
2. Cancel any future notifications scheduled under the current end date.
3. Compute the new end date: `new_end_date = current_end_date + extension`.
4. Reschedule all future notifications based on the new end date.

### Renewal Event Fields

| Field | Type | Description |
|-------|------|-------------|
| `name` | string | User whose subscription is being renewed |
| `extension` | int | Number of days added to the current end date |
| `change_date` | int | Day number the renewal takes effect |

> A `changes` entry is a **plan change** if it has `new_plan`, and a **renewal** if it has `extension`.

### Output

Same format, with `[Renewed]` events interleaved chronologically:

```
<day>: [Renewed] Subscription for <name> (<plan>)
```

### Example

Alice on `Enterprise` (end date day 31) renews with `+20` days on day 25:

```
...
25: [Renewed] Subscription for Alice (Enterprise)   ← new_end = 31 + 20 = 51
44: [Expiring Soon] Subscription for Alice (Enterprise)  ← 51 - 7 = 44
51: [Expired] Subscription for Alice (Enterprise)
```

---

## Constraints & Clarifications

- All day values are positive integers; day `1` is the earliest possible day.
- Multiple users may have events on the same day — print them in the order they appear in `user_accounts`.
- A user may have multiple plan changes or renewals; each is applied in ascending `change_date` order.
- A change or renewal that arrives after the subscription has already ended has no effect.
- Negative-offset notifications that would fall on or before the start date (or current date for re-schedules) are silently skipped.
- The `send_schedule` is global and applies uniformly to all users and plans.