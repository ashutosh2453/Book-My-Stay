import java.util.*;

/**
 * Book My Stay Application
 *
 * Use Case 7: Add-On Service Selection
 *
 * This program demonstrates how optional services can be
 * attached to an existing reservation without modifying
 * the core booking or inventory logic.
 *
 * Author: Ashutosh Chauhan
 * Version: 7.1
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

    public void displayReservation() {
        System.out.println("Reservation ID: " + reservationId);
        System.out.println("Guest: " + guestName);
        System.out.println("Room Type: " + roomType);
    }
}


/* =========================
   Add-On Service Model
   ========================= */

class AddOnService {

    private String serviceName;
    private double cost;

    public AddOnService(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public double getCost() {
        return cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void displayService() {
        System.out.println(serviceName + " - ₹" + cost);
    }
}


/* =========================
   Add-On Service Manager
   ========================= */

class AddOnServiceManager {

    // Map ReservationID -> List of Services
    private Map<String, List<AddOnService>> reservationServices = new HashMap<>();


    // Add service to reservation
    public void addService(String reservationId, AddOnService service) {

        reservationServices.putIfAbsent(reservationId, new ArrayList<>());

        reservationServices.get(reservationId).add(service);

        System.out.println("Service added: " + service.getServiceName());
    }


    // Display services
    public void displayServices(String reservationId) {

        System.out.println("\nSelected Services:");

        List<AddOnService> services = reservationServices.get(reservationId);

        if (services == null) {
            System.out.println("No services selected.");
            return;
        }

        for (AddOnService s : services) {
            s.displayService();
        }
    }


    // Calculate total cost
    public double calculateTotalCost(String reservationId) {

        double total = 0;

        List<AddOnService> services = reservationServices.get(reservationId);

        if (services != null) {

            for (AddOnService s : services) {
                total += s.getCost();
            }
        }

        return total;
    }
}


/* =========================
   Application Entry
   ========================= */

public class UseCase7AddOnServiceSelection {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("        Book My Stay App");
        System.out.println("   Hotel Booking System v7.1");
        System.out.println("=================================\n");


        // Existing reservation
        Reservation reservation = new Reservation("RES101", "Ashutosh", "Double Room");

        reservation.displayReservation();


        // Create service manager
        AddOnServiceManager serviceManager = new AddOnServiceManager();


        // Available services
        AddOnService breakfast = new AddOnService("Breakfast", 500);
        AddOnService airportPickup = new AddOnService("Airport Pickup", 1200);
        AddOnService spa = new AddOnService("Spa Access", 800);


        // Guest selects services
        serviceManager.addService(reservation.getReservationId(), breakfast);
        serviceManager.addService(reservation.getReservationId(), airportPickup);
        serviceManager.addService(reservation.getReservationId(), spa);


        // Display selected services
        serviceManager.displayServices(reservation.getReservationId());


        // Calculate total add-on cost
        double totalCost = serviceManager.calculateTotalCost(reservation.getReservationId());

        System.out.println("\nTotal Add-On Cost: ₹" + totalCost);
    }
}