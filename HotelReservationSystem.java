import java.util.ArrayList;
import java.util.Scanner;

class Room {
    private int roomNumber;
    private String roomType;
    private double pricePerNight;
    private boolean isAvailable;

    public Room(int roomNumber, String roomType, double pricePerNight) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
        this.isAvailable = true; 
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }

    public void displayRoomDetails() {
        System.out.println("Room No: " + roomNumber + " | Type: " + roomType + " | Price/Night: ₹" + pricePerNight + " | Status: " + (isAvailable ? "Available" : "Booked"));
    }
}

class Customer {
    private String name;
    private String phone;
    private String email;

    public Customer(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }
}

class Payment {
    private String paymentMethod;
    private double amountPaid;
    private String paymentStatus;

    public Payment(String paymentMethod, double amountPaid) {
        this.paymentMethod = paymentMethod;
        this.amountPaid = amountPaid;
        this.paymentStatus = "SUCCESSFUL";
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }
}

class Reservation {
    private static int idCounter = 101; 
    private int reservationId;
    private Customer customer;
    private Room room;
    private String checkInDate;
    private String checkOutDate;
    private Payment payment;

    public Reservation(Customer customer, Room room, String checkInDate, String checkOutDate, Payment payment) {
        this.reservationId = idCounter++;
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.payment = payment;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void printConfirmationDetails() {
        System.out.println(" BOOKING CONFIRMATION RECEIPT");
        System.out.println("Reservation ID  : " + reservationId);
        System.out.println("Customer Name   : " + customer.getName());
        System.out.println("Contact Phone   : " + customer.getPhone());
        System.out.println("Contact Email   : " + customer.getEmail());
        System.out.println("Room Allocated  : Room " + room.getRoomNumber() + " (" + room.getRoomType() + ")");
        System.out.println("Check-in Date   : " + checkInDate);
        System.out.println("Check-out Date  : " + checkOutDate);
        System.out.println("Amount Paid     : ₹" + payment.getAmountPaid());
        System.out.println("Payment Method  : " + payment.getPaymentMethod());
        System.out.println("Payment Status  : " + payment.getPaymentStatus());
    }
}

public class HotelReservationSystem {

    private ArrayList<Room> rooms = new ArrayList<>();
    private ArrayList<Reservation> reservations = new ArrayList<>();

    public HotelReservationSystem() {
        rooms.add(new Room(101, "Standard", 1500.0));
        rooms.add(new Room(102, "Standard", 1500.0));
        rooms.add(new Room(201, "Deluxe", 3000.0));
        rooms.add(new Room(202, "Deluxe", 3000.0));
        rooms.add(new Room(301, "Suite", 5000.0));
    }

    public void searchRooms(Scanner scanner) {
        System.out.println("\nSearch Available Rooms");
        System.out.print("Enter Check-in Date (DD/MM/YYYY): ");
        String checkIn = scanner.nextLine();
        System.out.print("Enter Check-out Date (DD/MM/YYYY): ");
        String checkOut = scanner.nextLine();
        System.out.print("Enter Room Type (Standard / Deluxe / Suite): ");
        String targetType = scanner.nextLine();

        System.out.println("\nAvailable Rooms for " + targetType + " between " + checkIn + " and " + checkOut + ":");
        boolean found = false;

        for (Room room : rooms) {
            if (room.isAvailable() && room.getRoomType().equalsIgnoreCase(targetType)) {
                room.displayRoomDetails();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No rooms available matching your criteria.");
        }
    }

    public void reserveRoom(Scanner scanner) {
        System.out.println("\n Make a Reservation");
        System.out.print("Enter desired Room Number: ");
        int roomNum = scanner.nextInt();
        scanner.nextLine(); 

        Room selectedRoom = null;
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNum) {
                selectedRoom = room;
                break;
            }
        }

        if (selectedRoom == null) {
            System.out.println("Invalid Room Number!");
            return;
        }

        if (!selectedRoom.isAvailable()) {
            System.out.println("Sorry, Room " + roomNum + " is already booked!");
            return;
        }

        System.out.print("Enter Customer Full Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Phone Number: ");
        String phone = scanner.nextLine();
        System.out.print("Enter Email Address: ");
        String email = scanner.nextLine();

        Customer customer = new Customer(name, phone, email);

        System.out.print("Enter Check-in Date (DD/MM/YYYY): ");
        String checkIn = scanner.nextLine();
        System.out.print("Enter Check-out Date (DD/MM/YYYY): ");
        String checkOut = scanner.nextLine();

        double price = selectedRoom.getPricePerNight();
        System.out.println("Total Amount Due: ₹" + price);
        System.out.print("Select Payment Method (1. UPI / 2. Credit Card / 3. Cash): ");
        int payChoice = scanner.nextInt();
        scanner.nextLine();

        String paymentMethod = switch (payChoice) {
            case 1 -> "UPI";
            case 2 -> "Credit Card";
            case 3 -> "Cash";
            default -> "Online Transfer";
        };

        Payment payment = new Payment(paymentMethod, price);

        selectedRoom.setAvailable(false);

        Reservation reservation = new Reservation(customer, selectedRoom, checkIn, checkOut, payment);
        reservations.add(reservation);

        reservation.printConfirmationDetails();
    }

    public void displayAllRooms() {
        System.out.println("\n Real-Time Room Availability Status ");
        for (Room room : rooms) {
            room.displayRoomDetails();
        }
    }

    public void displayAllReservations() {
        System.out.println("\nAll Reservations");
        if (reservations.isEmpty()) {
            System.out.println("No reservations made yet.");
            return;
        }
        for (Reservation res : reservations) {
            res.printConfirmationDetails();
        }
    }

    public static void main(String[] args) {
        HotelReservationSystem system = new HotelReservationSystem();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        System.out.println("   WELCOME TO THE HOTEL RESERVATION SYSTEM    ");

        while (!exit) {
            System.out.println("\nMAIN MENU:");
            System.out.println("1. Search Available Rooms");
            System.out.println("2. Make a Room Reservation");
            System.out.println("3. View Real-Time Room Status");
            System.out.println("4. View All Reservation Receipts");
            System.out.println("5. Exit");
            System.out.print("Enter your choice (1-5): ");

            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    system.searchRooms(scanner);
                    break;
                case 2:
                    system.reserveRoom(scanner);
                    break;
                case 3:
                    system.displayAllRooms();
                    break;
                case 4:
                    system.displayAllReservations();
                    break;
                case 5:
                    System.out.println("Thank you for using the Hotel Reservation System. Goodbye!");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please choose between 1 and 5.");
            }
        }

        scanner.close();
    }
}