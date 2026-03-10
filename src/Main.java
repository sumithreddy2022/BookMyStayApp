import java.util.LinkedList;
import java.util.Queue;

class Reservation {
        String guestName;
        String roomType;

        Reservation(String guestName, String roomType) {
                this.guestName = guestName;
                this.roomType = roomType;
        }

        void display() {
                System.out.println("Guest: " + guestName + " | Room Type: " + roomType);
        }
}

public class Main {

        public static void main(String[] args) {

                Queue<Reservation> bookingQueue = new LinkedList<>();

                bookingQueue.add(new Reservation("Alice", "Single Room"));
                bookingQueue.add(new Reservation("Bob", "Double Room"));
                bookingQueue.add(new Reservation("Charlie", "Suite Room"));

                System.out.println("Book My Stay - Hotel Booking System");
                System.out.println("Version 5.1");
                System.out.println();
                System.out.println("Booking Requests (First-Come-First-Served):");
                System.out.println();

                for (Reservation r : bookingQueue) {
                        r.display();
                }
        }
}