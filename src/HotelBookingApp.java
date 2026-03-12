import java.util.*;

/**
 * Book My Stay Application
 * Use Case 9: Error Handling & Validation
 *
 * Demonstrates validation of booking inputs
 * and custom exception handling to prevent
 * invalid system states.
 *
 * @author Charukesh
 * @version 9.1
 */

/* ------------------ CUSTOM EXCEPTION ------------------ */

class InvalidBookingException extends Exception {

    public InvalidBookingException(String message) {
        super(message);
    }
}

/* ------------------ INVENTORY CLASS ------------------ */

class RoomInventory {

    private Map<String, Integer> inventory;

    public RoomInventory() {

        inventory = new HashMap<>();

        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 0);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, -1);
    }

    public void bookRoom(String roomType) throws InvalidBookingException {

        // Validate room type
        if (!inventory.containsKey(roomType)) {
            throw new InvalidBookingException("Invalid room type selected.");
        }

        int available = inventory.get(roomType);

        // Prevent negative inventory
        if (available <= 0) {
            throw new InvalidBookingException(
                    "No rooms available for " + roomType);
        }

        inventory.put(roomType, available - 1);

        System.out.println("Booking confirmed for " + roomType);
    }

    public void displayInventory() {

        System.out.println("\nCurrent Inventory:");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {

            System.out.println(entry.getKey() + " -> "
                    + entry.getValue());
        }
    }
}

/* ------------------ VALIDATOR SERVICE ------------------ */

class BookingValidator {

    private RoomInventory inventory;

    public BookingValidator(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void processBooking(String roomType) {

        try {

            inventory.bookRoom(roomType);

        } catch (InvalidBookingException e) {

            System.out.println("Booking Failed: " + e.getMessage());
        }
    }
}

/* ------------------ MAIN APPLICATION ------------------ */

public class HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay Application =====");
        System.out.println("Error Handling & Validation");
        System.out.println("Version: 9.1");

        RoomInventory inventory = new RoomInventory();

        BookingValidator validator = new BookingValidator(inventory);

        // Valid booking
        validator.processBooking("Single Room");

        // Invalid room type
        validator.processBooking("Luxury Room");

        // No availability example
        validator.processBooking("Suite Room");

        inventory.displayInventory();

        System.out.println("\nSystem continues running safely.");
    }
}

