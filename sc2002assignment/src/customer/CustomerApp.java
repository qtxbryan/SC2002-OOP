package customer;

import java.util.Scanner;

public class CustomerApp {

	private Scanner sc = new Scanner(System.in);
	
	private CustomerApp() {}
	
	public void displayCustomerMenu() {
		
		int choice; 
		
		do {
			
			System.out.println(	"============== MOBLIMA CUSTOMER APP ================\n" +
                    " 1. View movie listings					        \n" +
                    " 2. View top 5                                    \n" +
                    " 3. Check booking history                         \n" +
                    " 0. Back to MOBLIMA APP                           \n"+
				    "====================================================");

			System.out.println("Enter choice: ");
			
		}while (!sc.hasNextInt() {
			
			System.out.println("Invalid input type. Please enter an integer value.");
    		sc.next(); // Remove newline character
		}
		
            	
		choice = sc.nextInt(); 
		
		
		
	}
	
	
}
