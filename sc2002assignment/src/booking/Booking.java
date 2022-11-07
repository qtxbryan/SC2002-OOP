package booking;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import MovieEntities.Movie;

/**
 * Booking made by Customer
 */
public class Booking {

	/**
	 * 
	 */
	private int bookingID; 
	private List<Ticket> tickets; 
	private int transactionID; 
	private LocalDateTime dateTime; 
	private int movieID; 
	private int hallNo; 
	private int cineplexID; 
	
	//Methods
	/**
	 * Display the booking
	 */
	private void displayBooking() {
		System.out.println("------------");
		System.out.printf("Booking ID: %d\n", getBookingID());
		//print movie name
		System.out.printf("Movie: %s\n", MovieEntities.Movie.getTitle());
		System.out.printf("Cineplex: %d\n", getCineplexID());
		System.out.printf("Hall: %d\n", getHallNo());

		//print showtime in specific format
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy, hh.mma");
		System.out.printf("Showtime: %s\n", getDateTime().format(formatter));

		//print tickets
		System.out.printf("Seats booked: ");
		for (int i = 0; i < getTickets().size(); i++){
			System.out.printf("%s", getTickets().get(i).getSeat());
			if (i+1 < getTickets().size()){
				System.out.printf(",");
			}
		System.out.println();

		//print transaction id
		System.out.printf("Transaction ID: %d\n", getTransactionID());
		System.out.println("------------");
		}
	}


	//getters
	public int getBookingID() {return bookingID;}
	public List<Ticket> getTickets() {return tickets;}
	public int getTransactionID(){ return transactionID;}
	public LocalDateTime getDateTime() {return dateTime;}
	public int getMovieID() {return movieID;}
	public int getHallNo() {return hallNo;}
	public int getCineplexID() {return cineplexID;}

	//setters
	public void setBookingID(int ID) {this.bookingID = ID;}
	public void setTickets(List<Ticket> tickets) {this.tickets = tickets;}
	public void setTransactionID(int transactionID) {this.transactionID = transactionID;}
	public void setDateTime(LocalDateTime datetime) {this.dateTime = dateTime;}
	public void setMovieID(int movieID) {this.movieID = movieID;}
	public void setHallNo(int hallNo) {this.hallNo = hallNo;}
	public void setCineplexID(int cineplexID) {this.cineplexID = cineplexID;}

	
}
