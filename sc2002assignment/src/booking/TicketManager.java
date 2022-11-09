package booking;

import MovieEntities.MovieManager;
import MovieEntities.Showtime;
import Staff.SystemSettingsManager;
import utilities.ResetSelf;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.time.format.TextStyle;

/**
 * This is the TicketManager. It will handle the ticket booking for the customer 
 *
 */
public class TicketManager implements ResetSelf {
	// Attributes
	/**
	 * This holds the current list of selected tickets by the customer during their booking
	 */
    private List<Ticket> selectedTickets = new ArrayList<Ticket>();
    
    /**
     * This holds the total number of each ticket type there is
     */
    private Map<TicketType, Integer> ticketCount = new HashMap<TicketType, Integer>();
    
    /**
     * This holds the various prices of the ticket types. These prices are the modifed prices 
     * after implementing the price modifiers for every aspect of the booking in question
     */
    private Map<TicketType, Double> ticketPrices = new HashMap<TicketType, Double>();
    
    private Scanner sc = new Scanner(System.in);
    
    /**
     * This is for loop control to exit the current ticket manager "window"
     */
    public Boolean exit = false;
	

	// Singleton
	/**
     * single_instance tracks whether TicketManager has been instantiated before.
     */
 	private static TicketManager single_instance = null;
	
	/**
     * Instantiates the TicketManager singleton. If no previous instance has been created,
     * one is created. Otherwise, the previous instance created is used.
     * @return an instance of TicketManager.
     */
	public static TicketManager getInstance()
	{
	    if (single_instance == null) {
	    	 single_instance = new TicketManager();
	    }	        
	    return single_instance;
	}

	
	// Constructor
    /**
     * Constructor of TicketManager. Doesn't do much
     */
	private TicketManager() {}
	
	// Public exposed methods to app
	
	/**
	 * Starts ticket selection process for the customer
	 * @param showtime This is the current showtime object that is being booked
	 * @param selectedSeats This is the current list of selected seats by the customer
	 */
    public void startTicketSelection(Showtime showtime, List<String> selectedSeats) {
    	
    	// Get new ticket prices based on showtime
    	updateTicketPrices(showtime);
    	
    	exit = false;
    	int maxTickets = selectedSeats.size(); // Total number of tickets available for selection
    	int ticketChoices = TicketType.values().length; // Number of ticket choices available
    	int ticketsLeft = maxTickets; // Tracks number of tickets left for selection
    	int choice;
    	
    	while (!exit) {
    		
    		ticketsLeft = maxTickets - getSelectedTickets().size();
    		
    		// Prints out ticket type selection menu
    		System.out.println("============================ SEAT BOOKING =============================");
    		System.out.printf("You have selected %d seat(s). %d ticket(s) remaining to select:\n", selectedSeats.size(), ticketsLeft);
    		
    		for (int i = 1; i <= ticketChoices; i++) {
    			System.out.printf("%d. %s\n", i, TicketType.values()[i-1].toString());
    		}
    		System.out.printf("%d. Clear selected tickets\n", ticketChoices+1);
    		System.out.printf("%d. Proceed to payment\n", ticketChoices+2);
    		System.out.println("0. Back to seats selection");		
    		System.out.println("=======================================================================");
    		
    		System.out.println("Please select a choice:");
    		
    		while (!sc.hasNextInt()) {
            	System.out.println("Invalid input type. Please enter an integer value.");
        		sc.next(); // Remove newline character
            }
    		
    		choice = sc.nextInt();
    		
    		// Clear selected tickets and ticketCount, return to seats
    		if (choice == 0) { 	// Exit
    			exit = true;
    			resetSelf();
    		}
    		// Clear selected tickets but try again  	
    		else if (choice == ticketChoices+1) { 		
    			clearTicketSelection();
    			System.out.println("Ticket selections cleared.");
    		}
    		// Check all seats have tickets, then proceed to payment
    		else if (choice == ticketChoices+2) { 		
    	    	
    			// Check number of tickets = number of selected seats
    			if (ticketsLeft == 0) {
    			
	    	    	// Update all tickets in selection with seatID information
	    	    	for (int j = 0; j < getSelectedTickets().size(); j++) {
	    	    		getSelectedTickets().get(j).setSeatID(selectedSeats.get(j));
	    	    	}
	    	    	
	    	    	// PROCEED TO PAYMENT, passes on ticket prices as well as a count of tickets selected
	    	    	exit = true;
	    	    	TransactionManager.getInstance().startTransaction(getSelectedTickets(), getTicketPrices(), getTicketCount());
    	    	}
    			else {
    				System.out.printf("Not enough tickets selected! %d tickets remaining to select.\n", ticketsLeft);
    			}
    		}
    		// Add ticket selection based on its type. Update ticketCount and ticketSelection.
    		else if (choice >= 1 && choice <= ticketChoices) {
    			System.out.printf("How many %s tickets would you like to purchase? (Max %d):\n", TicketType.values()[choice-1].toString(), ticketsLeft);
    			
    			while (!sc.hasNextInt()) {
                	System.out.println("Invalid input type. Please enter an integer value.");
            		sc.next(); // Remove newline character
                }
    			
    			int count = sc.nextInt();

    			// Too little or too many tickets booked, go back to ticket selection menu
    			if (count < 1 || count > ticketsLeft) {
    				System.out.println("Too many/little tickets selected! If you would like to change ticket selections, please clear selections and try again.");
    				continue;
    			}
    			
    			// Else we update ticketCount and ticketSelection.
    			addTicketSelection(TicketType.values()[choice-1], count);
    		}
    		// Invalid choice
    		else {
    			System.out.println("Invalid choice entered. Please try again.");
    		}
    	}
    }
    
    
    
    /**
     * This resets the ticket manager instance, and clears all the current memory 
     */
    public void resetSelf() {
    	getSelectedTickets().clear();
    	getTicketCount().clear();
    	getTicketPrices().clear();
    	exit = true;
    }
    
    
    // Getters
    /**
     * This returns the current selected tickets by the customer
     * @return List<Ticket> 
     */
 	public List<Ticket> getSelectedTickets() {return selectedTickets;}
 	
 	/**
 	 * This returns the current ticket count for type of ticket
 	 * @return Map<TicketType, Integer> 
 	 */
 	public Map<TicketType, Integer> getTicketCount() {return ticketCount;}
 	
 	/**
 	 * This returns the current ticket prices by ticket type 
 	 * @return Map<TicketType, Double>
 	 */
 	public Map<TicketType, Double> getTicketPrices() {return ticketPrices;}
    
    /**
     * Injects ticket selection information into BookingManager's booking when event is raised, and reset ticketmanager
     */
    public void confirmTicketSelection() {
    	BookingManager.getInstance().getBooking().setTickets(getSelectedTickets());
    	
    	// Update movie's ticket sales info
    	String currMovieID = BookingManager.getInstance().getShowtime().getMovieID();
    	MovieManager.getInstance().updateTicketsSold(currMovieID, getSelectedTickets().size());
    }

    /**
     * Adds a new ticket selection
     * @param ticketType TicketType This is the type of ticket
     * @param count This is the number of that type of ticket
     */
    private void addTicketSelection(TicketType ticketType, int count) {
    	for (int i = 0; i < count; i++) {
    		Ticket newTicket = new Ticket(ticketType);
    		
    		// Update ticket price based on cinema type, holiday and time
    		newTicket.setTicketPrice(getTicketPrices().get(ticketType));
    		
    		getSelectedTickets().add(newTicket);
    	}
    	getTicketCount().put(ticketType, count);
    }
   

    
    // Private methods
    
    /**
     * Updates ticket prices for all tickets types for current showtime
     * @param showtime this is the current showtime object being booked
     */
    private void updateTicketPrices(Showtime showtime) {
    	Map<TicketType, Double> prices = getTicketPrices();
    	SystemSettingsManager priceList = SystemSettingsManager.getInstance();
    	
    	for (TicketType type : TicketType.values()) { 
    		// Get base price based on ticket type
    	    prices.put(type, SystemSettingsManager.getInstance().getPrice("BASE"));
    	    
    	    // Update price based on ticket type
    	    prices.put(type, prices.get(type) + priceList.getPrice(type.toString()));
    	    
    	    // Update price based on date (see if it's a holiday)
    	    prices.put(type, prices.get(type) + priceList.getPrice(showtime.getDateTime().toLocalDate()));
    	    
    	    // Update price based on day of week
    	    String day = showtime.getDateTime().getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.getDefault());
    	    prices.put(type, prices.get(type) + priceList.getPrice(day.toUpperCase()));
    	    
    	    // Update price based on movie format
    	    prices.put(type, prices.get(type) + priceList.getPrice(showtime.getMovieFormat().toString()));
    	    
    	    // Update price based on cinema type
    	    prices.put(type, prices.get(type) + priceList.getPrice(showtime.getCinema().getCinemaType().toString()));
    	}
    }

    /** 
     * Clear tickets, doesn't clear other info
     */
    private void clearTicketSelection() {
    	getSelectedTickets().clear();
    	getTicketCount().clear();
    }
    
}
