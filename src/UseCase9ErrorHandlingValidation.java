import java.util.HashMap;
import java.util.Map;

/**
 * Book My Stay Application
 *
 * Use Case 9: Error Handling & Validation
 *
 * Demonstrates validation of booking inputs and structured
 * error handling using custom exceptions to prevent invalid
 * system states.
 *
 * Author: Ashutosh Chauhan
 * Version: 9.1
 */


/* =========================
   Custom Exception
   ========================= */

class InvalidBookingException extends Exception {

    public InvalidBookingException(String message) {
        super(message);
    }
}


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
   Inventory Service
   ========================= */

class InventoryService {

    private Map<String, Integer> inventory = new HashMap<>();

    public InventoryService() {

        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 0);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, -1);
    }

    public void allocateRoom(String roomType) throws InvalidBookingException {

        if (!inventory.containsKey(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }

        int available = inventory.get(roomType);

        if (available <= 0) {
            throw new InvalidBookingException("No rooms available for: " + roomType);
        }

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
   Booking Validator
   ========================= */

class BookingValidator {

    public static void validateReservation(Reservation reservation) throws InvalidBookingException {

        if (reservation.getGuestName() == null || reservation.getGuestName().trim().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty.");
        }

        if (reservation.getRoomType() == null || reservation.getRoomType().trim().isEmpty()) {
            throw new InvalidBookingException("Room type must be specified.");
        }
    }
}


/* =========================
   Application Entry
   ========================= */

public class UseCase9ErrorHandlingValidation {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("        Book My Stay App");
        System.out.println("   Hotel Booking System v9.1");
        System.out.println("=================================\n");


        InventoryService inventory = new InventoryService();


        Reservation[] requests = {

                new Reservation("Ashutosh", "Single Room"),
                new Reservation("Isha", "Double Room"),
                new Reservation("", "Suite Room"),            // invalid guest
                new Reservation("Rahul", "Luxury Room")       // invalid room type
        };


        for (Reservation request : requests) {

            try {

                BookingValidator.validateReservation(request);

                inventory.allocateRoom(request.getRoomType());

                System.out.println("Reservation Confirmed -> Guest: "
                        + request.getGuestName()
                        + " | Room Type: " + request.getRoomType());

            }
            catch (InvalidBookingException e) {

                System.out.println("Booking Failed -> " + e.getMessage());
            }
        }

        inventory.displayInventory();
    }
}