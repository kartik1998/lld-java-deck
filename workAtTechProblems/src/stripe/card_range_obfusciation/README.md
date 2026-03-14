# Problem 2: Card Range Obfuscation

## Problem Description

Payment card numbers consist of **8–19 digits**, with the **first 6 digits referred to as the Bank Identification Number (BIN)**.

For a given BIN, all **16-digit card numbers starting with that BIN** are considered to be in the **BIN range**.

Example:

BIN: `424242`

Range:
- Start: `4242420000000000` (inclusive)
- End: `4242429999999999` (inclusive)

Stripe’s **card metadata API** may return **partial coverage** of this BIN range by providing a list of intervals mapping to card brands (e.g., `VISA`, `MASTERCARD`).

However, these intervals may contain **gaps**:
- At the **beginning**
- In the **middle**
- At the **end**

These gaps can potentially be exploited by fraudsters probing for valid cards.

Your task is to **fill the missing intervals** so that the returned intervals **fully cover the entire BIN range with no gaps**, and return them **in sorted order**.

---

# Input Format

1. **Line 1:**  
   A **6-digit BIN**.

2. **Line 2:**  
   A positive integer **n**, representing the number of intervals.

3. **Next n lines:**  
   Each line represents an interval in the format:
