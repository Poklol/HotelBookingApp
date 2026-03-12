import java.util.*;



/* ------------------ SERVICE CLASS ------------------ */

class Service {

    private String serviceName;
    private double price;

    public Service(String serviceName, double price) {
        this.serviceName = serviceName;
        this.price = price;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getPrice() {
        return price;
    }
}

/* ------------------ ADD-ON SERVICE MANAGER ------------------ */

class AddOnServiceManager {

    private Map<String, List<Service>> reservationServices;

    public AddOnServiceManager() {
        reservationServices = new HashMap<>();
    }

    // Add service to reservation
    public void addService(String reservationId, Service service) {

        reservationServices.putIfAbsent(reservationId, new ArrayList<>());

        reservationServices.get(reservationId).add(service);

        System.out.println(service.getServiceName()
                + " added to Reservation " + reservationId);
    }

    // Display services for a reservation
    public void displayServices(String reservationId) {

        List<Service> services = reservationServices.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No services selected.");
            return;
        }

        System.out.println("\nServices for Reservation " + reservationId + ":");

        double totalCost = 0;

        for (Service s : services) {
            System.out.println("- " + s.getServiceName()
                    + " : ₹" + s.getPrice());
            totalCost += s.getPrice();
        }

        System.out.println("Total Add-On Cost: ₹" + totalCost);
    }
}

/* ------------------ MAIN APPLICATION ------------------ */

public class HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay Application =====");
        System.out.println("Add-On Service Selection");
        System.out.println("Version: 7.1");

        AddOnServiceManager serviceManager = new AddOnServiceManager();

        // Example reservation ID
        String reservationId = "RES-101";

        // Available services
        Service breakfast = new Service("Breakfast", 500);
        Service spa = new Service("Spa Access", 1200);
        Service airportPickup = new Service("Airport Pickup", 800);

        // Guest selects services
        serviceManager.addService(reservationId, breakfast);
        serviceManager.addService(reservationId, spa);
        serviceManager.addService(reservationId, airportPickup);

        // Display selected services
        serviceManager.displayServices(reservationId);

        System.out.println("\nBooking and inventory remain unchanged.");
    }
}

