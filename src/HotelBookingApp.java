import java.util.HashMap;
import java.util.Map;

/**
 * Book My Stay Application
 * Use Case 4: Room Search & Availability Check
 *
 * Demonstrates read-only room search using centralized inventory.
 * Only rooms with availability greater than zero are displayed.
 *
 * @author Charukesh
 * @version 4.1
 */

/* ------------------ ROOM DOMAIN MODEL ------------------ */
abstract class Room {

    protected String type;
    protected int beds;
    protected int size;
    protected double price;

    public Room(String type, int beds, int size, double price) {
        this.type = type;
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public void displayDetails() {
        System.out.println("Room Type: " + type);
        System.out.println("Beds: " + beds);
        System.out.println("Room Size: " + size + " sq ft");
        System.out.println("Price per night: ₹" + price);
    }
}

/* ------------------ ROOM TYPES ------------------ */

class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 180, 2000);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 250, 3500);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 400, 6000);
    }
}

/* ------------------ INVENTORY CLASS ------------------ */

class RoomInventory {

    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();

        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 0); // unavailable example
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public Map<String, Integer> getAllInventory() {
        return inventory;
    }
}

/* ------------------ SEARCH SERVICE ------------------ */

class RoomSearchService {

    private RoomInventory inventory;

    public RoomSearchService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void searchAvailableRooms() {

        System.out.println("\nAvailable Rooms:\n");

        Room[] rooms = {
                new SingleRoom(),
                new DoubleRoom(),
                new SuiteRoom()
        };

        for (Room room : rooms) {

            int available = inventory.getAvailability(room.type);

            // Defensive check
            if (available > 0) {
                room.displayDetails();
                System.out.println("Available Rooms: " + available);
                System.out.println("----------------------------");
            }
        }
    }
}

/* ------------------ MAIN APPLICATION ------------------ */


public class HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay Application =====");
        System.out.println("Room Search System");
        System.out.println("Version: 4.1");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Initialize search service
        RoomSearchService searchService = new RoomSearchService(inventory);

        // Perform search
        searchService.searchAvailableRooms();

        System.out.println("\nSearch completed. Inventory state unchanged.");
    }
}

