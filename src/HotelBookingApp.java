import java.util.*;



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

/* ------------------ SHARED INVENTORY ------------------ */

class RoomInventory {

    private Map<String, Integer> inventory;

    public RoomInventory() {

        inventory = new HashMap<>();

        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
    }

    // Critical section protected by synchronization
    public synchronized boolean allocateRoom(String roomType) {

        int available = inventory.getOrDefault(roomType, 0);

        if (available > 0) {

            inventory.put(roomType, available - 1);

            return true;
        }

        return false;
    }

    public void displayInventory() {

        System.out.println("\nRemaining Inventory:");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {

            System.out.println(entry.getKey()
                    + " -> " + entry.getValue());
        }
    }
}

/* ------------------ BOOKING PROCESSOR ------------------ */

class BookingProcessor implements Runnable {

    private Queue<Reservation> queue;

    private RoomInventory inventory;

    public BookingProcessor(Queue<Reservation> queue,
                            RoomInventory inventory) {

        this.queue = queue;
        this.inventory = inventory;
    }

    @Override
    public void run() {

        while (true) {

            Reservation request;

            synchronized (queue) {

                if (queue.isEmpty())
                    return;

                request = queue.poll();
            }

            boolean success =
                    inventory.allocateRoom(request.getRoomType());

            if (success) {

                System.out.println(Thread.currentThread().getName()
                        + " confirmed booking for "
                        + request.getGuestName()
                        + " (" + request.getRoomType() + ")");
            }

            else {

                System.out.println(Thread.currentThread().getName()
                        + " failed booking for "
                        + request.getGuestName()
                        + " (No rooms available)");
            }
        }
    }
}

/* ------------------ MAIN APPLICATION ------------------ */



public class HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay Application =====");
        System.out.println("Concurrent Booking Simulation");
        System.out.println("Version: 11.1");

        RoomInventory inventory = new RoomInventory();

        Queue<Reservation> bookingQueue = new LinkedList<>();

        // Simulated booking requests
        bookingQueue.add(new Reservation("Arun", "Single Room"));
        bookingQueue.add(new Reservation("Bala", "Single Room"));
        bookingQueue.add(new Reservation("Chitra", "Single Room"));
        bookingQueue.add(new Reservation("Divya", "Double Room"));

        // Create multiple threads
        Thread t1 = new Thread(new BookingProcessor(bookingQueue, inventory), "Thread-1");
        Thread t2 = new Thread(new BookingProcessor(bookingQueue, inventory), "Thread-2");

        t1.start();
        t2.start();

        try {

            t1.join();
            t2.join();

        } catch (InterruptedException e) {

            e.printStackTrace();
        }

        inventory.displayInventory();

        System.out.println("\nConcurrent booking simulation completed safely.");
    }
}

