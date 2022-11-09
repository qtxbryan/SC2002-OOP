package customer;

import java.util.Scanner;

import MovieEntities.MovieManager;

public class CustomerApp {

	private Scanner sc = new Scanner(System.in);
	
	private CustomerApp() {}
	
	private static CustomerApp single_instance = null; 
	
	public static CustomerApp getInstance() {
	    
	    if(single_instance == null) {
	        
	        single_instance = new CustomerApp();
	        
	    }
	    return single_instance;
	    
	}
	
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
			
		}while (!sc.hasNextInt()){
			
			System.out.println("Invalid input type. Please enter an integer value.");
    		sc.next(); // remove newline character
		}
		
            	
		choice = sc.nextInt(); 
		
		
		
	}
	
	
}