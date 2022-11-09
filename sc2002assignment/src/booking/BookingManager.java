package booking;

import java.util.List;
import java.util.ArrayList;
import java.lang.Math;
import MovieEntities.Showtime;
import MovieEntities.ShowtimeManager;
import CineplexEntities.CinemaStatus;
import customer.CustomerManager;
import utilities.*;
import java.util.HashMap;
import java.util.Scanner;

/**
 * This is the booking manager. It will interface with all booking related issues
 * The booking manager is the central control class for all booking related issues, and will interact with every other manager to coordinate
 */
public class BookingManager implements ResetSelf {
    // Attributes
	/**
	 * This is the current seating plan of the cinema being booked
	 */
    private List<String> seatingPlan;
    
    /**
     * This is the currently selected seats
     */
    private List<String> selectedSeats = new ArrayList<String>();
    
    /**
     * Checks for seat's columns and stores them. SEAT COL : INDEX in string
     */
    private HashMap<Integer, Integer> colChecker = new HashMap<Integer, Integer>(); 
    
    /**
     * Checks if a seat has been booked in a specific row
     */
    private HashMap<Character, ArrayList<Integer>> rowChecker = new HashMap<Character, ArrayList<Integer>>();
    
    /**
     * This is the current booking that is being filled up
     */
    private Booking booking = null; // Current booking to make
    
    /**
     * This is the current showtime that is being booked
     */
    private Showtime showtime = null; // Current showtime selected
    
	/**
	 * This is for loop control to exit the current transaction manager "window"
	 */
    public Boolean exit = false;
    
    private Scanner sc = new Scanner(System.in);
    
    
    // Singleton
	/**
     * single_instance tracks whether BookingManager has been instantiated before.
     */
 	private static BookingManager single_instance = null;
 	

	/**
     * Instantiates the BookingManager singleton. If no previous instance has been created,
     * one is created. Otherwise, the previous instance created is used.
     * @return an instance of BookingManager.
     */
	public static BookingManager getInstance()
	{
	    if (single_instance == null) {
	    	 single_instance = new BookingManager();
	    }	        
	    return single_instance;
	}
	    
	
	// Constructor
    /**
     * Constructor of BookingManager. Resets itself to clear its current memory in preparation for a new booking
     */
 	private BookingManager() {
 		resetSelf();
 	}
	
	
    // Public exposed methods to app
    /**
     * Starts a booking by showing available seats and allows user to select seats based on a copy of showtime
     * @param baseShowtime Showtime This is the original showtime object that is not going to be changed until the booking is finalized
     */
    public void startSeatSelection(Showtime baseShowtime) {
    	// Create a deep copy of showtime seats so we don't affect the original until booking completes
    	setShowtime(baseShowtime); // Contain reference to selected showtime
    	setSeatingPlan(copySeatingPlan(baseShowtime.getCinema().getCinemaLayout()));

    	// Go through the seating plan and populate the seat columns : index hashmap
    	// Iterate through List of Strings
    	for (int row = 0; row < getSeatingPlan().size(); row++) {
    		String rowRef = getSeatingPlan().get(row);
    		
    		// Check if row is null
    		if (rowRef == null || rowRef.length() == 0) {
    			System.out.println();
    			continue;
    		}
    		
    		// Check if this row has a number. If it has immediately stop as it is the seat columns indicator row.
    		Boolean found = false;
    		int col = 0;
    		while (col < rowRef.length()) {
    			int item = rowRef.charAt(col);
    			
    			// First occurrence of number, we hit the column indicator row
        		if (Character.isDigit(item)) {
        			found = true;
        			String seatColumn = Character.toString(item);
        			
        			// Check next item, if it's number use that instead, as it could be 14 (2 digit number)
        			if (col + 1 < rowRef.length()) {
        				if (Character.isDigit(rowRef.charAt(col+1))) {
        					// If it's digit, use its index as the number
        					seatColumn = seatColumn + Character.toString(rowRef.charAt(col+1)); // New 2 digit number
        					col++;
        				}
        			}
        			
        			getColChecker().put(Integer.valueOf(seatColumn), col); // Put in seat col : index
        		}
        		
        		col++;
    		}

    		if (found) {
    			break; // Terminate
    		}
    	}
    	
    	
    	// Show them booking menu until they exit
    	exit = false;
    	while (!exit) {
    		
    		// If no more seats left, terminate
    		if (baseShowtime.getCinemaStatus() == CinemaStatus.SOLD_OUT) {
    			System.out.println("Sorry. This showtime is sold out. Please select another showtime.");
    			exit = true;
    			resetSelf();
    			return;
    		}
    		
    		// Show the seating plan
        	displaySeats(getSeatingPlan());
        	
        	System.out.println("==================== SEAT BOOKING ====================\n"+
							   " 1. Select a seat                                   \n"+
								" 2. Deselect a seat                                 \n"+
								" 3. Confirm and proceed to ticket selection         \n"+
								" 0. Exit			                                  \n"+
							   "======================================================");
        	System.out.println("Please select a choice:");
        	
        	while (!sc.hasNextInt()) {
            	System.out.println("Invalid input type. Please enter an integer value.");
        		sc.next(); // Remove newline character
            }
        	
        	switch(sc.nextInt()) {
        		case 0: // Exit, reset everything
        			exit = true;
        			resetSelf();
        			break;
        		case 1: // Select seat
        			addSeatSelection();
        			break;
        		case 2: // Deselect seat
        			deleteSeatSelection();
        			break;
        		case 3: // Ticket selection, requires at least a seat selection
        			// If no seats selected, don't allow ticket selection
        			if (getSelectedSeats().size() <= 0) {
        				System.out.println("No seats selected. Please select a seat before choosing tickets.");
        			}
        			else {
        				// Book tickets
        				TicketManager.getInstance().startTicketSelection(getShowtime(), getSelectedSeats());
        			}
        			break;
        		default:
        			System.out.println("Invalid choice entered. Please try again.");
        	}
    	}
    }
    
    /**
     * Once booking is confirmed and payment is made, we raise an event to let other parts know to inject information into this booking
     */
    public void makeBooking() {
    	// Since booking is confirmed, we update all selected seats to be confirmed seats (occupied)
    	for (int i = 0; i < getSelectedSeats().size(); i++) {
    		updateSeatingPlan(getSelectedSeats().get(i), "confirmSelection");
    	}
    	
    	// We then update the current showtime REFERENCE with the new seating plan, and update the showtime fill status
    	getShowtime().getCinema().setCinemaLayout(getSeatingPlan());
    	getShowtime().updateCinemaStatus();
    	
    	// Save the new showtimes status
    	ShowtimeManager.getInstance().save(getShowtime(), getShowtime().getShowtimeID());
    	
    	// We create a new booking and fill it up with the finalized information before storing it
    	setBooking(new Booking());
    	
    	// We now fill up the booking's tickets, transactionID, bookerName, bookerMobileNo and bookerEmail
    	// Note that this also resets both these Managers (calls their respective reset functions)
    	// In these functions, we also update the movie's ticket sales and gross profits
    	// These also store the transaction as a serialized object
    	TicketManager.getInstance().confirmTicketSelection();
    	TransactionManager.getInstance().confirmTransaction(); 	
    	
    	// Now we have to update the rest of booking: bookingID, movieName, hallNo and cineplexName
    	
    	// Update the booking with the showtime's date and time
    	getBooking().setDateTime(getShowtime().getDateTime());
    	
    	// Update booking's movieName, cineplexName and hallNo
    	getBooking().setMovieID(showtime.getMovieID());
    	getBooking().setCineplexName(showtime.getCineplexName());
    	getBooking().setHallNo(showtime.getCinema().getHallNo());
    	
    	// Finally, we can generate a unique ID for this booking
    	getBooking().setBookingID(IDHelper.getLatestID("booking"));
    	
    	// We store the booking in our CustomerAccount
    	CustomerManager.getInstance().storeBooking(getBooking().getBookingID());
    	
    	// Print confirmation
    	System.out.println("Booking confirmed! Your booking details:");
    	getBooking().displayBooking();
    	System.out.println("You may want to print out a copy for your reference.");
    	System.out.println("Returning back to main screen...");
    	
    	// We can then send this booking off to store as a serialized file
    	String filePath = RootFinder.findRootPath();
    	filePath = filePath + "/data/bookings/booking_" + getBooking().getBookingID() + ".dat";
    	
    	Serializer.serializeObject(getBooking(), filePath);
    	   	
    	// Finally, reset this instance and all instances
    	TicketManager.getInstance().resetSelf();
    	TransactionManager.getInstance().resetSelf(); 	
    	resetSelf();
    }
    
    /**
     * Resets all variables of this singleton instance (e.g. if user presses back)
     */
    public void resetSelf() {
    	setShowtime(null);
		setBooking(null);
		setSeatingPlan(null);
    	getSelectedSeats().clear();
		getRowChecker().clear();
		getColChecker().clear();
		exit = true;
    }
    
    
    // Getters
    /**
     * This gets the current showtime being booked
     * @return Showtime
     */
	public Showtime getShowtime() {return showtime;}
	
	/**
	 * This gets the current booking being booked
	 * @return Booking
	 */
	public Booking getBooking() {return booking;}
	
	/**
	 * This gets the current seating plan
	 * @return List<String>
	 */
    public List<String> getSeatingPlan() {return seatingPlan;}
    
    /**
     * This gets the current selected seats 
     * @return List<String>
     */
	public List<String> getSelectedSeats() {return selectedSeats;}
	
	/**
	 * This gets the row checker, see whether the row is valid or not
	 * @return HashMap<Character, ArrayList<Integer>>
	 */
	public HashMap<Character, ArrayList<Integer>> getRowChecker() {return rowChecker;}
	
	/**
	 * This gets the column checker, see whether the column is valid or not
	 * @return HashMap<Character, ArrayList<Integer>>
	 */
	public HashMap<Integer, Integer> getColChecker() {return colChecker;}
	
    // Setters
    /**
     * This sets the current showtime that we are booking
     * @param showtime The showtime that we want to book
     */
    public void setShowtime(Showtime showtime) {this.showtime = showtime;}
    
    /**
     * This sets the current booking that we are working with
     * @param booking The booking that we want to book
     */
    public void setBooking(Booking booking) {this.booking = booking;}
    
    /**
     * This sets the current seating plan that we are working with, which is visual only so we do not affect the underlying seating plan
     * @param seatingPlan The seating plan that we want to display
     */
    public void setSeatingPlan(ArrayList<String> seatingPlan) {this.seatingPlan = seatingPlan;}
    
	

    
    
    
    // Private methods
	/**
	 * Prints out seating plan in a nice manner upon being given a List of Strings
	 * @param list List<String> this is the layout of the cinema being booked
	 */
    private void displaySeats(List<String> list) {
    	char item;
    	String rowRef;
    	String[] symbols = {"0", "1", "S"}; // Symbols for seat status display
    	
    	// Iterate through List of Strings
    	for (int row = 0; row < list.size(); row++) {
    		rowRef = list.get(row);
    		
    		// Check if row is null
    		if (rowRef == null || rowRef.length() == 0) {
    			System.out.println();
    			continue;
    		}
    		
    		// Check if this row has seats. If not, just print whole row straight.
    		if (!(Character.isAlphabetic(rowRef.charAt(0)))) { // No seats
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
    
    
    /**
     * Create a local deep copy of showtime's seats
     * @param list List<String> This is the layout of the cinema being booked
     * @return ArrayList<String> This is a new copy that will be visually updated without affected the actual booking object
     */
    private ArrayList<String> copySeatingPlan(List<String> list) {
    	ArrayList<String> seatsCopy = new ArrayList<String>();
    	int i = 0;
		while (i < list.size()) {
			seatsCopy.add(list.get(i));
			i++;
		}
    	return seatsCopy;
    }
    
    
    /**
     * Add a seat selection. We need to check that all seats added are adjacent to each other, and are unoccupied.
     */
    private void addSeatSelection() {
		System.out.println("Please enter a seat selection (e.g. C6):");
		
		while (!sc.hasNext()) { // Not a string
			sc.next(); // Remove newline character
			System.out.println("Invalid input! Please enter a valid seat ID.");
		}
		
		String selection = sc.next().toUpperCase();
		
		// Check if seat selection is allowable. If it is, update rowChecker and selectedSeats
		if (allowSeatSelection(selection)) {
			char seatRow = selection.charAt(0); // Alphabet row of seatID
			int seatCol = Integer.valueOf(selection.substring(1, selection.length())); // Integer column of seatID
			
			// Add the new seat selection to selectedSeats
			getSelectedSeats().add(selection);
			
			// If no existing seats selected for a row, create the row in rowChecker
			if (!(getRowChecker().containsKey(seatRow))) {
				getRowChecker().put(seatRow, new ArrayList<Integer>());		
			}
			
			// Add in the selected seat in the row for rowChecker
			getRowChecker().get(seatRow).add(seatCol);
			
			// Update seating plan
			System.out.println("Seat selection " + selection + " added!");
	    	updateSeatingPlan(selection, "addSelection");
		}
		
		// If invalid selection, error is automatically generated by allowSeatSelection() function.
    }
    
    
    /**
     * Checks if seat selection is valid
     * @param seatID This is the current seatID that is being booked
     * @return Boolean whether the seat can be booked or not
     */
    private Boolean allowSeatSelection(String seatID) {
    	// First we check if entered seat ID is valid aka starts with alphabet and has an integer (capped at 2 digits)
    	if (seatID.matches("[A-Z]\\d{1,2}")) {
			// If seatID entered is valid, check if seat exists
			char seatRow = seatID.charAt(0); // Alphabet row of seatID
			int seatCol = Integer.valueOf(seatID.substring(1, seatID.length())); // Integer column of seatID
			String rowRef;
			
			// Iterate through rows of seating plan to find seat row alphabet
			for (int row = 1; row < getSeatingPlan().size(); row++) {				
				rowRef = getSeatingPlan().get(row);
				
				// Check if row is null
	    		if (rowRef == null || rowRef.length() == 0) {
	    			continue;
	    		}
				
				// If we find a match for seat row && seatCol exists in this cinema (i.e. no overshot)
	    		
				if (rowRef.charAt(0) == seatRow && getColChecker().containsKey(seatCol)) {
					
					int index = getColChecker().get(seatCol);
					
					// Seat column required is within index
					if (index < rowRef.length()) {
						// If unoccupied
						if (rowRef.charAt(index) == '0') {
							if (rowChecker.containsKey(seatRow)) {
								ArrayList<Integer> rowSeats= rowChecker.get(seatRow);
								
								// Selected seat column must be +/- one of any existing entry
								for (int i = 0; i < rowSeats.size(); i++) {
									if (Math.abs(rowSeats.get(i) - seatCol) == 1) {
										// Seat exists, is unoccupied, and is adjacent to a previous selection, allow selection.
										return true;
									}
								}
								
								// Seat is not adjacent to previous selections in the same row, return false
								System.out.println("Seat selection invalid. Please do not leave spaces between seats.");
								return false;
							}
							else {
								return true; // Row has not been booked before, and the seat is unoccupied.
							}
						}
						// Seat is already occupied, disallow selection
						else {
							System.out.println("Seat selection invalid. This seat has already been selected or is otherwise occupied.");
							return false;
						}
					}
				}	 	
			}
		} 	
    	// If we reach here, either the input is invalid, the row doesn't exist, or the column of seat doesn't exist.
    	System.out.println("Invalid seatID input. Please try again.");
    	return false;
    }
    
    
    /**
     * Deletes a seat selection
     */
    private void deleteSeatSelection() {
    	System.out.println("Please enter a seat selection to remove (e.g. C6):");
		String selection = sc.next().toUpperCase();
		
		// Check if seat selection is allowable. If it is, update rowChecker and selectedSeats
		if (allowSeatDeletion(selection)) {
			char seatRow = selection.charAt(0); // Alphabet row of seatID
			int seatCol = Integer.valueOf(selection.substring(1, selection.length())); // Integer column of seatID
		
			// Remove seat selection from selectedSeats
			getSelectedSeats().remove(selection);
			
			// Remove the seat from rowChecker
			getRowChecker().get(seatRow).remove((Integer)seatCol);
			
			// If the row is empty after removing this seat, then remove row entry from rowChecker
			if (getRowChecker().get(seatRow).size() <= 0) {
				getRowChecker().remove(seatRow);
			}
			
			// Update seating plan
			System.out.println("Seat selection " + selection + " removed!");
			updateSeatingPlan(selection, "deleteSelection");
		}
		
		// If invalid selection, error is automatically generated by allowSeatDeletion() function.
    }
    
    
    /**
     * Checks if seat selection removal is valid
     * @param seatID The seat that is being deleted
     * @return Boolean Whether the seat can be deleted or not
     */
    private Boolean allowSeatDeletion(String seatID) {
    	// First we check if entered seat ID is valid aka starts with alphabet and has an integer (capped at 2 digits)
    	if (seatID.matches("[A-Z]\\d{1,2}")) {
			// If seatID entered is valid, check if seat exists in currently selected seats
    		if (getSelectedSeats().contains(seatID)) {
    			char seatRow = seatID.charAt(0); // Alphabet row of seatID
    			int seatCol = Integer.valueOf(seatID.substring(1, seatID.length())); // Integer column of seatID
    			
    			// We need to check if the seat exists in a row between selections to ensure no gaps are formed
    			ArrayList<Integer> rowRef = getRowChecker().get(seatRow);
    			
    			// If seat for deletion is in between two other seats, do not allow deletion
    			if (rowRef.contains(seatCol+1) && rowRef.contains(seatCol-1)) {
    				System.out.println("The seatID specified could not be deselected. Please do not leave spaces between seat selections.");
					return false;
    			}
    			// Else, the seat is either an edge seat or is a solitary seat in that row, allow deselection
    			else {
    				return true;
    			}
    		}
    		// Seat doesn't exist as a selection
    		else {
    			System.out.println("SeatID does not match any currently selected seats. Please try again.");
    			return false;
    		}
		}

    	// If we reach here, either the input is invalid, the row doesn't exist, or the column of seat doesn't exist.
    	System.out.println("Invalid seatID input. Please try again.");
    	return false;
    }
    
    
    /** 
     * Update seating plan List. Operation can be: addSelection, deleteSelection or confirmSelection
     * @param seatID The current seat being booked
     * @param operation What we are doing to the current seat
     */
    private void updateSeatingPlan(String seatID, String operation) {
    	char seatRow = seatID.charAt(0); // Alphabet row of seatID
		int seatCol = Integer.valueOf(seatID.substring(1, seatID.length())); // Integer column of seatID
		String seatModifier;
		
		switch(operation) {
			case "addSelection":
				seatModifier = "2";
				break;
			case "deleteSelection":
				seatModifier = "0";
				break;
			case "confirmSelection":
				seatModifier = "1";
				break;
			default:
				System.out.println("Error! Operation parameter for function updateSeatingPlan() in BookingManager is invalid.");
				return; // Terminate
		}
		
		// Iterate until we find the seat's row and col index position in the seatingPlan List
    	for (int row = 0; row < getSeatingPlan().size(); row++) {
    		String rowRef = getSeatingPlan().get(row);
    		
    		// Check if row is null
    		if (rowRef == null || rowRef.length() == 0) {
    			System.out.println();
    			continue;
    		}
    		
    		// Found seat row
    		if (rowRef.charAt(0) == seatRow) {
    			int index = getColChecker().get(seatCol);
    			
 				String updatedRow = rowRef.substring(0, index) + seatModifier + rowRef.substring(index+1);
 				
 				// Replace current row
 				getSeatingPlan().set(row, updatedRow);
 				return; // Terminate function
    		}
    	}
    }
    
}