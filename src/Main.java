import java.util.*;

class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

class BookingSystem {
    private Map<String, Integer> rooms = new HashMap<>();

    public BookingSystem() {
        rooms.put("Single", 2);
        rooms.put("Double", 2);
        rooms.put("Suite", 1);
    }

    public void validateRoomType(String roomType) throws InvalidBookingException {
        if (!rooms.containsKey(roomType)) {
            throw new InvalidBookingException("Booking failed: Invalid room type selected.");
        }
    }

    public void bookRoom(String guestName, String roomType) throws InvalidBookingException {
        validateRoomType(roomType);

        int available = rooms.get(roomType);

        if (available <= 0) {
            throw new InvalidBookingException("Booking failed: No rooms available.");
        }

        rooms.put(roomType, available - 1);
        System.out.println("Booking successful for " + guestName + " in " + roomType + " room.");
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BookingSystem system = new BookingSystem();

        System.out.print("Enter guest name: ");
        String guestName = sc.nextLine();

        System.out.print("Enter room type (Single/Double/Suite): ");
        String roomType = sc.nextLine();

        try {
            system.bookRoom(guestName, roomType);
        } catch (InvalidBookingException e) {
            System.out.println(e.getMessage());
        }

        sc.close();
    }
}