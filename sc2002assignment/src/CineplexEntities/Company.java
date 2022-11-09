package CineplexEntities;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import utilities.RootFinder;

public class Company implements Serializable {
	// Attributes
	/**
	 * Company name.
	 */
    private String companyName;
	/**
	 * List of cineplex objects that are owned by the company.
	 */
	private List<Cineplex> cineplexes;
	/**
	 * List of IDs of the cineplexes owned by the company.
	 */
    private List<String> cineplexNames;
    
    
    // Constructor

	/**
	 * Constructor for the company. Reads from text file details about the company.
	 */
	public Company(){
    	this.cineplexes = new ArrayList<Cineplex>();
    	this.cineplexNames = new ArrayList<String>();
    	this.openCompanyFile();
    }

    
    // Getters
    public String getCompanyName() {return this.companyName;}
    public List<Cineplex> getCineplexes() {return this.cineplexes;}
    public List<String> getCineplexNames() {return this.cineplexNames;} 


    // Setters
    public void setCompanyName(String companyName){
		this.companyName = companyName;
    }

	/**
	 * Add cineplex to the array list of cineplexes.
	 * @param cineplexName of cineplex to be added. Cineplex object will be constructed based on the name.
	 */
	public void addCineplexes(String cineplexName){
    	Cineplex cineplex = new Cineplex(cineplexName);
    	this.cineplexes.add(cineplex); 
    	this.cineplexNames.add(cineplex.getCineplexName());
    }
    
    
    
    
    
    
    
    
    
    
    
    
	// Initializers: Below code used only for the very first run of the app
    // File Reader

	/**
	 * Reads information about company from text file in our "database".
	 */
    private void openCompanyFile() {
		try {
			// Get filepath
			String filePath = RootFinder.findRootPath();
			
			if (filePath == null) {
				throw new IOException("Cannot find root");
			} else {
				filePath = filePath + "/data/company/company.txt";
			}
			
			// Open file and traverse it
			FileReader frStream = new FileReader( filePath );
			BufferedReader brStream = new BufferedReader( frStream );
			String inputLine;
			int i = 0;

			do {
				inputLine = brStream.readLine(); // read in a line
				if (inputLine == null) {break;} // end of file
				
				if (i==0) {
					// first line of file is the company name
					this.setCompanyName(inputLine);
				} else {	
					// all other lines are lists of cineplexes the company owns
					this.addCineplexes(inputLine);
				}

				i++;
			} while (inputLine != null);
			
			brStream.close();	
			
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
