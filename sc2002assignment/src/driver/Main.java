package driver;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//TEST TESTING INGGN 
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
			
			
		}while (choice != 0);
		
		sc.close();
		
	
	}

}
