import java.util.*;



/* ------------------ RESERVATION CLASS ------------------ */

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

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void displayReservation() {
        System.out.println("Reservation ID: " + reservationId
                + " | Guest: " + guestName
                + " | Room Type: " + roomType);
    }
}

/* ------------------ BOOKING HISTORY ------------------ */

class BookingHistory {

    private List<Reservation> bookingList;

    public BookingHistory() {
        bookingList = new ArrayList<>();
    }

    // Store confirmed reservation
    public void addReservation(Reservation reservation) {
        bookingList.add(reservation);
    }

    // Retrieve booking history
    public List<Reservation> getAllReservations() {
        return bookingList;
    }
}

/* ------------------ REPORT SERVICE ------------------ */

class BookingReportService {

    private BookingHistory history;

    public BookingReportService(BookingHistory history) {
        this.history = history;
    }

    public void generateReport() {

        List<Reservation> reservations = history.getAllReservations();

        System.out.println("\n===== Booking History Report =====");

        if (reservations.isEmpty()) {
            System.out.println("No bookings available.");
            return;
        }

        for (Reservation r : reservations) {
            r.displayReservation();
        }

        System.out.println("\nTotal Bookings: " + reservations.size());
    }
}

/* ------------------ MAIN APPLICATION ------------------ */

public class HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay Application =====");
        System.out.println("Booking History & Reporting System");
        System.out.println("Version: 8.1");

        // Initialize history
        BookingHistory history = new BookingHistory();

        // Simulate confirmed bookings
        history.addReservation(new Reservation("RES-101", "Arun", "Single Room"));
        history.addReservation(new Reservation("RES-102", "Bala", "Double Room"));
        history.addReservation(new Reservation("RES-103", "Chitra", "Suite Room"));

        // Generate report
        BookingReportService reportService = new BookingReportService(history);

        reportService.generateReport();

        System.out.println("\nReport generated without modifying history.");
    }
}

