import java.util.*;

/**
 * Book My Stay Application
 * Use Case 10: Booking Cancellation & Inventory Rollback
 *
 * Demonstrates safe cancellation of reservations using
 * stack-based rollback and inventory restoration.
 *
 * @author Charukesh
 * @version 10.1
 */

/* ------------------ RESERVATION CLASS ------------------ */

class Reservation {

    private String reservationId;
    private String guestName;
    private String roomType;
    private String roomId;

    public Reservation(String reservationId, String guestName,
                       String roomType, String roomId) {

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
        System.out.println("Reservation: " + reservationId
                + " | Guest: " + guestName
                + " | Room Type: " + roomType
                + " | Room ID: " + roomId);
    }
}

/* ------------------ INVENTORY SERVICE ------------------ */

class RoomInventory {

    private Map<String, Integer> inventory;

    public RoomInventory() {

        inventory = new HashMap<>();

        inventory.put("Single Room", 1);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 0);
    }

    public void increaseRoom(String roomType) {

        int count = inventory.getOrDefault(roomType, 0);

        inventory.put(roomType, count + 1);
    }

    public void displayInventory() {

        System.out.println("\nCurrent Inventory:");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {

            System.out.println(entry.getKey()
                    + " -> " + entry.getValue());
        }
    }
}

/* ------------------ CANCELLATION SERVICE ------------------ */

class CancellationService {

    private Map<String, Reservation> reservations;

    private Stack<String> rollbackStack;

    private RoomInventory inventory;

    public CancellationService(RoomInventory inventory) {

        this.inventory = inventory;

        reservations = new HashMap<>();

        rollbackStack = new Stack<>();
    }

    public void addReservation(Reservation r) {

        reservations.put(r.getReservationId(), r);
    }

    public void cancelReservation(String reservationId) {

        if (!reservations.containsKey(reservationId)) {

            System.out.println("Cancellation Failed: Reservation not found.");

            return;
        }

        Reservation r = reservations.remove(reservationId);

        rollbackStack.push(r.getRoomId());

        inventory.increaseRoom(r.getRoomType());

        System.out.println("Reservation Cancelled Successfully.");
        System.out.println("Released Room ID: " + r.getRoomId());
    }

    public void displayRollbackStack() {

        System.out.println("\nRollback Stack (recent releases):");

        System.out.println(rollbackStack);
    }
}

/* ------------------ MAIN APPLICATION ------------------ */

public class HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay Application =====");
        System.out.println("Booking Cancellation & Rollback");
        System.out.println("Version: 10.1");

        RoomInventory inventory = new RoomInventory();

        CancellationService cancellationService =
                new CancellationService(inventory);

        // Existing reservations
        Reservation r1 =
                new Reservation("RES-201", "Arun",
                        "Single Room", "SR-101");

        Reservation r2 =
                new Reservation("RES-202", "Bala",
                        "Double Room", "DR-201");

        cancellationService.addReservation(r1);
        cancellationService.addReservation(r2);

        // Cancellation request
        cancellationService.cancelReservation("RES-201");

        // Invalid cancellation example
        cancellationService.cancelReservation("RES-999");

        cancellationService.displayRollbackStack();

        inventory.displayInventory();

        System.out.println("\nSystem state restored successfully.");
    }
}

