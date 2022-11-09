package CineplexEntities;

import java.util.ArrayList;

import utilities.RootFinder;
import utilities.Serializer;

public class CompanyManager {
	// Attributes
	/**
	 * This is the entire company object
	 */
	private Company company;

	
	// Singleton
	/**
     * single_instance tracks whether CompanyManager has been instantiated before.
     */
	private static CompanyManager single_instance = null;
	
	/**
     * Instantiates the CompanyManager singleton. If no previous instance has been created,
     * one is created. Otherwise, the previous instance created is used.
     * @return an instance of CompanyManager.
     */
	public static CompanyManager getInstance() {
		if (single_instance == null)
			single_instance = new CompanyManager();
		return single_instance;
	}
	
	
	// Constructor
    /**
     * Constructor of CompanyManager. Tries to read in the serialized data first. If not available, it will create these files
     * from our initialization data
     */
	private CompanyManager() {
		Company serializedObject = this.load();
		if (serializedObject != null) {
			this.company = serializedObject;
		} else {
			this.company = new Company();
			this.save();
			System.out.println("Company File created!");
		}
	}
	
	
	// Public exposed methods to app
	/**
	 * This returns the company
	 * @return Company
	 */
	public Company getCompany(){
		return company;
	}
	
	/**
	 * This returns a deep copy of a cinema for the showtime manager to create a new showtime from. 
	 * The deep copy is always of the completely empty cinema 
	 * @param cinemaID This is the ID of the cinema to be copied
	 * @return Cinema this returns a new cinema object with all its attributes
	 */
	public Cinema getNewCinema(String cinemaID) {
		int i;
		int noOfCineplex = this.company.getCineplexes().size();
		
		for (i=0;i<noOfCineplex;i++) {
			Cineplex cineplex = this.company.getCineplexes().get(i);
			
			if (cineplex.getCinemaIDs().contains(cinemaID)) {
				int cinemaIndex = cineplex.getCinemaIDs().indexOf(cinemaID);

				Cinema newCinema = new Cinema(cinemaID);
				Cinema oldCinema = cineplex.getCinemas().get(cinemaIndex);

				newCinema.setCinemaID(oldCinema.getCinemaID());
				newCinema.setHallNo(oldCinema.getHallNo());
				newCinema.setCinemaType(oldCinema.getCinemaType());
				newCinema.setTotalSeatNo(oldCinema.getTotalSeatNo());
				newCinema.setOccupiedSeatsNo(oldCinema.getOccupiedSeatsNo());
				newCinema.setCinemaLayout(oldCinema.getCinemaLayout());
				return newCinema;
			}
		}
		
		return null;
	}
	
	/**
	 * This returns all the Cineplex Names that the company owns
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getCineplexNames() {
		ArrayList<String> cineplexNames = (ArrayList<String>) this.company.getCineplexNames();
		return cineplexNames;
	}
	
	
	// Private Serialization and Deserialization
	/**
	 * This saves the entire company object and serializes it
	 */
	public void save() {
		String filePath = RootFinder.findRootPath() + "/data/company/company.dat";
		Serializer.serializeObject(this.company, filePath);
	}
	
	/**
	 * This returns the entire company object from a serialized file
	 * @return
	 */
	public Company load() {
		String filePath = RootFinder.findRootPath() + "/data/company/company.dat";
		return (Company) Serializer.deserializeObject(filePath);
	}
}
