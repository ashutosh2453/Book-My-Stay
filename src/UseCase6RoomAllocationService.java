import java.util.*;

/**
 * Book My Stay Application
 *
 * Use Case 6: Reservation Confirmation & Room Allocation
 *
 * This program processes booking requests from a queue,
 * allocates unique room IDs, prevents double booking,
 * and updates inventory immediately after allocation.
 *
 * Author: Ashutosh Chauhan
 * Version: 6.1
 */


/* =========================
   Reservation Model
   ========================= */

class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}


/* =========================
   Booking Request Queue
   ========================= */

class BookingQueue {

    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation r) {
        queue.add(r);
        System.out.println("Request received from " + r.getGuestName());
    }

    public Reservation getNextRequest() {
        return queue.poll(); // FIFO
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}


/* =========================
   Inventory Service
   ========================= */

class InventoryService {

    private HashMap<String, Integer> inventory = new HashMap<>();

    public InventoryService() {

        inventory.put("Single Room", 2);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void decrementRoom(String roomType) {

        int available = inventory.get(roomType);
        inventory.put(roomType, available - 1);
    }

    public void displayInventory() {

        System.out.println("\nCurrent Inventory:");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}


/* =========================
   Room Allocation Service
   ========================= */

class RoomAllocationService {

    private HashMap<String, Set<String>> allocatedRooms = new HashMap<>();
    private int roomCounter = 1;

    public String allocateRoom(String roomType) {

        allocatedRooms.putIfAbsent(roomType, new HashSet<>());

        String roomId;

        do {
            roomId = roomType.replace(" ", "").substring(0,3).toUpperCase() + roomCounter++;
        }
        while (allocatedRooms.get(roomType).contains(roomId));

        allocatedRooms.get(roomType).add(roomId);

        return roomId;
    }
}


/* =========================
   Application Entry
   ========================= */

public class UseCase6RoomAllocationService {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("        Book My Stay App");
        System.out.println("   Hotel Booking System v6.1");
        System.out.println("=================================\n");


        BookingQueue queue = new BookingQueue();
        InventoryService inventory = new InventoryService();
        RoomAllocationService allocator = new RoomAllocationService();


        // Simulated booking requests
        queue.addRequest(new Reservation("Ashutosh", "Single Room"));
        queue.addRequest(new Reservation("Isha", "Double Room"));
        queue.addRequest(new Reservation("Rahul", "Single Room"));
        queue.addRequest(new Reservation("Ananya", "Suite Room"));


        System.out.println("\nProcessing Booking Requests...\n");

        while (!queue.isEmpty()) {

            Reservation request = queue.getNextRequest();

            String roomType = request.getRoomType();

            if (inventory.getAvailability(roomType) > 0) {

                String roomId = allocator.allocateRoom(roomType);

                inventory.decrementRoom(roomType);

                System.out.println(
                        "Reservation Confirmed -> Guest: "
                                + request.getGuestName()
                                + " | Room Type: " + roomType
                                + " | Room ID: " + roomId
                );

            }
            else {

                System.out.println(
                        "Reservation Failed -> Guest: "
                                + request.getGuestName()
                                + " | Room Type: " + roomType
                                + " | Reason: No Rooms Available"
                );
            }
        }

        inventory.displayInventory();
    }
}