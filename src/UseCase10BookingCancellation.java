import java.util.*;

/**
 * Book My Stay Application
 *
 * Use Case 10: Booking Cancellation & Inventory Rollback
 *
 * Demonstrates safe cancellation of confirmed bookings by
 * reversing room allocation and restoring inventory.
 *
 * Author: Ashutosh Chauhan
 * Version: 10.1
 */


/* =========================
   Reservation Model
   ========================= */

class Reservation {

    private String reservationId;
    private String guestName;
    private String roomType;
    private String roomId;

    public Reservation(String reservationId, String guestName, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }

    public void display() {
        System.out.println("Reservation ID: " + reservationId +
                " | Guest: " + guestName +
                " | Room Type: " + roomType +
                " | Room ID: " + roomId);
    }
}


/* =========================
   Inventory Service
   ========================= */

class InventoryService {

    private Map<String, Integer> inventory = new HashMap<>();

    public InventoryService() {
        inventory.put("Single Room", 1);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 0);
    }

    public void incrementRoom(String roomType) {

        int available = inventory.getOrDefault(roomType, 0);

        inventory.put(roomType, available + 1);
    }

    public void displayInventory() {

        System.out.println("\nCurrent Inventory:");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}


/* =========================
   Cancellation Service
   ========================= */

class CancellationService {

    private Stack<String> rollbackStack = new Stack<>();

    private Map<String, Reservation> activeReservations = new HashMap<>();

    public void addReservation(Reservation reservation) {
        activeReservations.put(reservation.getReservationId(), reservation);
    }

    public void cancelReservation(String reservationId, InventoryService inventory) {

        if (!activeReservations.containsKey(reservationId)) {

            System.out.println("Cancellation Failed -> Reservation not found: " + reservationId);
            return;
        }

        Reservation reservation = activeReservations.remove(reservationId);

        // Push roomId to rollback stack
        rollbackStack.push(reservation.getRoomId());

        // Restore inventory
        inventory.incrementRoom(reservation.getRoomType());

        System.out.println("Reservation Cancelled -> Room Released: " + reservation.getRoomId());
    }

    public void displayRollbackStack() {

        System.out.println("\nRollback Stack (Recently Released Rooms):");

        for (String roomId : rollbackStack) {
            System.out.println(roomId);
        }
    }
}


/* =========================
   Application Entry
   ========================= */

public class UseCase10BookingCancellation {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("        Book My Stay App");
        System.out.println("   Hotel Booking System v10.1");
        System.out.println("=================================\n");


        InventoryService inventory = new InventoryService();
        CancellationService cancellationService = new CancellationService();


        // Simulated confirmed reservations
        Reservation r1 = new Reservation("RES201", "Ashutosh", "Single Room", "SIN101");
        Reservation r2 = new Reservation("RES202", "Isha", "Double Room", "DOU201");

        cancellationService.addReservation(r1);
        cancellationService.addReservation(r2);


        System.out.println("Confirmed Reservations:");
        r1.display();
        r2.display();


        // Perform cancellations
        System.out.println("\nProcessing Cancellation...\n");

        cancellationService.cancelReservation("RES201", inventory);

        // Invalid cancellation attempt
        cancellationService.cancelReservation("RES999", inventory);


        // Display rollback structure
        cancellationService.displayRollbackStack();


        // Display updated inventory
        inventory.displayInventory();
    }
}