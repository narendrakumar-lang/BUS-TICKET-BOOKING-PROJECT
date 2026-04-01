package src;
import java.util.Scanner;
public class BusTicketBookingProjectDemo {
		
		    public static void main(String[] args) {

		        Scanner sc = new Scanner(System.in);
		        BusTicketBookingProject service = new BusTicketBookingProject();

		        while (true) {

		            System.out.println("\n========= A-Z TRAVELS =========");
		            System.out.println("1. Search & Book Ticket");
		            System.out.println("2. View All Tickets");
		            System.out.println("3. Search by Ticket No");
		            System.out.println("4. Cancel Last Booking");
		            System.out.println("5. View Booking Stack");
		            System.out.println("6. View Waiting Queue");
		            System.out.println("7. Serve Waiting Queue");
		            System.out.println("8. Exit");

		            System.out.print("👉 Enter your choice: ");

		            if (!sc.hasNextInt()) {
		                System.out.println("❌ Invalid input!");
		                sc.next();
		                continue;
		            }

		            int choice = sc.nextInt();

		            switch (choice) {
		                case 1:
		                    service.searchAndBook();
		                    break;
		                case 2:
		                    service.viewTickets();
		                    break;
		                case 3:
		                    service.searchByTicketNo();
		                    break;
		                case 4:
		                    service.popLastBooking();
		                    break;
		                case 5:
		                    service.displayBookingStack();
		                    break;
		                case 6:
		                    service.displayWaitingQueue();
		                    break;
		                case 7:
		                    service.serveNextInQueue();
		                    break;
		                case 8:
		                    System.out.println("🙏 Thank you!");
		                    sc.close();
		                    return;
		                default:
		                    System.out.println("❌ Invalid choice!");
		            }
		        }
		    }
		
	}


