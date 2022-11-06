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



    //called when booking is confirmed
    public int makeBooking(){
        
        for (int i = 0; i < getSelectedSeats().size(); i++){

        }
    }
    private listBookings(int customerID){

    }

    private void displaySeats(){

    }
}
