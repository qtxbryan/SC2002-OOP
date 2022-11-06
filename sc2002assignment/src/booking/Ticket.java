package booking;

import CineplexEntities.seat;


public class Ticket {
	private int ticketID;
	private TicketType ticketType;
	private Seat seat;
	private double ticketPrice;


	//Constructor
	public Ticket (TicketType ticketType){
		this.ticketType = ticketType;
	}



	//getters
	public int getTicketID() {return ticketID;}
	public TicketType getTicketType() {return ticketType;}
	public Seat getSeat() {return seat;}
	public double getTicketPrice() {return ticketPrice;}

	//setters
	public void setTicketID(int ticketID) {this.ticketID = ticketID;}
	public void setTicketType(TicketType ticketType) {this.ticketType = ticketType;}
	public void setSeat(Seat seat) {this.seat = seat;}
	public void setTicketPrice(double ticketPrice) {this.ticketPrice = ticketPrice;}
}
