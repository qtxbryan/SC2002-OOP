package movie;

import java.time.LocalDate;
import java.util.List;

import booking.Ticket;

public class Movie {

	private int bookingID; 
	private List<Ticket> tickets; 
	private int transactionID; 
	private LocalDate dateTime; 
	private int movieID; 
	private int hallNo; 
	private int cineplexID;
	public int getBookingID() {
		return bookingID;
	}
	public void setBookingID(int bookingID) {
		this.bookingID = bookingID;
	}
	public List<Ticket> getTickets() {
		return tickets;
	}
	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}
	public int getTransactionID() {
		return transactionID;
	}
	public void setTransactionID(int transactionID) {
		this.transactionID = transactionID;
	}
	public LocalDate getDateTime() {
		return dateTime;
	}
	public void setDateTime(LocalDate dateTime) {
		this.dateTime = dateTime;
	}
	public int getMovieID() {
		return movieID;
	}
	public void setMovieID(int movieID) {
		this.movieID = movieID;
	}
	public int getHallNo() {
		return hallNo;
	}
	/**
	 * @param hallNo
	 */
	public void setHallNo(int hallNo) {
		this.hallNo = hallNo;
	}
	public int getCineplexID() {
		return cineplexID;
	}
	public void setCineplexID(int cineplexID) {
		this.cineplexID = cineplexID;
	}
	
	
	
}
