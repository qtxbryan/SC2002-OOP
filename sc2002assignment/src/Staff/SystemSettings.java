package Staff;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utilities.RootFinder;

/**
 * This is the SystemSettings class. It will hold the information about all the system settings which affect a movie's price
 *
 */
public class SystemSettings implements Serializable {
	// Attributes
	/**
	 * This holds all the system information in a hashmap of Map<String, Map<Object, Object>>
	 */
	private Map<String, Map<Object, Object>> systemInfoHash;	
	
	/**
	 * This holds the list of system information
	 */
	private List<String> systemInfoList;

	
	// Constructor
	/**
	 * This sets the two attributes of systemInfoList and systemInfoHash
	 */
	public SystemSettings() {
		this.setsystemInfoList();
		this.setSystemInfoHash();
	}	
	
	
	// Getters 
	/**
	 * This returns the systemInfoList, which is all the system settings that are available 
	 * @return List<String>
	 */
	public List<String> getsystemInfoList() {return this.systemInfoList;}
	
	/**
	 * This returns the actual system info hash, which is all the system settings with detailed information 
	 * @return Map<String, Map<Object, Object>>
	 */
	public Map<String, Map<Object, Object>> getSystemInfoHash() {return this.systemInfoHash;};
	
	/**
	 * This gets the price modifier for a certain key that will modify a ticket's base price
	 * @param key
	 * @return
	 */
	public double getPrice(String key) {
		return (double) this.getSystemInfoHash().get("priceReference").get(key);
	}
	
	/**
	 * This checks whether the date given is a holiday or not. 
	 * @param key The date 
	 * @return Boolean is holiday or not
	 */
	public boolean isHoliday(LocalDate key) {
		String temp = (String) this.getSystemInfoHash().get("holidayReference").get(key);
		if (temp==null) {
			return false;
		} else {
			return true;
		}
	}
	
	
	// CRUD methods
	/**
	 * This allows the staff to view the system settings by the information type (category)
	 * @param infoType
	 */
	public void viewSetting(String infoType) {
		System.out.println(this.getSystemInfoHash().get(infoType));
	}
	
	
	/**
	 * This allows the staff to add a setting into system settings. This can only add a new holiday since it is the only one that makes sense
	 * @param infoType At the moment, only allow "holidayReference". However note that "priceReference" is also coded to cater for future changes
	 * @param key The key of the new setting
	 * @param value The price modifier of the new setting if price. If holiday, it is the holiday name
	 */
	public void addSetting(String infoType, Object key, Object value) {
		if (infoType.equals("holidayReference")) {
			this.systemInfoHash.get(infoType).put((LocalDate) key, (String) value);
		} else {
			this.systemInfoHash.get(infoType).put((String) key, (double) value);
			this.systemInfoHash.get("priceReference").put((String) key, (double) value); // also update master list
		}
		
		System.out.println("Setting added");
	}
	
	/**
	 * This allows the staff to update the system settings that are available
	 * @param infoType The type of setting to update (category)
	 * @param key The key that we want to update
	 * @param value The new price modifier for that key
	 */
	public void updateSetting(String infoType, Object key, Object value) {
		if (infoType.equals("holidayReference")) {
			this.systemInfoHash.get(infoType).replace((LocalDate) key, (String) value);
		} else {
			this.systemInfoHash.get(infoType).replace((String) key, (double) value);
			this.systemInfoHash.get("priceReference").replace((String) key, (double) value); // also update master list
		}
		
		System.out.println("Setting updated");		
	}
	
	/**
	 * This allows the staff to delete a specific system setting. At the moment, only holidays makes sense. However we also coded for priceReference to cater to future changes
	 * @param infoType This is the type of information to be deleted (category)
	 * @param key This is the key that we want to delete
	 */
	public void deleteSetting(String infoType, Object key) {
		if (infoType.equals("holidayReference")) {
			this.systemInfoHash.get(infoType).remove((LocalDate) key);
		} else {
			this.systemInfoHash.get(infoType).remove((String) key);
			this.systemInfoHash.get("priceReference").remove((String) key); // also update master list
		}
		
		System.out.println("Setting removed");		
	}	
	

	
	
	
	
	
	
	// Initializers: Below code used only for the very first run of the app
	private void setsystemInfoList() {
		this.systemInfoList = new ArrayList<String>(List.of("priceReference", "holidayReference", "dayOfWeek$", "default$", "holiday$", "movieFormat$", "ticketType$", "cinemaType$"));	
	}
	
	private void setSystemInfoHash() {
		this.systemInfoHash = new HashMap<String, Map<Object, Object>>();
		for (String attributeName : this.getsystemInfoList()) {
			this.setSystemInfoItem(attributeName);
		}
 		
	}
	
	private void setSystemInfoItem(String attributeName) {
		try {
			// Get filepath
			String filePath = RootFinder.findRootPath();
			
			if (filePath == null) {
				throw new IOException("Cannot find root");
			} 
			
			this.systemInfoHash.put(attributeName, new HashMap<Object, Object>());
			
			switch (attributeName) {
				case "priceReference": 
					filePath = filePath + "/data/system_settings/price_reference.txt";
					break;
				case "holidayReference":
					filePath = filePath + "/data/system_settings/holiday_list.txt";
					break;
				case "dayOfWeek$":
					filePath = filePath + "/data/system_settings/day_of_week.txt";
					break;
				case "default$":
					filePath = filePath + "/data/system_settings/default_price.txt";
					break;
				case "holiday$":
					filePath = filePath + "/data/system_settings/holiday.txt";
					break;
				case "movieFormat$":
					filePath = filePath + "/data/system_settings/movie_format.txt";
					break;				
				case "ticketType$":
					filePath = filePath + "/data/system_settings/ticket_type.txt";
					break;			
				case "cinemaType$":
					filePath = filePath + "/data/system_settings/cinema_type.txt";
					break;							
			}
			
			
			// Open file and traverse it
			FileReader frStream = new FileReader( filePath );
			BufferedReader brStream = new BufferedReader( frStream );
			String inputLine;
			
			do {
				inputLine = brStream.readLine(); // read in a line
				if (inputLine == null) {break;} // end of file
				String inputLineSeparated[] = inputLine.split("\\|"); // escape the | character
	
				switch (attributeName) {
					case "holidayReference":
						LocalDate date = LocalDate.parse(inputLineSeparated[0].trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
						String holidayName = inputLineSeparated[1].trim();
						this.systemInfoHash.get(attributeName).put(date, holidayName);
						break;
					default:
						String key = inputLineSeparated[0].trim();
						Double value = Double.parseDouble(inputLineSeparated[1].trim());
						this.systemInfoHash.get(attributeName).put(key, value);						
						break;
				}
				
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