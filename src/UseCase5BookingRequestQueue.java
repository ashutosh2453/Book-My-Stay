import java.util.LinkedList;
import java.util.Queue;

/**
 * Book My Stay Application
 *
 * Use Case 5: Booking Request (First-Come-First-Served)
 *
 * This program demonstrates how booking requests are collected
 * using a Queue data structure. Requests are stored in arrival
 * order to ensure fairness using the FIFO principle.
 *
 * No inventory updates or allocations occur at this stage.
 *
 * @author Ashutosh Chauhan
 * @version 5.1
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

    public void displayReservation() {
        System.out.println("Guest: " + guestName + " | Requested Room: " + roomType);
    }
}


/* =========================
   Booking Request Queue
   ========================= */

class BookingRequestQueue {

    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    // Add booking request
    public void addRequest(Reservation reservation) {

        requestQueue.add(reservation);

        System.out.println("Booking request added for " + reservation.getGuestName());
    }

    // Display queued requests
    public void displayQueue() {

        System.out.println("\n----- Booking Request Queue -----");

        for (Reservation r : requestQueue) {
            r.displayReservation();
        }
    }
}


/* =========================
   Application Entry Point
   ========================= */

public class UseCase5BookingRequestQueue {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("        Book My Stay App");
        System.out.println("   Hotel Booking System v5.1");
        System.out.println("=================================\n");

        // Initialize booking queue
        BookingRequestQueue queue = new BookingRequestQueue();

        // Simulated booking requests
        Reservation r1 = new Reservation("Ashutosh", "Single Room");
        Reservation r2 = new Reservation("Isha", "Double Room");
        Reservation r3 = new Reservation("Rahul", "Suite Room");

        // Add requests to queue
        queue.addRequest(r1);
        queue.addRequest(r2);
        queue.addRequest(r3);

        // Display queue (FIFO order preserved)
        queue.displayQueue();
    }
}