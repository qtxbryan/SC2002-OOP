package CineplexEntities;

import utilities.RootFinder;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Cinema object.
 */
public class Cinema implements Cloneable, Serializable {
	// Attributes
	/**
	 * unique cinemaID that identifies the cinema.
	 */
	private String cinemaID;
	/**
	 * hallNo of the cinema.
	 */
	private int hallNo;
	/**
	 * CinemaType enum specifying the type of the cinema.
	 */
	private CinemaType cinemaType;
	/**
	 * Total number of seats in the cinema.
	 */
	private int totalSeatNo;
	/**
	 * Number of currently occupied seats.
	 */
	private int occupiedSeatsNo;
	/**
	 * Shows the seating plan of the cinema. Seating plan is stored in a list.
	 */
	private List<String> cinemaLayout;
	

	// Constructor

	/**
	 * Constructor for the cinema object. It will open a text file from our "database" containing
	 * information about the cinema.
	 * @param cinemaID of the cinema.
	 */
	public Cinema(String cinemaID) {
		this.cinemaLayout = new ArrayList<String>();
		this.setCinemaID(cinemaID);			
		this.openCinemaFile(cinemaID);
	}

	/**
	 * To clone the object.
	 * @return cloned object.
	 * @throws CloneNotSupportedException when the object cannot be cloned.
	 */
	public Object clone() throws CloneNotSupportedException { 
		return super.clone(); 
	} 

	
	// Getters
	public String getCinemaID() {return cinemaID;}
	public int getHallNo() {return hallNo;}
	public CinemaType getCinemaType() {return cinemaType;}
	public int getTotalSeatNo() {return totalSeatNo;}
	public int getOccupiedSeatsNo() {return occupiedSeatsNo;}
	public List<String> getCinemaLayout() {return cinemaLayout;}

	/**
	 * To print the layout of the cinema stored in an ArrayList.
	 */
	public void printCinemaLayout() {
		int i=0;
		while (i<this.getCinemaLayout().size()) {
			System.out.println(this.getCinemaLayout().get(i));
			i++;
		}
	}

	
	// Setters
	public void setCinemaID(String cinemaID) {this.cinemaID = cinemaID;}
	public void setHallNo(int hallNo) {this.hallNo = hallNo;}
	public void setCinemaType(CinemaType cinemaType) {this.cinemaType = cinemaType;}
	public void setTotalSeatNo(int totalSeatNo) {this.totalSeatNo = totalSeatNo;}
	public void setOccupiedSeatsNo(int occupiedSeatsNo) {this.occupiedSeatsNo = occupiedSeatsNo;}
	public void setCinemaLayout(List<String> list) {this.cinemaLayout = list;}
	
  
	// Initializers: Below code used only for the very first run of the app
    // File Reader

	/**
	 * Reads in information of the cinema to be created from a text file.
	 * @param cinemaID unique identifier of cinemaID that specified filename from which information is read.
	 */
	private void openCinemaFile(String cinemaID) {
		try {
			// Get filepath
			String filePath = RootFinder.findRootPath();
			
			if (filePath == null) {
				throw new IOException("Cannot find root");
			} else {
				filePath = filePath + "/data/cinemas/cinema_" + cinemaID + ".txt";
			}			
			
			// Open file and traverse it						
			FileReader frStream = new FileReader( filePath );
			BufferedReader brStream = new BufferedReader( frStream );
			String inputLine;
			
			int i = 0;
			ArrayList<String> seatingPlan = new ArrayList<String>();

			do {
				inputLine = brStream.readLine(); // read in a line
				if (inputLine == null) {break;} // end of file
				
				switch (i) {
					case 0:
						// first line of file is the hall number
						this.setHallNo(Integer.parseInt(inputLine));
						break;		
					case 1:
						// second line of file is the cinema type (ENUM)
						this.setCinemaType(CinemaType.valueOf(inputLine));
						break;					
					case 2: 
						// third line of file is the total seats
						this.setTotalSeatNo(Integer.parseInt(inputLine));
						break;	
					case 3:
						// fourth line of file is the occupied seats
						this.setOccupiedSeatsNo(Integer.parseInt(inputLine));
						break;				
					default:
						// fifth line of file onwards will be the cinema layout
						seatingPlan.add(inputLine);
						break;
				}
				i++;
			} while (inputLine != null);
			
			brStream.close();	
			
			this.setCinemaLayout(seatingPlan);
			
		} catch ( FileNotFoundException e ) {
			System.out.println( "Error opening the input file!" + e.getMessage() );
			System.exit( 0 );
		} catch ( IOException e ) {
			System.out.println( "IO Error!" + e.getMessage() );
			e.printStackTrace();
			System.exit( 0 );
		}           
    }	
}
