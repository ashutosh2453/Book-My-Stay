import java.io.*;
import java.util.*;

/**
 * Book My Stay Application
 *
 * Use Case 12: Data Persistence & System Recovery
 *
 * Demonstrates saving booking history and inventory
 * to a file using serialization and restoring the state
 * when the system restarts.
 *
 * Author: Ashutosh Chauhan
 * Version: 12.1
 */


/* =========================
   Reservation Model
   ========================= */

class Reservation implements Serializable {

    private static final long serialVersionUID = 1L;

    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public void display() {
        System.out.println(
                "Reservation ID: " + reservationId +
                        " | Guest: " + guestName +
                        " | Room Type: " + roomType
        );
    }
}


/* =========================
   System State Container
   ========================= */

class SystemState implements Serializable {

    private static final long serialVersionUID = 1L;

    Map<String, Integer> inventory;
    List<Reservation> bookingHistory;

    public SystemState(Map<String, Integer> inventory, List<Reservation> bookingHistory) {
        this.inventory = inventory;
        this.bookingHistory = bookingHistory;
    }
}


/* =========================
   Persistence Service
   ========================= */

class PersistenceService {

    private static final String FILE_NAME = "hotel_system_state.dat";


    // Save system state
    public static void saveState(SystemState state) {

        try (ObjectOutputStream out =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            out.writeObject(state);

            System.out.println("\nSystem state saved successfully.");

        } catch (IOException e) {

            System.out.println("Error saving system state: " + e.getMessage());
        }
    }


    // Load system state
    public static SystemState loadState() {

        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            System.out.println("System state restored from file.");

            return (SystemState) in.readObject();

        } catch (FileNotFoundException e) {

            System.out.println("No previous system state found. Starting fresh.");

        } catch (IOException | ClassNotFoundException e) {

            System.out.println("Error loading system state: " + e.getMessage());
        }

        return null;
    }
}


/* =========================
   Application Entry
   ========================= */

public class UseCase12DataPersistenceRecovery {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("        Book My Stay App");
        System.out.println("   Hotel Booking System v12.1");
        System.out.println("=================================\n");


        Map<String, Integer> inventory;
        List<Reservation> bookingHistory;


        // Attempt recovery
        SystemState recoveredState = PersistenceService.loadState();

        if (recoveredState != null) {

            inventory = recoveredState.inventory;
            bookingHistory = recoveredState.bookingHistory;

            System.out.println("\nRecovered Booking History:");

            for (Reservation r : bookingHistory) {
                r.display();
            }

        } else {

            inventory = new HashMap<>();
            bookingHistory = new ArrayList<>();

            inventory.put("Single Room", 2);
            inventory.put("Double Room", 1);
            inventory.put("Suite Room", 1);

            // Simulated bookings
            bookingHistory.add(new Reservation("RES301", "Ashutosh", "Single Room"));
            bookingHistory.add(new Reservation("RES302", "Isha", "Double Room"));
        }


        // Display inventory snapshot
        System.out.println("\nCurrent Inventory:");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {

            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }


        // Save system state before shutdown
        SystemState state = new SystemState(inventory, bookingHistory);

        PersistenceService.saveState(state);
    }
}