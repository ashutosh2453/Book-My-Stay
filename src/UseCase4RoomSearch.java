import java.util.HashMap;
import java.util.Map;

/**
 * Book My Stay Application
 *
 * Use Case 4: Room Search & Availability Check
 *
 * This program allows guests to search for available rooms.
 * The system retrieves availability from the centralized inventory
 * and displays only room types that currently have availability.
 *
 * Search operations are read-only and do not modify system state.
 *
 * @author Ashutosh Chauhan
 * @version 4.1
 */


/* =========================
   Room Domain Model
   ========================= */

abstract class Room {

    protected String roomType;
    protected int beds;
    protected double price;

    public Room(String roomType, int beds, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.price = price;
    }

    public String getRoomType() {
        return roomType;
    }

    public void displayDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Beds: " + beds);
        System.out.println("Price per Night: ₹" + price);
    }
}


class SingleRoom extends Room {

    public SingleRoom() {
        super("Single Room", 1, 2000);
    }
}


class DoubleRoom extends Room {

    public DoubleRoom() {
        super("Double Room", 2, 3500);
    }
}


class SuiteRoom extends Room {

    public SuiteRoom() {
        super("Suite Room", 3, 6000);
    }
}


/* =========================
   Centralized Inventory
   ========================= */

class RoomInventory {

    private HashMap<String, Integer> inventory;

    public RoomInventory() {

        inventory = new HashMap<>();

        inventory.put("Single Room", 10);
        inventory.put("Double Room", 6);
        inventory.put("Suite Room", 0); // example unavailable room
    }

    // Read-only availability access
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }
}


/* =========================
   Search Service
   ========================= */

class RoomSearchService {

    public static void searchAvailableRooms(RoomInventory inventory, Room[] rooms) {

        System.out.println("----- Available Rooms -----\n");

        for (Room room : rooms) {

            int available = inventory.getAvailability(room.getRoomType());

            // Filter unavailable rooms
            if (available > 0) {

                room.displayDetails();
                System.out.println("Available Rooms: " + available);
                System.out.println();
            }
        }
    }
}


/* =========================
   Application Entry
   ========================= */

public class UseCase4RoomSearch {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("        Book My Stay App");
        System.out.println("   Hotel Booking System v4.1");
        System.out.println("=================================\n");

        // Create room objects
        Room single = new SingleRoom();
        Room dbl = new DoubleRoom();
        Room suite = new SuiteRoom();

        Room[] rooms = { single, dbl, suite };

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Perform search (read-only)
        RoomSearchService.searchAvailableRooms(inventory, rooms);
    }
}