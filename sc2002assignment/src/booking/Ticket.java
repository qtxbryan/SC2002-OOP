package booking;
import java.io.Serializable;

/**
 * Ticket purchased by customer.
 */
public class Ticket implements Serializable {
    private TicketType ticketType;
    private Double ticketPrice;
    private String seatID;

    /**
     * Assigns type of ticket to ticket.
     * @param ticketType Type of ticket. (e.g STANDARD,SENIOR)
     */
    // Constructor
    public Ticket(TicketType ticketType) {
    	this.ticketType = ticketType;
    }
    
    
    // Getters
    
    public TicketType getTicketType() {return ticketType;}
    public void setTicketType(TicketType ticketType) {this.ticketType = ticketType;}
    public Double getTicketPrice() {return ticketPrice;}

    
    // Setters
    
    public void setTicketPrice(Double ticketPrice) {this.ticketPrice = ticketPrice;}
    public String getSeatID() {return seatID;}
    public void setSeatID(String seatID) {this.seatID = seatID;}
}