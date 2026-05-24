package uber_problems.meeting_rooms;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Main {
    static class Reservation {
        public final Room room;
        public final int startTime, endTime;

        public Reservation(Room room, int startTime, int endTime) {
            this.room = room;
            this.startTime = startTime;
            this.endTime = endTime;
        }
    }

    static class Room {
        String id;
        TreeMap<Integer, Integer> map;
        ReadWriteLock lock = new ReentrantReadWriteLock();

        public Room(String id) {
            this.id = id;
            this.map = new TreeMap<>();
        }

        public Reservation reserve(int startTime, int endTime) {
            try {
                lock.writeLock().lock();
                Map.Entry<Integer, Integer> prev = map.floorEntry(startTime);
                if (prev != null && prev.getValue() > startTime) return null;

                Map.Entry<Integer, Integer> next = map.ceilingEntry(startTime);
                if (next != null && next.getKey() < endTime) return null;

                map.put(startTime, endTime);
            } finally {
                lock.writeLock().unlock();
            }
            return new Reservation(this, startTime, endTime);
        }

    }

    static class MeetingHandler {
        private List<Room> rooms;
        private List<Reservation> reservations = new ArrayList<>();
        private ReadWriteLock lock = new ReentrantReadWriteLock();

        public MeetingHandler(List<String> roomList) {
            this.rooms = new ArrayList<>();
            for (String roomId : roomList) {
                rooms.add(new Room(roomId));
            }
        }

        public Reservation scheduleMeeting(int startTime, int endTime) {
            for (Room room : rooms) {
                Reservation reservation = room.reserve(startTime, endTime);
                if (reservation != null) {
                    try {
                        lock.writeLock().lock();
                        reservations.add(reservation);
                    } finally {
                        lock.writeLock().unlock();
                    }
                    return reservation;
                }
            }
            return null;
        }

        public List<Reservation> getList(int startTime) {
            List<Reservation> retval = new ArrayList<>();
            try {
                lock.readLock().lock();
                for (Reservation reservation : reservations) {
                    if (reservation.startTime > startTime) retval.add(reservation);
                }
            } finally {
                lock.readLock().unlock();
            }
            return retval;
        }
    }

    public static void main(String[] args) {
        MeetingHandler handler = new MeetingHandler(List.of("A", "B", "C", "D"));
        // Fill all 4 rooms with the same slot; 5th booking must fail
        Reservation r1 = handler.scheduleMeeting(9, 11);
        assert r1 != null && r1.room.id.equals("A") : "Expected A";
        Reservation r2 = handler.scheduleMeeting(9, 11);
        assert r2 != null && r2.room.id.equals("B") : "Expected B";
        Reservation r3 = handler.scheduleMeeting(9, 11);
        assert r3 != null && r3.room.id.equals("C") : "Expected C";
        Reservation r4 = handler.scheduleMeeting(9, 11);
        assert r4 != null && r4.room.id.equals("D") : "Expected D";
        Reservation r5 = handler.scheduleMeeting(9, 11);
        assert r5 == null : "Expected null — all rooms at capacity";
        System.out.println("r1-r4: filled all rooms [9,11]; r5: no room (correct)");

        // Back-to-back on A: starts exactly when A's [9,11] ends
        // A: floor(11)->{9,11}, value 11 > 11? No. ceiling(11)=null → OK
        Reservation r6 = handler.scheduleMeeting(11, 14);
        assert r6 != null && r6.room.id.equals("A") : "Expected A back-to-back";
        System.out.println("r6: room=" + r6.room.id + " [11,14] — back-to-back on A");

        // Earlier gap on A: [5,8] fits before A's [9,11]
        // A: floor(5)=null, ceiling(5)->{9,11}, 9 < 8? No → OK
        Reservation r7 = handler.scheduleMeeting(5, 8);
        assert r7 != null && r7.room.id.equals("A") : "Expected A for [5,8]";
        System.out.println("r7: room=" + r7.room.id + " [5,8] — earlier gap on A");

        // [7,10] conflicts everywhere: hits A's [5,8] via floor, and [9,11] on B/C/D via ceiling
        Reservation r8 = handler.scheduleMeeting(7, 10);
        assert r8 == null : "Expected null — [7,10] conflicts with all rooms";
        System.out.println("r8: no room for [7,10] (correct)");

        // Chain on A: [14,16] after A's [11,14]
        Reservation r9 = handler.scheduleMeeting(14, 16);
        assert r9 != null && r9.room.id.equals("A") : "Expected A for [14,16]";
        System.out.println("r9: room=" + r9.room.id + " [14,16] — chained on A");

        // Back-to-back on B: B only has [9,11], so [11,13] is free
        // B: floor(11)->{9,11}, value 11 > 11? No. ceiling(11)=null → OK
        Reservation r10 = handler.scheduleMeeting(11, 13);
        assert r10 != null && r10.room.id.equals("B") : "Expected B for [11,13]";
        System.out.println("r10: room=" + r10.room.id + " [11,13] — back-to-back on B");

        // [0,5] fits in A before A's [5,8]: ceiling(0)->{5,8}, 5 < 5? No (strict) → OK
        Reservation r11 = handler.scheduleMeeting(0, 5);
        assert r11 != null && r11.room.id.equals("A") : "Expected A for [0,5]";
        System.out.println("r11: room=" + r11.room.id + " [0,5] — fits before A's [5,8]");

        System.out.println("All assertions passed.");
    }
}
