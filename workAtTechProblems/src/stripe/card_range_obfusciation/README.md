# Problem 2: Card Range Obfuscation

Payment card numbers consist of 8–19 digits. The first 6 digits are the **Bank Identification Number (BIN)**. For a given BIN, all 16-digit card numbers starting with that BIN form its **BIN range** — from `BIN0000000000` to `BIN9999999999` (inclusive).

Stripe's card metadata API returns a list of intervals within this BIN range, each mapped to a card brand (e.g., VISA, MASTERCARD). These intervals may have **gaps** at the start, middle, or end of the BIN range, which fraudsters can exploit to probe for valid cards.

Your task: **fill all gaps** so the output fully covers the entire BIN range with no uncovered offsets, then return all intervals in sorted order.

**Gap-filling rule:**
- A **leading gap** (before the first interval) is filled by extending the first interval's brand backward to offset `0000000000`.
- A **trailing gap** (after the last interval) is filled by extending the last interval's brand forward to offset `9999999999`.
- A **middle gap** (between two intervals) is filled by extending the left interval's brand forward to meet the right interval's start.

---

## Input Format

```
Line 1: A 6-digit BIN
Line 2: A positive integer n (number of intervals)
Next n lines: start,end,brand
```

Where `start` and `end` are **10-digit offsets** within the BIN range (inclusive), and `brand` is an alphanumeric string.

---

## Output Format

A sorted list of gap-free intervals using **full 16-digit card numbers**:

```
start,end,brand
```

---

## Example 1 — Gaps at start and end

**Input:**
```
777777
2
1000000000,3999999999,VISA
4000000000,5999999999,MASTERCARD
```

**Gaps identified:**
- Leading gap: `0000000000` – `0999999999` (before VISA) → extend VISA backward
- Trailing gap: `6000000000` – `9999999999` (after MASTERCARD) → extend MASTERCARD forward

**Output:**
```
7777770000000000,7777773999999999,VISA
7777774000000000,7777779999999999,MASTERCARD
```

---

## Example 2 — Full coverage, no gaps

**Input:**
```
424242
2
0000000000,4999999999,VISA
5000000000,9999999999,MASTERCARD
```

**Output:**
```
4242420000000000,4242424999999999,VISA
4242425000000000,4242429999999999,MASTERCARD
```

---

## Example 3 — Gap in the middle

**Input:**
```
123456
3
0000000000,1999999999,VISA
3000000000,5999999999,MASTERCARD
7000000000,9999999999,AMEX
```

**Gaps identified:**
- Middle gap: `2000000000` – `2999999999` → extend VISA forward
- Middle gap: `6000000000` – `6999999999` → extend MASTERCARD forward

**Output:**
```
1234560000000000,1234562999999999,VISA
1234563000000000,1234565999999999,MASTERCARD
1234566000000000,1234566999999999,MASTERCARD
1234567000000000,1234569999999999,AMEX
```

---

## Notes

- If the input already covers the full BIN range, return it sorted with no changes.
- Intervals in the input are **non-overlapping** and may be given in **arbitrary order**.
- Output endpoints are **inclusive** on both sides.
- A middle gap is absorbed by the **left** (lower) interval's brand.