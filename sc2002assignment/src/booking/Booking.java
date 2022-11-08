package booking;

import MovieEntities.MovieManager;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.io.*;

/**
 * Booking made by Customer
 */
public class Booking implements Serializable {
	
    //Attributes
	/**
	 * Booking ID of booking.
	 */
    private String bookingID;
	/**
	 * List of Tickets purchased per booking.
	 */
	private List<Ticket> tickets;
	/**
	 * Transaction ID of booking.
	 */
    private String transactionID;
	/**
	 * Time booking was made.
	 */
	private LocalDateTime dateTime;
	/**
	 * Movie ID of movie booked.
	 */
    private String movieID;
	/**
	 * Hall No of movie booked.
	 */
	private int hallNo;
	/**
	 * Name of cineplex where movie booked is showing.
	 */
    private String cineplexName;

    
    //Methods
	/**
	 * Displays booking in formatted form.
	 */
    public void displayBooking() {
    	System.out.println("=============================================================================");
    	System.out.printf("Booking ID: %s\n", getBookingID());
    	System.out.printf("Movie: %s\n", MovieManager.getInstance().getMoviebyID(getMovieID()).getTitle());
    	System.out.printf("Cineplex: %s\n", getCineplexName()); 	
    	System.out.printf("Hall: %d\n", getHallNo());
    	
    	// Print showtime date and time
        DateTimeFormatter formatter= DateTimeFormatter.ofPattern("dd MMM yyyy, hh.mma");
    	System.out.printf("Showtime: %s\n", getDateTime().format(formatter));
    	
    	// Print tickets
    	System.out.printf("Seats booked: ");
    	for (int i = 0; i < getTickets().size(); i++) {
    		System.out.printf("%s", getTickets().get(i).getSeatID());
    		if (i+1 < getTickets().size()) {
    			System.out.printf(", ");
    		}
    	}
    	System.out.println();

    	// print transaction id
		System.out.println("Transaction ID: " + getTransactionID());
		System.out.println("=============================================================================");
    }
    
    
    // Getters
    
	public String getBookingID() {return bookingID;}
	public List<Ticket> getTickets() {return tickets;}
	public String getTransactionID() {return transactionID;}
    public LocalDateTime getDateTime() {return dateTime;}
	public String getMovieID() {return movieID;}
	public int getHallNo() {return hallNo;}
	public String getCineplexName() {return cineplexName;}
	
	
	// Setters
	
	public void setBookingID(String latestID) {this.bookingID = latestID;}	
	public void setTickets(List<Ticket> tickets) {this.tickets = tickets;}
	public void setTransactionID(String transactionID) {this.transactionID = transactionID;}
    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }
	public void setMovieID(String movieID) {this.movieID = movieID;}
	public void setHallNo(int hallNo) {this.hallNo = hallNo;}
	public void setCineplexName(String cineplexName) {this.cineplexName = cineplexName;}
}
