# Stripe Payment Card Validation System

## Background

Stripe processes billions of dollars through various payment methods. To ensure payment security, card numbers must be validated before processing.

This system requires:
- Network detection (VISA, MASTERCARD, AMEX)
- Luhn algorithm validation
- Handling of redacted and corrupted card inputs

---

## Card Networks

| Network    | Length   | Prefix     |
|------------|----------|------------|
| VISA       | 16 digits | Starts with `4` |
| MASTERCARD | 16 digits | Starts with `51`–`55` |
| AMEX       | 15 digits | Starts with `34` or `37` |

---

## Luhn Algorithm

1. From the **rightmost digit** (excluding the check digit), double every second digit.
2. If a doubled digit **> 9**, subtract 9.
3. Sum **all** digits.
4. If `sum % 10 == 0` → **valid**.

### Example

```
Card:   4532015112830366
Step 1: 8 5 6 2 0 1 1 1 2 2 7 3 0 3 3 6   ← doubled every 2nd from right
Step 2: Sum = 50
Step 3: 50 % 10 = 0 → Valid
```

---

## Part 1 — Basic Visa Validation (Test Cases 1–5)

**Input:** 16-digit number starting with `4`.

**Output:**
- `VISA` — if checksum passes
- `INVALID_CHECKSUM` — if checksum fails

### Examples

| Input                | Output             |
|---------------------|--------------------|
| `4532015112830366`  | `VISA`             |
| `4242424242424243`  | `INVALID_CHECKSUM` |

---

## Part 2 — Multi-Network Validation (Test Cases 6–10)

**Input:** 15- or 16-digit card number.

**Output:**
- Network name (`VISA` / `MASTERCARD` / `AMEX`) — if valid
- `INVALID_CHECKSUM` — if checksum fails
- `UNKNOWN_NETWORK` — if length or prefix does not match any known network

### Examples

| Input              | Output            | Reason                    |
|-------------------|-------------------|---------------------------|
| `5482334509943`   | `UNKNOWN_NETWORK` | 13 digits                 |
| `4425233430109994`| `VISA`            | Valid 16-digit VISA       |
| `562523343010901` | `UNKNOWN_NETWORK` | Prefix `56` unrecognized  |

---

## Part 3 — Redacted Cards (Test Cases 11–15)

**Input:** A card number containing `*` (representing 1–5 redacted digits).

**Output:** Count of valid cards per network, sorted **alphabetically** by network name.

### Format

```
NETWORK,count
```

### Examples

| Input                  | Output                        |
|-----------------------|-------------------------------|
| `4242424242424*42`    | `VISA,1`                      |
| `3*8282246310005`     | `AMEX,2`                      |
| `**2424242424242`     | `MASTERCARD,5`<br>`VISA,10`   |

### Notes
- Enumerate all digit combinations for `*` positions (0–9 per `*`).
- For each candidate, detect network and run Luhn validation.
- Aggregate counts by network; only include networks with count > 0.

---

## Part 4 — Corrupted Cards (Test Cases 16–20)

**Input:** A card number ending with `?`, indicating exactly **one** error occurred:
- One digit may have been **changed** (to any digit 0–9), **or**
- Two **adjacent** digits may have been **swapped**.

**Output:** All possible valid original cards, in **ascending numeric order**.

### Format

```
card_number,NETWORK
```

### Example

**Input:** `4344555566660004?`

**Output (partial):**
```
4342555566660004,VISA
4344555566660004,VISA
4344555566660014,VISA
...
```

### Notes
- The `?` at the end is a sentinel — strip it before processing; the actual card digits precede it.
- For **digit change**: iterate each position, try all 10 digit replacements.
- For **adjacent swap**: iterate each pair of adjacent digits and swap them.
- Deduplicate candidates before validating.
- Sort final results numerically (ascending).

---

## Implementation Notes

- Output strings must match exactly (network names uppercase, comma-separated).
- **Part 3:** Sort results alphabetically by network name; omit networks with zero valid cards.
- **Part 4:** Sort results numerically (ascending) by card number.
- Handle edge cases: wrong lengths, invalid prefixes, leading zeros after substitution.
- Optimize for large search spaces (e.g., multiple `*` wildcards → up to 10⁵ combinations).

---

## Output Format Summary

| Part | Output Format                        |
|------|--------------------------------------|
| 1–2  | `NETWORK` or `INVALID_CHECKSUM` or `UNKNOWN_NETWORK` |
| 3    | `NETWORK,count` (one per line, alphabetical) |
| 4    | `card_number,NETWORK` (one per line, numeric ascending) |