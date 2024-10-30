import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Room {
    private final int roomNumber;
    private final String category;
    private final double rate;
    private boolean isAvailable;

    public Room(int roomNumber, String category, double rate) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.rate = rate;
        this.isAvailable = true;
    }

    public int getRoomNumber() { return roomNumber; }
    public String getCategory() { return category; }
    public double getRate() { return rate; }
    public boolean isAvailable() { return isAvailable; }
    public void setAvailability(boolean available) { isAvailable = available; }

    @Override
    public String toString() {
        return "Room #" + roomNumber + " | Category: " + category + " | Rate: $" + rate + " per night | Available: " + isAvailable;
    }
}

class Reservation {
    private final Room room;
    private final String customerName;
    private final int nights;
    private final double totalCost;

    public Reservation(Room room, String customerName, int nights) {
        this.room = room;
        this.customerName = customerName;
        this.nights = nights;
        this.totalCost = nights * room.getRate();
    }

    public Room getRoom() { return room; }
    public String getCustomerName() { return customerName; }
    public int getNights() { return nights; }
    public double getTotalCost() { return totalCost; }

    @Override
    public String toString() {
        return "Reservation Details:\n" +
                "Customer: " + customerName +
                "\nRoom #" + room.getRoomNumber() +
                "\nCategory: " + room.getCategory() +
                "\nNights: " + nights +
                "\nTotal Cost: $" + totalCost;
    }
}

public class Main {
    private final List<Room> rooms;
    private final List<Reservation> reservations;

    public Main() {
        rooms = new ArrayList<>();
        reservations = new ArrayList<>();
        initializeRooms();
    }

    private void initializeRooms() {
        // Sample rooms with different categories and rates
        rooms.add(new Room(101, "Standard", 100));
        rooms.add(new Room(102, "Standard", 100));
        rooms.add(new Room(201, "Deluxe", 150));
        rooms.add(new Room(202, "Deluxe", 150));
        rooms.add(new Room(301, "Suite", 200));
    }

    public void showAvailableRooms() {
        System.out.println("\nAvailable Rooms:");
        for (Room room : rooms) {
            if (room.isAvailable()) {
                System.out.println(room);
            }
        }
    }

    public Room findRoomByNumber(int roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                return room;
            }
        }
        return null;
    }

    public void makeReservation(Scanner scanner) {
        showAvailableRooms();
        System.out.print("\nEnter room number to reserve: ");
        int roomNumber = scanner.nextInt();
        Room room = findRoomByNumber(roomNumber);

        if (room == null || !room.isAvailable()) {
            System.out.println("Room not available.");
            return;
        }

        System.out.print("Enter your name: ");
        String customerName = scanner.next();
        System.out.print("Enter number of nights: ");
        int nights = scanner.nextInt();

        Reservation reservation = new Reservation(room, customerName, nights);
        reservations.add(reservation);
        room.setAvailability(false);

        System.out.println("Reservation successful!");
        System.out.println(reservation);
    }

    public void viewReservations() {
        if (reservations.isEmpty()) {
            System.out.println("No reservations found.");
            return;
        }

        System.out.println("\nAll Reservations:");
        for (Reservation reservation : reservations) {
            System.out.println(reservation);
            System.out.println("--------------------------------");
        }
    }

    public void processPayment(Scanner scanner, Reservation reservation) {
        System.out.print("Enter payment amount to confirm booking ($" + reservation.getTotalCost() + "): ");
        double payment = scanner.nextDouble();

        if (payment >= reservation.getTotalCost()) {
            System.out.println("Payment successful. Booking confirmed!");
        } else {
            System.out.println("Insufficient payment. Please try again.");
        }
    }

    public static void main(String[] args) {
        Main hotelSystem = new Main();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nWelcome to the Hotel Reservation System");
            System.out.println("1. Search for available rooms");
            System.out.println("2. Make a reservation");
            System.out.println("3. View all reservations");
            System.out.println("4. Exit");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    hotelSystem.showAvailableRooms();
                    break;
                case 2:
                    hotelSystem.makeReservation(scanner);
                    System.out.print("Do you want to process payment? (yes/no): ");
                    String paymentResponse = scanner.next();
                    if (paymentResponse.equalsIgnoreCase("yes")) {
                        Reservation lastReservation = hotelSystem.reservations.get(hotelSystem.reservations.size() - 1);
                        hotelSystem.processPayment(scanner, lastReservation);
                    }
                    break;
                case 3:
                    hotelSystem.viewReservations();
                    break;
                case 4:
                    System.out.println("Thank you for using the Hotel Reservation System.");
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}