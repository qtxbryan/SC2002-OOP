package booking; //change to ManagerEntities

import java.util.Scanner;
import CineplexEntities.Cinema;


public class BookingManager {
    
    private Scanner sc = new Scanner(System.in);


    //called when tyring to make booking
    // AFTER CHOSEN MOVIE AND SHOWTIME
    public void printBookingPortal(int movieID){
        boolean exit = false;
        //show booking menu until exit chosen
        while(!exit){

            //If no more seat is available
            if (cinema.getCinemaStatus() == CinemaStatus.SOLD_OUT){
                System.out.println("Sorry. This showtime is sold out. Please select another showtime.");
                exit = true;
                return;
            }

            //Show seating plan
            displaySeats()

            System.out.println( "=========== SEAT BOOKING ===========\n" +
                                "1. Select a seat\n" +
                                "2. Deselect a seat\n" +
                                "3. Confirm and proceed to ticket selection\n"+
                                "0. Exit\n"+
                                "====================================");
            System.out.println("Please select a choice");

            while (!sc.hasNextInt()){
                System.out.println("Invalid input type. Please enter an integer value.");
                sc.next();
            }

            switch(sc.nextInt()){
                case 0:
                    exit = true;
                    break;
                case 1:
                    addSeatSelection();
                    break;
                case 2:
                    deleteSeatSelection();
                    break;
                case 3:
                    if (getSelectedSeats().size() <= 0){
                        System.out.println("No seat selected. Please select a seat before choosing tickets.");
                    }
                    else{
                        //Book tickets
                        makeBooking();
                    }
                    break;
                default:
                    System.out.println("Invalid choice entered. Please try again.");
            }   
        }
    }


    //called when booking is confirmed, create record in data
    public int makeBooking(){
        
        for (int i = 0; i < getSelectedSeats().size(); i++){

        }
    }

    private listBookings(int customerID){

    }

    private void displaySeats(List<String> list){
        char item;
        String rowRef;
        String[] symbols = {"0", "1", "S"}; //Symbols for seat status display

        //Iterate through List of Strings
        for (int row = 0; row < list.size(); row++){
            rowRef = list.get(row);

            //check if row is NULL
            if (rowRef == null || rowRef.lenght() == 0){
                System.out.println();
                continue;
            }

            //Check if this row has seats. If not, print whole row
            if () {
                System.out.println(rowRef);
            }

            else { // Seats present
    			for (int col = 0; col < rowRef.length(); col++) {
        			item = rowRef.charAt(col);

        			// Check if current item is a seat. Prints corresponding symbol according to seat status.
        			if (Character.isDigit(item)) {
        				System.out.printf("%s", symbols[Character.getNumericValue(item)]);
        			}
        			// Else just print the character
        			else {
        				System.out.printf(Character.toString(item));
        			}
        		}
    			System.out.println(); // Newline
    		}
        }
    }



}
