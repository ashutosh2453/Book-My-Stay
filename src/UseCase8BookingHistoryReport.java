import java.util.*;

/**
 * Book My Stay Application
 *
 * Use Case 8: Booking History & Reporting
 *
 * This program demonstrates how confirmed reservations
 * are stored in booking history and how reports can be
 * generated without modifying stored data.
 *
 * Author: Ashutosh Chauhan
 * Version: 8.1
 */


/* =========================
   Reservation Model
   ========================= */

class Reservation {

    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }

    public void displayReservation() {
        System.out.println("Reservation ID: " + reservationId +
                " | Guest: " + guestName +
                " | Room Type: " + roomType);
    }
}


/* =========================
   Booking History Storage
   ========================= */

class BookingHistory {

    private List<Reservation> history = new ArrayList<>();

    // Store confirmed booking
    public void addReservation(Reservation reservation) {

        history.add(reservation);

        System.out.println("Booking recorded: " + reservation.getReservationId());
    }

    // Retrieve booking history
    public List<Reservation> getHistory() {
        return history;
    }
}


/* =========================
   Reporting Service
   ========================= */

class BookingReportService {

    // Display all bookings
    public void displayAllBookings(List<Reservation> history) {

        System.out.println("\n----- Booking History -----");

        for (Reservation r : history) {
            r.displayReservation();
        }
    }

    // Generate summary report
    public void generateSummary(List<Reservation> history) {

        Map<String, Integer> roomCount = new HashMap<>();

        for (Reservation r : history) {

            roomCount.put(
                    r.getRoomType(),
                    roomCount.getOrDefault(r.getRoomType(), 0) + 1
            );
        }

        System.out.println("\n----- Booking Summary Report -----");

        for (Map.Entry<String, Integer> entry : roomCount.entrySet()) {

            System.out.println(entry.getKey() + " Bookings: " + entry.getValue());
        }
    }
}


/* =========================
   Application Entry Point
   ========================= */

public class UseCase8BookingHistoryReport {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("        Book My Stay App");
        System.out.println("   Hotel Booking System v8.1");
        System.out.println("=================================\n");


        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();


        // Simulated confirmed bookings
        Reservation r1 = new Reservation("RES101", "Ashutosh", "Single Room");
        Reservation r2 = new Reservation("RES102", "Isha", "Double Room");
        Reservation r3 = new Reservation("RES103", "Rahul", "Single Room");
        Reservation r4 = new Reservation("RES104", "Ananya", "Suite Room");


        // Store bookings in history
        history.addReservation(r1);
        history.addReservation(r2);
        history.addReservation(r3);
        history.addReservation(r4);


        // Admin views booking history
        reportService.displayAllBookings(history.getHistory());


        // Generate booking summary report
        reportService.generateSummary(history.getHistory());
    }
}