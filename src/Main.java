import java.util.*;

abstract class Room {

    private final String roomType;
    private final int beds;
    private final double price;

    public Room(String roomType, int beds, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.price = price;
    }

    public String getRoomType() {
        return roomType;
    }

    public int getBeds() {
        return beds;
    }

    public double getPrice() {
        return price;
    }

    public abstract void displayRoom();
}


class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 1500);
    }

    public void displayRoom() {
        System.out.println("Single Room | Beds:1 | Price: ₹1500");
    }
}


class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 2500);
    }

    public void displayRoom() {
        System.out.println("Double Room | Beds:2 | Price: ₹2500");
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 5000);
    }

    public void displayRoom() {
        System.out.println("Suite Room | Beds:3 | Price: ₹5000");
    }
}

class RoomInventory {

    private final Map<String,Integer> inventory = new HashMap<>();

    public void addRoom(String type,int count){
        inventory.put(type,count);
    }

    public int getAvailability(String type){
        return inventory.getOrDefault(type,0);
    }

    public void update(String type,int value){
        inventory.put(type,value);
    }

    public void display(){
        System.out.println("\nRoom Inventory");
        for(String key: inventory.keySet()){
            System.out.println(key+" -> "+inventory.get(key));
        }
    }
}


class SearchService {

    private final RoomInventory inventory;

    public SearchService(RoomInventory inventory){
        this.inventory=inventory;
    }

    public void search(Room[] rooms){
        System.out.println("\nAvailable Rooms");

        for(Room r:rooms){

            int count=inventory.getAvailability(r.getRoomType());

            if(count>0){
                r.displayRoom();
                System.out.println("Available: "+count+"\n");
            }
        }
    }
}


class Reservation {

    private final String guest;
    private final String roomType;

    public Reservation(String guest,String roomType){
        this.guest=guest;
        this.roomType=roomType;
    }

    public String getGuest(){
        return guest;
    }

    public String getRoomType(){
        return roomType;
    }
}


class BookingQueue{

    Queue<Reservation> queue=new LinkedList<>();

    public void add(Reservation r){
        queue.add(r);
        System.out.println("Request Added: "+r.getGuest());
    }

    public Queue<Reservation> getQueue(){
        return queue;
    }
}


class BookingService{

    private final RoomInventory inventory;

    Map<String,String> confirmedReservations=new HashMap<>();

    public BookingService(RoomInventory inventory){
        this.inventory=inventory;
    }

    public void process(BookingQueue bookingQueue){

        Queue<Reservation> q=bookingQueue.getQueue();

        while(!q.isEmpty()){

            Reservation r=q.poll();

            int available=inventory.getAvailability(r.getRoomType());

            if(available>0){

                String reservationId="RES-"+UUID.randomUUID().toString().substring(0,5);

                confirmedReservations.put(reservationId,r.getGuest());

                inventory.update(r.getRoomType(),available-1);

                System.out.println("CONFIRMED: "+r.getGuest()+" | "+reservationId);
            }

            else{

                System.out.println("FAILED: "+r.getGuest()+" | No Rooms");
            }
        }
    }

    public Set<String> getReservationIds(){
        return confirmedReservations.keySet();
    }
}


class Service{

    String name;
    double price;

    public Service(String name,double price){
        this.name=name;
        this.price=price;
    }
}


class AddOnManager{

    Map<String,List<Service>> map=new HashMap<>();

    public void addService(String reservationId,Service s){

        map.putIfAbsent(reservationId,new ArrayList<>());

        map.get(reservationId).add(s);

        System.out.println("Added "+s.name+" to "+reservationId);
    }

    public void showServices(String reservationId){

        System.out.println("\nServices for "+reservationId);

        double total=0;

        if(map.containsKey(reservationId)){

            for(Service s:map.get(reservationId)){

                System.out.println(s.name+" ₹"+s.price);

                total+=s.price;
            }
        }

        System.out.println("Extra Cost: ₹"+total);
    }
}


public class Main{

    public static void main(String[] args){

        System.out.println("=======================================");
        System.out.println("   Welcome to Book My Stay App!");
        System.out.println("   Hotel Booking Management System V7.1");
        System.out.println("=======================================\n");


        Room single = new SingleRoom();
        Room dbl = new DoubleRoom();
        Room suite = new SuiteRoom();

        Room[] rooms = {single, dbl, suite};


        RoomInventory inventory = new RoomInventory();

        inventory.addRoom("Single Room",2);
        inventory.addRoom("Double Room",1);
        inventory.addRoom("Suite Room",0);

        inventory.display();


        SearchService search = new SearchService(inventory);
        search.search(rooms);


        BookingQueue queue = new BookingQueue();

        queue.add(new Reservation("Alice","Single Room"));
        queue.add(new Reservation("Bob","Double Room"));
        queue.add(new Reservation("Charlie","Suite Room"));


        BookingService booking = new BookingService(inventory);
        booking.process(queue);


        AddOnManager manager = new AddOnManager();

        Service breakfast = new Service("Breakfast",500);
        Service spa = new Service("Spa",2000);

        for(String id: booking.getReservationIds()){

            manager.addService(id,breakfast);
            manager.addService(id,spa);
            manager.showServices(id);
        }

        System.out.println();
        inventory.display();

        System.out.println("\n=======================================");
        System.out.println("       Booking Process Completed");
        System.out.println("=======================================");
    }
}