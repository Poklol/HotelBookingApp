import java.io.*;
import java.util.*;



/* ------------------ RESERVATION CLASS ------------------ */

class Reservation implements Serializable {

    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {

        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public void display() {

        System.out.println("Reservation ID: " + reservationId
                + " | Guest: " + guestName
                + " | Room Type: " + roomType);
    }
}

/* ------------------ SYSTEM STATE CLASS ------------------ */

class SystemState implements Serializable {

    Map<String, Integer> inventory;
    List<Reservation> bookings;

    public SystemState() {

        inventory = new HashMap<>();
        bookings = new ArrayList<>();
    }
}

/* ------------------ PERSISTENCE SERVICE ------------------ */

class PersistenceService {

    private static final String FILE_NAME = "hotel_state.dat";

    // Save system state
    public static void save(SystemState state) {

        try (ObjectOutputStream out =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            out.writeObject(state);

            System.out.println("System state saved successfully.");

        } catch (IOException e) {

            System.out.println("Error saving system state.");
        }
    }

    // Load system state
    public static SystemState load() {

        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            System.out.println("System state restored successfully.");

            return (SystemState) in.readObject();

        } catch (Exception e) {

            System.out.println("No previous state found. Starting fresh.");

            return new SystemState();
        }
    }
}

/* ------------------ MAIN APPLICATION ------------------ */



public class HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay Application =====");
        System.out.println("Data Persistence & Recovery");
        System.out.println("Version: 12.1");

        // Load previous state
        SystemState state = PersistenceService.load();

        // If fresh system, initialize inventory
        if (state.inventory.isEmpty()) {

            state.inventory.put("Single Room", 2);
            state.inventory.put("Double Room", 1);
        }

        // Simulate new booking
        Reservation r =
                new Reservation("RES-301", "Arun", "Single Room");

        state.bookings.add(r);

        System.out.println("\nCurrent Bookings:");

        for (Reservation booking : state.bookings) {

            booking.display();
        }

        System.out.println("\nCurrent Inventory:");

        for (Map.Entry<String, Integer> entry : state.inventory.entrySet()) {

            System.out.println(entry.getKey()
                    + " -> " + entry.getValue());
        }

        // Save state before shutdown
        PersistenceService.save(state);

        System.out.println("\nSystem shutdown complete.");
    }
}

