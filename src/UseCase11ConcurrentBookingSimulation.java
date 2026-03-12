import java.util.*;

/**
 * Book My Stay Application
 *
 * Use Case 11: Concurrent Booking Simulation (Thread Safety)
 *
 * This program demonstrates how multiple booking requests can be
 * processed concurrently using threads while maintaining a consistent
 * system state through synchronized access to shared resources.
 *
 * Author: Ashutosh Chauhan
 * Version: 11.1
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
   Shared Inventory Service
   ========================= */

class InventoryService {

    private Map<String, Integer> inventory = new HashMap<>();

    public InventoryService() {

        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    // Critical section protected by synchronization
    public synchronized boolean allocateRoom(String roomType, String guestName) {

        int available = inventory.getOrDefault(roomType, 0);

        if (available > 0) {

            inventory.put(roomType, available - 1);

            System.out.println(
                    "Booking Confirmed -> Guest: " +
                            guestName + " | Room Type: " + roomType +
                            " | Remaining: " + (available - 1)
            );

            return true;
        }
        else {

            System.out.println(
                    "Booking Failed -> Guest: " +
                            guestName + " | Room Type: " + roomType +
                            " | Reason: No Rooms Available"
            );

            return false;
        }
    }

    public void displayInventory() {

        System.out.println("\nFinal Inventory State:");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {

            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}


/* =========================
   Concurrent Booking Worker
   ========================= */

class BookingProcessor extends Thread {

    private Reservation reservation;
    private InventoryService inventory;

    public BookingProcessor(Reservation reservation, InventoryService inventory) {

        this.reservation = reservation;
        this.inventory = inventory;
    }

    @Override
    public void run() {

        inventory.allocateRoom(
                reservation.getRoomType(),
                reservation.getGuestName()
        );
    }
}


/* =========================
   Application Entry Point
   ========================= */

public class UseCase11ConcurrentBookingSimulation {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("        Book My Stay App");
        System.out.println("   Hotel Booking System v11.1");
        System.out.println("=================================\n");


        InventoryService inventory = new InventoryService();


        // Simulated concurrent booking requests
        Reservation r1 = new Reservation("Ashutosh", "Single Room");
        Reservation r2 = new Reservation("Isha", "Single Room");
        Reservation r3 = new Reservation("Rahul", "Single Room");
        Reservation r4 = new Reservation("Ananya", "Double Room");
        Reservation r5 = new Reservation("Karan", "Suite Room");


        // Create threads for concurrent processing
        Thread t1 = new BookingProcessor(r1, inventory);
        Thread t2 = new BookingProcessor(r2, inventory);
        Thread t3 = new BookingProcessor(r3, inventory);
        Thread t4 = new BookingProcessor(r4, inventory);
        Thread t5 = new BookingProcessor(r5, inventory);


        // Start all threads simultaneously
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();


        // Wait for threads to complete
        try {

            t1.join();
            t2.join();
            t3.join();
            t4.join();
            t5.join();

        } catch (InterruptedException e) {

            System.out.println("Thread interrupted.");
        }


        // Display final inventory
        inventory.displayInventory();
    }
}