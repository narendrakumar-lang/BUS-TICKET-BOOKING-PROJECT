package src;
	import java.util.*;
	import java.time.LocalDate;
	import java.time.format.DateTimeFormatter;
	import java.time.format.DateTimeParseException;

	public class BusTicketBookingProject {
	    Scanner sc = new Scanner(System.in);
	    Random rand = new Random();

	    private int[] ticketNumbers = new int[100];
	    private String[] passengerNames = new String[100];
	    private String[] busNames = new String[100];
	    private String[] routes = new String[100];
	    private int[] seatNumbers = new int[100];
	    private String[] travelDates = new String[100];
	    private int ticketCount = 0;

	    private String[] travels = {
	        "Orange Travels","SRS Travels","Kaveri Travels","Garuda Express",
	        "Morning Star","VRL Travels","APSRTC","TSRTC",
	        "GreenLine","National Travels","SRM Travels","Parveen Travels",
	        "YBM Travels","Jabbar Travels","KPN Travels","SVR Travels",
	        "Orange Deluxe","CityLink","FastTrack","InterCity"
	    };

	    private String[] features = {
	        "AC Sleeper WiFi","Non-AC Seater","AC Sleeper","Luxury AC WiFi",
	        "AC Sleeper WiFi","AC Sleeper","Seater","AC Seater",
	        "Luxury Sleeper","AC Sleeper","AC Sleeper","Luxury Seater",
	        "AC Sleeper","Non-AC","AC Sleeper","Luxury AC",
	        "AC Sleeper","Seater","AC","Sleeper"
	    };

	    private int[] prices = {
	        500,700,600,900,750,850,300,600,950,700,
	        800,650,720,400,880,920,770,550,610,830
	    };

	    private String[] places = {
	        "Hyderabad","Chennai","Mumbai","Vijayawada",
	        "Lucknow","Kerala","Gujarat","Vizag",
	        "Goa","Kolkata","Pune"
	    };

	    private final int TOTAL_SEATS = 10;

	    private Stack<String> bookingStack = new Stack<>();
	    private Queue<String> waitingQueue = new LinkedList<>();

	    class Bus {
	        String name, feature, start, duration;
	        int price, rating;

	        Bus(String n,String f,int p,int r,String s,String d){
	            name=n; feature=f; price=p;
	            rating=r; start=s; duration=d;
	        }
	    }

	    private boolean isValidPlace(String place){
	        for(String p:places)
	            if(p.equalsIgnoreCase(place)) return true;
	        return false;
	    }

	    private String getTime(){
	        int h=rand.nextInt(12)+1;
	        int m=rand.nextBoolean()?0:30;
	        String ap=rand.nextBoolean()?"AM":"PM";
	        return String.format("%02d:%02d %s",h,m,ap);
	    }

	    private String getDuration(){
	        int hr = rand.nextInt(11) + 2;
	        return hr + "hr";
	    }

	    private void displaySeatLayout(boolean[] seatBooked){
	        System.out.println("\n===== SEAT LAYOUT =====");

	        for(int i=0;i<TOTAL_SEATS;i++){
	            if(seatBooked[i])
	                System.out.printf("[X] ");
	            else
	                System.out.printf("[%d] ", (i+1));

	            if((i+1)%4==0) System.out.println();
	        }
	        System.out.println();
	    }

	    public void searchAndBook(){

	        System.out.println("\n================ SEARCH BUSES ================");
	        System.out.println("👉 Enter 0 anytime to return to Home");

	        // ✅ DATE VALIDATION
	        String date = "";
	        while(true){
	            System.out.print("Enter Travel Date (DD-MM-YYYY): ");
	            date = sc.next();

	            if(date.equals("0")) return;

	            try{
	                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	                LocalDate enteredDate = LocalDate.parse(date, formatter);
	                LocalDate today = LocalDate.now();

	                if(enteredDate.isBefore(today)){
	                    System.out.println("❌ Past date not allowed! Enter today or future date.");
	                } else {
	                    break;
	                }

	            } catch(DateTimeParseException e){
	                System.out.println("❌ Invalid format! Use DD-MM-YYYY");
	            }
	        }

	        System.out.print("Enter Source: ");
	        String source=sc.next();
	        if(source.equals("0")) return;

	        System.out.print("Enter Destination: ");
	        String dest=sc.next();
	        if(dest.equals("0")) return;

	        if(!isValidPlace(source)||!isValidPlace(dest)){
	            System.out.println("❌ No buses available!");
	            return;
	        }

	        if(source.equalsIgnoreCase(dest)){
	            System.out.println("⚠ Same location!");
	            return;
	        }

	        String route=source+" -> "+dest;

	        List<Bus> busList=new ArrayList<>();

	        for(int i=0;i<travels.length;i++){
	            busList.add(new Bus(
	                travels[i],features[i],prices[i],
	                rand.nextInt(3)+3,getTime(),getDuration()
	            ));
	        }

	        Collections.shuffle(busList);
	        int showCount = rand.nextInt(10) + 6;
	        busList = busList.subList(0, showCount);

	        System.out.println("\n===== AVAILABLE BUSES =====");
	        display(busList);

	        System.out.println("\n1. High Price First");
	        System.out.println("2. High Rating First");
	        System.out.println("3. Low Price First (Best Value)");
	        System.out.println("4. More Features First");
	        System.out.print("Enter option (0 to go back): ");

	        int opt=sc.nextInt();
	        if(opt==0) return;

	        switch(opt){
	            case 1 -> busList.sort((a,b)->b.price-a.price);
	            case 2 -> busList.sort((a,b)->b.rating-a.rating);
	            case 3 -> busList.sort((a,b)->{
	                if(a.price==b.price) return b.rating-a.rating;
	                return a.price-b.price;
	            });
	            case 4 -> busList.sort((a,b)->{
	                int fa = a.feature.split(" ").length;
	                int fb = b.feature.split(" ").length;
	                return fb-fa;
	            });
	            default -> {
	                System.out.println("Invalid option!");
	                return;
	            }
	        }

	        System.out.println("\n===== SORTED BUSES =====");
	        display(busList);

	        System.out.print("\nSelect Bus (0 to go back): ");
	        int choice=sc.nextInt();
	        if(choice==0) return;

	        String selectedBus=busList.get(choice-1).name;

	        boolean[] seatBooked=new boolean[TOTAL_SEATS];
	        displaySeatLayout(seatBooked);

	        System.out.print("How many tickets: ");
	        int t=sc.nextInt();
	        if(t==0) return;
	        sc.nextLine();

	        List<String> tempNames = new ArrayList<>();
	        List<Integer> tempSeats = new ArrayList<>();

	        for(int i=0;i<t;i++){
	            System.out.print("Passenger Name: ");
	            String name=sc.nextLine();

	            System.out.print("Seat No: ");
	            int seat=sc.nextInt();
	            sc.nextLine();

	            if(seatBooked[seat-1]){
	                System.out.println("❌ Seat filled → Added to waiting list");
	                waitingQueue.add(name);
	                continue;
	            }

	            seatBooked[seat-1]=true;
	            tempNames.add(name);
	            tempSeats.add(seat);
	        }

	        System.out.println("\nSelect Payment Method:");
	        System.out.println("1. UPI");
	        System.out.println("2. Credit Card");
	        System.out.println("3. Net Banking");
	        System.out.print("Enter choice: ");

	        int pay = sc.nextInt();

	        if(pay>=1 && pay<=3){

	            System.out.println("\n✅ Ticket Booking Successful!");
	            System.out.println("🎟 Total Tickets Booked: " + tempNames.size());

	            for(int i=0;i<tempNames.size();i++){

	                int ticket=rand.nextInt(9000)+1000;

	                ticketNumbers[ticketCount]=ticket;
	                passengerNames[ticketCount]=tempNames.get(i);
	                busNames[ticketCount]=selectedBus;
	                routes[ticketCount]=route;
	                seatNumbers[ticketCount]=tempSeats.get(i);
	                travelDates[ticketCount]=date;
	                ticketCount++;

	                bookingStack.push(ticket+" | "+tempNames.get(i));

	                System.out.println("\n------ TICKET ------");
	                System.out.println("Ticket No : " + ticket);
	                System.out.println("Name      : " + tempNames.get(i));
	                System.out.println("Bus       : " + selectedBus);
	                System.out.println("Route     : " + route);
	                System.out.println("Seat No   : " + tempSeats.get(i));
	                System.out.println("Date      : " + date);
	                System.out.println("--------------------");
	            }

	        } else {
	            System.out.println("❌ Invalid Payment Option!");
	        }
	    }

	    public void viewTickets(){
	        for(int i=0;i<ticketCount;i++){
	            System.out.println(ticketNumbers[i]+" | "+passengerNames[i]+" | "+travelDates[i]);
	        }
	    }

	    public void searchByTicketNo(){
	        System.out.print("Enter Ticket No: ");
	        int t=sc.nextInt();
	        for(int i=0;i<ticketCount;i++){
	            if(ticketNumbers[i]==t){
	                System.out.println("Found: "+passengerNames[i]+" | Date: "+travelDates[i]);
	                return;
	            }
	        }
	        System.out.println("Not found!");
	    }

	    public void popLastBooking(){
	        if(bookingStack.isEmpty()){
	            System.out.println("No bookings!");
	            return;
	        }
	        System.out.println("Cancelled: "+bookingStack.pop());
	    }

	    public void displayBookingStack(){
	        System.out.println("Stack: "+bookingStack);
	    }

	    public void displayWaitingQueue(){
	        System.out.println("Queue: "+waitingQueue);
	    }

	    public void serveNextInQueue(){
	        if(waitingQueue.isEmpty()){
	            System.out.println("Empty!");
	            return;
	        }
	        System.out.println("Serving: "+waitingQueue.poll());
	    }

	    private void display(List<Bus> list){
	        System.out.printf("%-3s %-20s %-18s %-8s %-10s %-6s %-6s%n",
	                "No","Bus Name","Feature","Price","Start","Time","Rate");

	        for(int i=0;i<list.size();i++){
	            Bus b=list.get(i);

	            System.out.printf("%-3d %-20s %-18s ₹%-7d %-10s %-6s %-6s%n",
	                    (i+1), b.name, b.feature, b.price,
	                    b.start, b.duration, "⭐".repeat(b.rating));
	        }
	    }
	}

	
	
