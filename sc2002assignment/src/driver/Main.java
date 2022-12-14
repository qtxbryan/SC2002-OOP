package driver;

import java.util.Scanner;

import MovieEntities.MovieManager;
import Staff.StaffApp;
import customer.CustomerApp;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int choice;
		Scanner sc = new Scanner(System.in);
		
		do {
			
			System.out.println("======================= MOBLIMA APP =======================\n"+
	                " 1. Customer App                                          \n"+
	                " 2. Staff App                                             \n"+
	                " 0. Quit App                                              \n"+
							   "===========================================================");
		
			System.out.println("Enter choice: ");
			
			while(!sc.hasNextInt()) {
				
				System.out.println("Invalid input type. Please enter integer value.");
				sc.next();
				
			}
			
			choice = sc.nextInt();
			
			switch(choice) {
			    
			    case 1: 
			        CustomerApp.getInstance().displayCustomerMenu();
			        break; 
			         
			    case 2: 
			        //Put Staff App TESTING
			        StaffApp.getInstance().displayLoginMenu();
			        break; 
			    case 0:
			        System.out.println("Thank you for using our application");
			        break; 
			        
			    default:
			        System.out.println("Please enter a choice between 0 - 2");
			        break; 
			        
			}
			
			
			
		}while (choice != 0);
		
		sc.close();
		
	
	}

}
