import java.util.*;

/**
 * Book My Stay Application
 * Use Case 6: Reservation Confirmation & Room Allocation
 *
 * Demonstrates booking confirmation, room allocation,
 * uniqueness enforcement using Set, and inventory updates.
 *
 * @author Charukesh
 * @version 6.1
 */

/* ------------------ RESERVATION CLASS ------------------ */

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

/* ------------------ INVENTORY SERVICE ------------------ */

class RoomInventory {

    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();

        inventory.put("Single Room", 2);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void decreaseRoom(String roomType) {
        int count = inventory.get(roomType);
        inventory.put(roomType, count - 1);
    }

    public void displayInventory() {

        System.out.println("\nCurrent Inventory:");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}

/* ------------------ BOOKING SERVICE ------------------ */

class BookingService {

    private Queue<Reservation> bookingQueue;
    private RoomInventory inventory;

    private HashMap<String, Set<String>> allocatedRooms;

    public BookingService(RoomInventory inventory) {

        this.inventory = inventory;
        bookingQueue = new LinkedList<>();
        allocatedRooms = new HashMap<>();
    }

    // Add request to queue
    public void addBookingRequest(Reservation reservation) {
        bookingQueue.add(reservation);
    }

    // Process queue and allocate rooms
    public void processBookings() {

        while (!bookingQueue.isEmpty()) {

            Reservation request = bookingQueue.poll();

            String roomType = request.getRoomType();

            int available = inventory.getAvailability(roomType);

            if (available > 0) {

                String roomId = generateRoomId(roomType);

                allocatedRooms.putIfAbsent(roomType, new HashSet<>());

                allocatedRooms.get(roomType).add(roomId);

                inventory.decreaseRoom(roomType);

                System.out.println("Reservation Confirmed");
                System.out.println("Guest: " + request.getGuestName());
                System.out.println("Room Type: " + roomType);
                System.out.println("Assigned Room ID: " + roomId);
                System.out.println("-----------------------");

            } else {

                System.out.println("Reservation Failed for "
                        + request.getGuestName()
                        + " (No rooms available)");
            }
        }
    }

    // Generate unique room ID
    private String generateRoomId(String roomType) {

        String prefix = roomType.replace(" ", "").substring(0, 3).toUpperCase();

        int id = new Random().nextInt(900) + 100;

        return prefix + "-" + id;
    }

    public void displayAllocatedRooms() {

        System.out.println("\nAllocated Rooms:");

        for (Map.Entry<String, Set<String>> entry : allocatedRooms.entrySet()) {

            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}

public class HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay Application =====");
        System.out.println("Room Allocation Service");
        System.out.println("Version: 6.1");

        RoomInventory inventory = new RoomInventory();

        BookingService bookingService = new BookingService(inventory);

        // Booking requests
        bookingService.addBookingRequest(new Reservation("Arun", "Single Room"));
        bookingService.addBookingRequest(new Reservation("Bala", "Double Room"));
        bookingService.addBookingRequest(new Reservation("Chitra", "Single Room"));
        bookingService.addBookingRequest(new Reservation("Divya", "Suite Room"));

        // Process requests
        bookingService.processBookings();

        bookingService.displayAllocatedRooms();

        inventory.displayInventory();
    }
}

