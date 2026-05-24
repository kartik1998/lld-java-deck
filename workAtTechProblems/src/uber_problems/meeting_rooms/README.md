# Schedule Meeting API

## Problem Statement

Design and implement a reservation system for a predefined set of conference rooms.

The system is initialized with a list of unique room IDs such as:

```text
["RoomA", "RoomB", "RoomC"]
```

Implement an API:

```text
scheduleMeeting(startTime, endTime)
```

The API should:

- Reserve an available room for the given time interval
- Return a reservation object/identifier containing the allocated `roomId`
- Return `INVALID` (or an appropriate error) if no rooms are available for the requested time slot

A room cannot be double-booked for overlapping meeting times.

---

## Example

```text
init(["RoomA", "RoomB", "RoomC"])

scheduleMeeting(2, 4) -> RoomA
scheduleMeeting(2, 4) -> RoomB
scheduleMeeting(5, 6) -> RoomA
scheduleMeeting(1, 5) -> RoomC
scheduleMeeting(2, 4) -> INVALID
```

### Explanation

- `RoomA` and `RoomB` are already occupied during `[2,4]`
- `RoomC` is occupied during `[1,5]`
- Therefore, no room is available for another meeting in `[2,4]`

---

## Functional Requirements

### Core APIs

```text
init(roomIds)
scheduleMeeting(startTime, endTime)
```

### Follow-up Enhancements

1. **Booking Cancellation**
    - Add support for canceling an existing reservation

2. **Concurrency / Multithreading**
    - Ensure the system behaves correctly under concurrent booking requests
    - Prevent race conditions and double booking

---

## Expected Considerations

- Efficient overlap detection
- Optimal room allocation
- Time complexity of booking operations
- Scalability with increasing rooms and bookings
- Thread-safe design for concurrent environments