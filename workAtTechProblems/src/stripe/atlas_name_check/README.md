# Problem 1: Atlas Company Name Check

## Background

Stripe Atlas allows founders to register a US company remotely from anywhere in the world. A key step in the registration process is verifying the availability of the proposed company name. To avoid confusion, the check doesn’t just look for exact matches—names that are too similar (per specific rules) are also considered unavailable.

---

# Part 1: Basic Name Availability Check

We need to determine if a proposed company name is available by **normalizing it** and comparing it against previously registered names (after applying the same normalization rules).

## Normalization Rules

Two company names are considered identical if they match after applying the following rules:

1. **Ignore case**

   ```
   "Llama, Inc." == "LLAMA, Inc."
   ```

2. **Treat `&` and `,` as spaces**

3. **Collapse consecutive spaces into a single space**

4. **Ignore standard company suffixes** (case-insensitive)

   ```
   ["Inc.", "Corp.", "LLC", "L.L.C.", "LLC."]
   ```

5. **Ignore leading articles**

   ```
   "The", "An", "A"
   ```

   Example:

   ```
   "Llama, Inc." == "The Llama, Inc."
   ```

6. **Ignore the word "And" unless it appears at the start**

   ```
   "Llama And Friend, Inc." == "Llama Friend, Inc."
   "And Llama Friend, Inc." is distinct
   ```

7. **Invalid Names**

   If the normalized name is empty or contains only spaces, it is considered **unavailable**.

---

## Input

A table of **name check requests**.

Each line contains:

```
account_id | proposed_name
```

Example:

```
123 | Llama, Inc.
456 | The Llama Inc.
789 | Alpaca Corp.
```

---

## Output

For each request return:

```
account_id | Name Available
```

or

```
account_id | Name Not Available
```

### Rule

Once a name is marked **available**, it becomes **registered** and is **unavailable for all subsequent requests**.

---

# Part 2: Persistent Registration Tracking

Extend Part 1 to maintain a **permanent record of registered names**.

### Requirements

* The system must store **all normalized registered names**
* If any merchant submits a name that **matches a previously registered normalized name**, it must be marked:

```
Name Not Available
```

even if the merchant attempting registration is the same.

---

# Part 3: Name Reclamation Requests

Companies may dissolve, freeing up their names for reuse.

The system now supports **reclamation requests**.

### Input Format

```
RECLAIM,account_id,original_proposed_name
```

Example:

```
RECLAIM,123,Llama Inc.
```

### Rules

* The system must normalize the `original_proposed_name`
* Remove the normalized name from the **unavailable list**
* Only the **account that originally registered the name** is authorized to reclaim it

If a different account attempts reclamation, the request should be ignored.

---

# Summary

The system must support:

1. **Company name normalization**
2. **Availability checking**
3. **Persistent tracking of registered names**
4. **Authorized name reclamation**

---

# Expected System Behavior

| Request Type           | Description                                                  |
| ---------------------- | ------------------------------------------------------------ |
| Registration           | Check normalized name availability and register if available |
| Duplicate Registration | Return `Name Not Available`                                  |
| Reclamation            | Remove registered name if requested by original owner        |

---