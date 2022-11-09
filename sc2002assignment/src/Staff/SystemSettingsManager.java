package Staff;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import utilities.RootFinder;
import utilities.Serializer;

public class SystemSettingsManager {
	// Attributes
	/**
	 * Holds the systemsettings object, that has a hashmap of all the available system settings 
	 * You can search out a system setting by providing its name
	 */
	private SystemSettings systemSettings;
	
	private Scanner sc = new Scanner(System.in);
	
	/**
     * single_instance tracks whether ShowtimeManager has been instantiated before.
     */
	private static SystemSettingsManager single_instance = null;
	
	/**
     * Instantiates the SystemSettingsManager singleton. If no previous instance has been created,
     * one is created. Otherwise, the previous instance created is used.
     * @return an instance of SystemSettingsManager.
     */
	public static SystemSettingsManager getInstance() {
		if (single_instance == null)
			single_instance = new SystemSettingsManager();
		return single_instance;
	}
	
	
	// Constructor
	/**
	 * Constructor of the SystemSettingsManager. It will first try to load a serialized file of the class SystemSettings  
	 * If it fails, it will create the system settings from the initialization files that we have preloaded and then serialize it for future use
	 */
	private SystemSettingsManager() {
		SystemSettings serializedObject = this.load();
		if (serializedObject != null) {
			this.systemSettings = serializedObject;
		} else {
			this.systemSettings = new SystemSettings();
			this.save();
			System.out.println("System Settings File created!");
		}
	}


	// Public exposed methods to app
	
	/**
	 * Gets the value of a certain ticket attribute such as BASE (base price) / BOOKING (booking price modifier) / MON (Monday price modifier) 
	 * @param key This is the keyword present in system settings
	 * @return	This returns a value by which the base ticket price should be modified
	 */
	public double getPrice(String key) {
		return this.systemSettings.getPrice(key);
	}
	
	
	/**
	 * Similar to getPrice(String key), except that this checks whether the specific date of the showtime is a holiday or not. 
	 * @param date This is the date of the showtime
	 * @return	This returns the value of the holiday price modifier if the date is a holiday, or returns 0 if it is not a holiday
	 */
	public double getPrice(LocalDate date) {
		if (this.systemSettings.isHoliday(date)) {
			return this.systemSettings.getPrice("HOLIDAY");
		} else {
			return 0;
		}
	}
	
	/**
	 * This is an option menu, the entry point into the System Settings App where a staff can come to CRUD the system settings
	 */
	public void displayMenu() {
		int choice;
		
		do {
	        System.out.println("=================== SystemSettings Menu ===================\n"+
	                " 1. View Setting                                         \n"+
	                " 2. Add Setting                                           \n"+
	                " 3. Change Setting                                        \n"+
	                " 4. Delete Setting                                        \n"+
	                " 0. Back to StaffApp                                      \n"+
							   "==========================================================");
			System.out.println("Enter choice: ");
			while(!sc.hasNextInt()) {
				System.out.println("Please enter a number!");
				sc.next();
			}
			choice = sc.nextInt();
			sc.nextLine();
			
			switch (choice) {
				case 1:
					this.viewSystemSetting();
					break;
				case 2:
					this.addSystemSetting();
					break;
				case 3: 
					this.changeSystemSetting();
					break;
				case 4:
					this.deleteSystemSetting();
					break;
				case 0:
					System.out.println("Back to StaffApp......");
					break;
				default:
					System.out.println("Invalid choice. Please choose between 0-4.");
					break;
			}

		} while (choice!=0);
		
		this.save();
	}
	
	
	// Private CRUD methods
	/**
	 * This is an option menu where a staff can come in to view the various system settings available to them.
	 * There are many categories of settings, and the staff can get a good overview of what they can change
	 */
	private void viewSystemSetting() {
		int choice;
		
		do {
	        System.out.println(	"=================== View SystemSettings ===================\n"+
	                		   	" 1. All Price References                                  \n"+
	                		   	" 2. Holiday References                                    \n"+
				                " 3. Default Prices                                        \n"+
				                " 4. Day-of-the-Week Prices                                \n"+
				                " 5. Holiday Prices                                        \n"+
				                " 6. Movie Format Prices                                   \n"+
				                " 7. Ticket Type Prices                                    \n"+
				                " 8. Cinema Type Prices                                    \n"+
				                " 0. Back to SystemSettings Menu                           \n"+
								"===========================================================");
			System.out.println("Enter choice:");
			while(!sc.hasNextInt()) {
				System.out.println("Please enter a number!");
				sc.next();
			}
			choice = sc.nextInt();
			sc.nextLine();
				
			switch (choice) {
				case 1:
					this.systemSettings.viewSetting("priceReference");
					break;
				case 2:
					this.systemSettings.viewSetting("holidayReference");
					break;
				case 3: 
					this.systemSettings.viewSetting("default$");
					break;
				case 4:
					this.systemSettings.viewSetting("dayOfWeek$");
					break;
				case 5:
					this.systemSettings.viewSetting("holiday$");
					break;
				case 6:
					this.systemSettings.viewSetting("movieFormat$");
					break;
				case 7:
					this.systemSettings.viewSetting("ticketType$");
					break;
				case 8:
					this.systemSettings.viewSetting("cinemaType$");
					break;
				case 0:
					System.out.println("Back to SystemSettings Menu......");
					break;
				default:
					System.out.println("Invalid choice. Please choose between 0-8.");
					break;
			}
		} while (choice!=0);
	}
	
	/**
	 * This is an option menu where a staff will come in to enter a new system setting. 
	 * The only system setting that actually makes sense to add will be a holiday reference, since new holidays can be 
	 * easily declared, but new movie formats or new cinema types are very rare, and it's not possible to create a new day of the week
	 */
	private void addSystemSetting() {
		int choice;
		
		do {
	        System.out.println(	"==================== Add SystemSettings ===================\n"+
			        		   	" 1. New Holiday Reference                                 \n"+
				                " 0. Back to SystemSettings Menu                           \n"+
								"===========================================================");
			System.out.println("Enter choice: ");
			while(!sc.hasNextInt()) {
				System.out.println("Please enter a number!");
				sc.next();
			}
			choice = sc.nextInt();
			sc.nextLine();
				
			switch (choice) {
				case 1:
					LocalDate newHolidayDate;
					String newHolidayName;
					
					System.out.println("Enter date of holiday in format YYYY-MM-DD: ");
					newHolidayDate = this.dateParser(sc.nextLine());
					while (newHolidayDate == null) {
						System.out.println("Enter date of holiday in format YYYY-MM-DD: ");
						newHolidayDate = this.dateParser(sc.nextLine());
					}
					
					System.out.println("Enter name of holiday: ");
					newHolidayName = sc.next().toUpperCase();
					
					this.systemSettings.addSetting("holidayReference", newHolidayDate, newHolidayName);
					this.systemSettings.viewSetting("holidayReference");
					break;
				case 0:
					System.out.println("Back to SystemSettings Menu......");
					break;
				default:
					System.out.println("Invalid choice. Please choose between 0-1.");
					break;
			}
		} while (choice!=0);
	}
	
	
	/**
	 * This is an option menu, where the staff will decide what kind of system settings they would like to change. 
	 * It is sorted by categories and hence the staff can easily pick what kind of settings they want to change
	 */
	private void changeSystemSetting() {
		int choice;
		
		do {
	        System.out.println(	"=================== Change SystemSettings ==================\n"+
			        		   	" 1. Holiday Reference                                     \n"+
			        		   	" 2. Movie Format Prices                                   \n"+
				                " 3. Ticket Type Prices                                    \n"+
				                " 4. Cinema Type Prices                                    \n"+
				                " 5. Day-of-the-Week Prices                                \n"+
				                " 6. Holiday Prices                                        \n"+
				                " 7. Default Prices                                        \n"+
				                " 0. Back to SystemSettings Menu                           \n"+
								"============================================================");
			System.out.println("Enter choice: ");
			while(!sc.hasNextInt()) {
				System.out.println("Please enter a number!");
				sc.next();
			}
			choice = sc.nextInt();
			sc.nextLine();
				
			switch (choice) {
				case 1:
					this.systemSettings.viewSetting("holidayReference");
					LocalDate newHolidayDate;
					String newHolidayName;
					
					System.out.println("Enter date of holiday you want to change in format YYYY-MM-DD: ");
					newHolidayDate = this.dateParser(sc.next());
					while (newHolidayDate == null) {
						System.out.println("Enter date of holiday in format YYYY-MM-DD: ");
						newHolidayDate = this.dateParser(sc.nextLine());
					}
					System.out.println("Enter new name of holiday: ");
					newHolidayName = sc.next().toUpperCase();
					
					this.systemSettings.updateSetting("holidayReference", newHolidayDate, newHolidayName);
					this.systemSettings.viewSetting("holidayReference");
					break;
				case 2:
					this.systemSettings.viewSetting("movieFormat$");
					String newMovieFormatName;
					double newMovieFormatModifier;
					
					System.out.println("Enter name of movie format you want to change: ");
					newMovieFormatName = sc.next().toUpperCase();
					
					System.out.println("Enter new price modifier: ");
					while(!sc.hasNextDouble()) {
						System.out.println("Please enter a number!");
						sc.next();
					}
					newMovieFormatModifier = sc.nextDouble();
					
					this.systemSettings.updateSetting("movieFormat$", newMovieFormatName, newMovieFormatModifier);
					this.systemSettings.viewSetting("movieFormat$");
					break;
				case 3: 
					this.systemSettings.viewSetting("ticketType$");
					String newTicketTypeName;
					double newTicketTypeModifier;
					
					System.out.println("Enter name of ticket type you want to change: ");
					newTicketTypeName = sc.next().toUpperCase();
					
					System.out.println("Enter new price modifier: ");
					while(!sc.hasNextDouble()) {
						System.out.println("Please enter a number!");
						sc.next();
					}
					newTicketTypeModifier = sc.nextDouble();
					
					this.systemSettings.updateSetting("ticketType$", newTicketTypeName, newTicketTypeModifier);
					this.systemSettings.viewSetting("ticketType$");
					break;
				case 4:
					this.systemSettings.viewSetting("cinemaType$");
					String newCinemaTypeName;
					double newCinemaTypeModifier;
					
					System.out.println("Enter name of cinema type you want to change: ");
					newCinemaTypeName = sc.next().toUpperCase();
					
					System.out.println("Enter new price modifier: ");
					while(!sc.hasNextDouble()) {
						System.out.println("Please enter a number!");
						sc.next();
					}
					newCinemaTypeModifier = sc.nextDouble();
					
					this.systemSettings.updateSetting("cinemaType$", newCinemaTypeName, newCinemaTypeModifier);	
					this.systemSettings.viewSetting("cinemaType$");
					break;
				case 5:
					this.systemSettings.viewSetting("dayOfWeek$");
					String newDayOfWeekName;
					double newDayOfWeekModifier;
					
					System.out.println("Enter name of day of the week you want to change: ");
					newDayOfWeekName = sc.next().toUpperCase();
					
					System.out.println("Enter new price modifier: ");
					while(!sc.hasNextDouble()) {
						System.out.println("Please enter a number!");
						sc.next();
					}
					newDayOfWeekModifier = sc.nextDouble();
					
					this.systemSettings.updateSetting("dayOfWeek$", newDayOfWeekName, newDayOfWeekModifier);	
					this.systemSettings.viewSetting("dayOfWeek$");
					break;			
				case 6:
					this.systemSettings.viewSetting("holiday$");
					double newHolidayModifier;
					
					System.out.println("Enter new price modifier for holidays: ");
					while(!sc.hasNextDouble()) {
						System.out.println("Please enter a number!");
						sc.next();
					}
					newHolidayModifier = sc.nextDouble();
					
					this.systemSettings.updateSetting("holiday$", "HOLIDAY", newHolidayModifier);	
					this.systemSettings.viewSetting("holiday$");
					break;		
				case 7:
					this.systemSettings.viewSetting("default$");
					String newDefaultName;
					double newDefaultModifier;
					
					System.out.println("Enter the default setting you want to change: ");
					newDefaultName = sc.next().toUpperCase();
					
					System.out.println("Enter new default price: ");
					while(!sc.hasNextDouble()) {
						System.out.println("Please enter a number!");
						sc.next();
					}
					newDefaultModifier = sc.nextDouble();
					
					this.systemSettings.updateSetting("default$", newDefaultName, newDefaultModifier);	
					this.systemSettings.viewSetting("default$");
					break;			
				case 0: 
					break;
				default:
					System.out.println("Invalid choice. Please choose between 0-7.");
					break;
			}
		} while (choice!=0);
		
		System.out.println("Back to SystemSettings Menu......");
	}
		
	/**
	 * This is where a staff can come in to delete a system settings. Similar to adding a system setting, the only
	 * setting that makes sense to be added is a new holiday reference. Any new movie format or cinema will be a structural change
	 * to the entire cinema, and not something that needs to be accounted for in the MOBLIMA app
	 */
	private void deleteSystemSetting() {
		int choice;
		
		do {
	        System.out.println(	"=================== Delete SystemSettings ==================\n"+
			        		   	" 1. Holiday Reference                                     \n"+
				                " 0. Back to SystemSettings Menu                           \n"+
								"============================================================");
	        System.out.println("Enter choice:");
			while(!sc.hasNextInt()) {
				System.out.println("Please enter a number!");
				sc.next();
			}
			choice = sc.nextInt();
			sc.nextLine();
				
			switch (choice) {
				case 1:
					this.systemSettings.viewSetting("holidayReference");
					
					if (this.systemSettings.getSystemInfoHash().get("holidayReference").size()==0) {
						System.out.println("No data!");
						break;
					} else {				
						LocalDate newHolidayDate;
						
						System.out.println("Enter date of holiday you want to delete in format YYYY-MM-DD: ");
						newHolidayDate = this.dateParser(sc.next());
						while (newHolidayDate == null) {
							System.out.println("Enter date of holiday in format YYYY-MM-DD: ");
							newHolidayDate = this.dateParser(sc.nextLine());
						}
						this.systemSettings.deleteSetting("holidayReference", newHolidayDate);
						this.systemSettings.viewSetting("holidayReference");
						break;
					}
				case 0: 
					break;
				default:
					System.out.println("Invalid choice. Please choose between 0-1.");
					break;
			}
		} while (choice!=0);
		
		System.out.println("Back to SystemSettings Menu......");
	}

	
	// Private Serialization and Deserialization
	/**
	 * This is used to save the entire new system settings object
	 */
	private void save() {
		String filePath = RootFinder.findRootPath() + "/data/system_settings/system_settings.dat";
		Serializer.serializeObject(this.systemSettings, filePath);
	}
	
	/**
	 * This is used to load all of the system settings from the "database". If it fails to load, it will return null. 
	 * The constructor will then create the system settings data file. This will only occur upon initialisation (the very first run of the app)
	 * Or when data is cleared
	 * @return This returns all the SystemSettings available as the SystemSettings class
	 */
	private SystemSettings load() {
		String filePath = RootFinder.findRootPath() + "/data/system_settings/system_settings.dat";
		return (SystemSettings) Serializer.deserializeObject(filePath);
	}

	/**
	 * Helper function to parse date.
	 * @param dateString date in string
	 * @return date object.
	 */
	private LocalDate dateParser(String dateString) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate date = LocalDate.parse(dateString, formatter);
			return date;
		}
		catch (DateTimeParseException dtpe) {
			System.out.println("Wrong date format entered!");
			return null;
		}
	}
}
