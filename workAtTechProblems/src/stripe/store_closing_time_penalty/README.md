# Store Closing Time Penalty

## Overview

You're given a store's hourly log — a record of whether customers were present each hour (`Y`) or not (`N`). Your goal is to find the **optimal closing time** that minimizes a penalty score, then scale this up to handle multi-day aggregate log files.

---

## The Penalty Model

A **closing time** is an integer from `0` to `n` (where `n` is the number of hours logged):

- `0` → the store never opens
- `n` → the store stays open all day

Every closing time choice incurs a **penalty** based on two types of mistakes:

| Situation | Penalty |
|---|---|
| Store is **open**, but **no customers** that hour | +1 (wasted time) |
| Store is **closed**, but **customers were present** | +1 (lost opportunity) |

> **Key insight:** Hours *before* closing time are considered "open"; hours *from* closing time onward are considered "closed".

### Example

```
hour:         |  1  |  2  |  3  |  4  |
log:          |  Y  |  Y  |  N  |  Y  |
closing_time:  0   1    2    3    4
```

If `closing_time = 3`:
- Hours 1–2 are open → both have customers → **no penalty**
- Hour 3 is open → no customers → **+1 penalty**
- Hour 4 is closed → customers present → **+1 penalty**
- **Total penalty = 2**

---

## Part 1: `compute_penalty(log, closing_time)`

**Input:**
- `log` — a space-separated string of `'Y'` and `'N'` characters (e.g. `"Y Y N Y"`)
- `closing_time` — an integer between `0` and `len(log)` (inclusive)

**Output:** The integer penalty for that closing time.

**Things to think about:**
- How do you determine whether a given hour index falls in the "open" or "closed" window?
- What condition on each hour produces a penalty point?

---

## Part 2: `find_best_closing_time(log)`

**Input:**
- `log` — same format as Part 1

**Output:** The closing time (integer) that produces the **minimum penalty**.

- If **multiple closing times tie** for the minimum, return the **smallest** one.

**Things to think about:**
- The simplest approach: try every possible closing time and track the best.
- Can you do it in a single pass without recomputing from scratch each time? Think about how the penalty *changes* as you move the closing time by one hour.

---

## Part 3: `get_best_closing_times(aggregate_log)`

Employees sometimes dump multiple days' logs into a single file, mixed with garbage text and incomplete entries.

**Input:**
- `aggregate_log` — a raw string containing tokens spread across one or more lines

**Output:** A list of best closing times (one per valid log), in the order the logs appear.

### What Makes a Log Valid?

A valid log is a sequence of tokens matching this structure:

```
BEGIN  [zero or more Y/N tokens]  END
```

### Parsing Rules

1. **Delimiters:** `BEGIN` marks the start of a log; `END` marks the end.
2. **No nesting:** If a second `BEGIN` appears before an `END`, the first log is discarded (it's incomplete/garbage). The new `BEGIN` starts a fresh log attempt.
3. **Unfinished logs:** A `BEGIN` with no matching `END` is ignored entirely.
4. **Garbage tokens:** Any token that is not `BEGIN`, `END`, `Y`, or `N` should be ignored within a log (or treated as garbage outside one).
5. **Multi-line:** Tokens may be spread across lines; treat the whole file as one token stream.
6. **Multiple logs per line:** There can be more than one complete log on the same line.

### Example

```
garbage BEGIN Y N Y END junk BEGIN BEGIN Y END leftover
```

Valid logs extracted:
1. `Y N Y` → from the first `BEGIN ... END`
2. The second `BEGIN` is overwritten by the third `BEGIN`, so only `Y` (from the last `BEGIN Y END`) is valid.

**Things to think about:**
- A simple state machine works well here: are you currently "inside" a log or "outside"?
- What should happen to accumulated tokens when you hit a second `BEGIN` before an `END`?
- Tokenize first (split on whitespace), then process token by token.

---

## Summary of Functions

```python
def compute_penalty(log: str, closing_time: int) -> int:
    """Return the penalty for a given closing time."""

def find_best_closing_time(log: str) -> int:
    """Return the closing time with the minimum penalty (smallest if tied)."""

def get_best_closing_times(aggregate_log: str) -> list[int]:
    """Parse aggregate log, extract valid logs, return list of best closing times."""
```

---

## Tips

- Start with Part 1 and test it thoroughly before moving on.
- Part 2 can reuse Part 1 directly — optimize only if you want a challenge.
- For Part 3, sketch the state machine on paper before coding: *outside*, *inside*, and the transitions between them cover most of the edge cases.