package booking; //change to ManagerEntities

package CineplexEntities;

import java.util.List;
import java.util.ArrayList;
import java.lang.Math;


public class BookingManager {
    
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
            
        }
        System.out.println("==========MakeBooking==========");


    }



    //called when booking is confirmed
    public int makeBooking(){
        
        for (int i = 0; i < getSelectedSeats().size(); i++){

        }
    }
    private listBookings(int customerID){

    }
}
