import java.util.HashMap;
import java.util.Map;

/**
 * Book My Stay Application
 *
 * Use Case 3: Centralized Room Inventory Management
 *
 * This program demonstrates how room availability can be managed
 * using a centralized inventory system backed by a HashMap.
 *
 * The inventory acts as the single source of truth for room
 * availability across the system.
 *
 * @author Ashutosh Chauhan
 * @version 3.1
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

    public void displayRoomDetails() {
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
   Inventory Management
   ========================= */

class RoomInventory {

    private HashMap<String, Integer> inventory;

    // Constructor initializes the inventory
    public RoomInventory() {

        inventory = new HashMap<>();

        inventory.put("Single Room", 10);
        inventory.put("Double Room", 6);
        inventory.put("Suite Room", 3);
    }

    // Retrieve availability
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Update availability
    public void updateAvailability(String roomType, int newCount) {
        inventory.put(roomType, newCount);
    }

    // Display full inventory
    public void displayInventory() {

        System.out.println("----- Current Room Inventory -----");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {

            System.out.println(entry.getKey() + " : " + entry.getValue() + " rooms available");
        }
    }
}


/* =========================
   Application Entry Point
   ========================= */

public class UseCase3InventorySetup {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("        Book My Stay App");
        System.out.println("   Hotel Booking System v3.1");
        System.out.println("=================================\n");


        // Create room objects
        Room single = new SingleRoom();
        Room dbl = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Display room details
        System.out.println("----- Room Types -----\n");

        single.displayRoomDetails();
        System.out.println();

        dbl.displayRoomDetails();
        System.out.println();

        suite.displayRoomDetails();
        System.out.println();


        // Initialize centralized inventory
        RoomInventory inventory = new RoomInventory();

        // Display current inventory
        System.out.println();
        inventory.displayInventory();


        // Example update
        System.out.println("\nUpdating inventory for Single Room...\n");

        inventory.updateAvailability("Single Room", 8);

        // Display updated inventory
        inventory.displayInventory();
    }
}