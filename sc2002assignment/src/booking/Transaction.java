package booking;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;


/**
 * Transaction of booking made by customer.
 */
public class Transaction implements Serializable {
	// Attributes
    /**
     * Transaction ID of transaction.
     */
    private String transactionID;
    /**
     * Credit card Number used to make transaction.
     */
    private String creditCardNo;
    /**
     * List of tickets purchased during transaction.
     */
    private List<Ticket> ticketList;
    /**
     * Amount paid during transaction.
     */
    private double totalPrice;
    
    // Constructor

    /**
     * Instantiates new transaction by setting transaction ID and credit Card Number to first be NULL.
     * Creates empty ticket list for transaction and sets total price paid to be 0.
     */
    public Transaction(){
    	this.transactionID = null;
    	this.creditCardNo= null;
    	this.ticketList = new ArrayList<Ticket>();
    	this.totalPrice = 0;
    }
    
    
    // Getters
    
    public List<Ticket> getTicketList() {return ticketList;}
	public double getTotalPrice() {return totalPrice;}
	public String getTransactionID() {return transactionID;}   
    public String getCreditCardNo() {return creditCardNo;}

    
    // Setters
    
    public void setCreditCardNo(String creditCardNo) {this.creditCardNo = creditCardNo;}
    public void setTotalPrice(double totalPrice) {this.totalPrice = totalPrice;}
    public void setTicketList(List<Ticket> ticketList) {this.ticketList = ticketList;}
    
    // Called after booking is complete

    /**
     * Sets new transaction ID according to date time format.
     */
    public void setTransactionID() {
        LocalDateTime datetime= LocalDateTime.now() ;
        DateTimeFormatter formatter= DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        String datetimeStr = datetime.format(formatter);
        String newID = BookingManager.getInstance().getShowtime().getCinema().getCinemaID() + datetimeStr;
        this.transactionID = newID;
    }

}
	
