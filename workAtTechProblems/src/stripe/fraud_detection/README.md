# Problem 3: Catch Me If You Can — Fraud Detection

## Background

Stripe processes billions of dollars worth of transactions every day. Our job is to protect customers and legitimate merchants by detecting and blocking fraudulent transactions.

In this problem, we will build a simplified fraud detection model that marks merchants as fraudulent if too many of their transactions are suspicious.

The problem is divided into **three parts**.

---

# Part 1: Count-Based Fraud Detection

Each merchant has a **Merchant Consumer Code (MCC)** that represents their industry (for example: retail, airline, etc.).

Each MCC has an associated **fraud threshold** (an integer > 1) that indicates the **maximum allowed number of fraudulent transactions before the merchant is marked as fraudulent**.

## Inputs

You are given the following:

### 1. Non-Fraudulent Codes

A comma-separated list of transaction codes that represent **non-fraudulent outcomes**.

Example:

```
approved,invalid_pin,expired_card
```

---

### 2. Fraudulent Codes

A comma-separated list of transaction codes that represent **fraudulent outcomes**.

Example:

```
do_not_honor,stolen_card,lost_card
```

---

### 3. MCC Fraud Threshold Table

A table containing MCC values and their corresponding fraud thresholds.

Format:

```
MCC,threshold
```

Example:

```
5411,3
3000,5
```

---

### 4. Merchant Table

A table mapping merchants to their MCC.

Format:

```
account_id,MCC
```

Example:

```
acct_1,5411
acct_2,3000
```

---

### 5. Minimum Transaction Requirement

An integer **≥ 0** representing the **minimum number of total transactions that must be observed before evaluating a merchant**.

---

### 6. Charges Table

Transaction records appear in the following format:

```
CHARGE,charge_id,account_id,amount,code
```

Example:

```
CHARGE,ch_1,acct_1,100,approved
CHARGE,ch_2,acct_1,50,stolen_card
```

---

## Output

Return a **lexicographically sorted**, **comma-separated list of fraudulent merchants** based on `account_id`.

Example:

```
acct_1,acct_7,acct_9
```

---

# Part 2: Percentage-Based Fraud Detection

Count-based thresholds can unfairly penalize **high-volume merchants**. To address this, we switch to **percentage-based fraud thresholds**.

## Changes from Part 1

Instead of an integer threshold, each MCC now contains a **fraction between 0 and 1**.

This fraction represents the **maximum allowed percentage of fraudulent transactions**.

### Rule

A merchant is marked as **fraudulent if**:

```
fraudulent_transactions / total_transactions ≥ threshold
```

---

### Additional Rules

* Merchants **remain fraudulent permanently**, even if their fraud percentage later decreases.
* Merchants are **only evaluated after reaching the minimum number of total transactions**.
* All other inputs remain the same as Part 1, except that the MCC table now contains **fractions instead of integers**.

Example MCC table:

```
5411,0.20
3000,0.15
```

---

# Part 3: Dispute Resolution

Sometimes transactions are incorrectly flagged as fraudulent. To address this, the system now supports **disputes**.

## New Input Type

A dispute record has the format:

```
DISPUTE,charge_id
```

Example:

```
DISPUTE,ch_2
```

---

## Dispute Behavior

When a dispute occurs:

* The referenced transaction is **treated as non-fraudulent** for all calculations.
* Fraud counts and percentages must be **recalculated accordingly**.

---

### Merchant Status Updates

If a merchant was marked as fraudulent **only because of disputed transactions**, they may:

* Return to **non-fraudulent status**.
* Remain non-fraudulent until **new transactions push them past the threshold again**.

---

# Summary of System Behavior

| Feature                         | Part 1 | Part 2     | Part 3                |
| ------------------------------- | ------ | ---------- | --------------------- |
| Threshold Type                  | Count  | Percentage | Percentage            |
| Fraud Status Reversible         | No     | No         | Yes (due to disputes) |
| Minimum Transaction Requirement | Yes    | Yes        | Yes                   |
| Dispute Handling                | No     | No         | Yes                   |

---

# Goal

Implement a fraud detection system that processes transactions and disputes while correctly identifying fraudulent merchants based on MCC-specific thresholds.

The final output should always be a **lexicographically sorted list of fraudulent merchant account IDs**.