package staff;


// import helper class
import utilities.RootFinder;


import java.util.HashMap;
import java.util.Map;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;



public class SystemSettings implements Serializable {
    
    private double standardTicketPrice;
    private double seniorTicketPrice;
    private double studentTicketPrice;
    private List<DateTime> holidays;
    



    private Map<String, Map<Object, Object>> systemInfoHash;	
	private List<String> systemInfoList;


    //constructor 

    public SystemSettings() {
		this.setsystemInfoList();
		this.setSystemInfoHash();
	}	
	

    public void printSystemSettingsPortal() {

    }


    public void viewSetting(String infoType) {
		System.out.println(this.getSystemInfoHash().get(infoType));
	}



    // private int editShowTime(List<Movie> MovieList){
    //     /*Declare  */
    //     int findmovieID;
    //     System.out.println("Enter movie ID number to edit");
    //     Scanner sc = new Scanner(System.in);
    //     findmovieID = sc.nextInt();
 
    //     for(int i= 0; i< MovieList.length() ; i++){
    //         if(MovieList.get(i).movieID == findmovieID){

    //             System.out.println("Showtimes for this movie");
    //             for( int j = 0; j< MovieList.get(i).showTimes.length() ; j++){
                    
    //                 System.out.println( MovieList.get(i).showTimes.(j).showTimeID + ": " + MovieList.get(i).showTimes.(j).LocalDate)


    //                 // if showtime == showtime
    //             // edit.... 
    //             // note : need change showTime attributes using getters & setters 
    //             // bc its private
    //             // update txt file
    //             }   
    // }
    //         }
    //     }
    // private int removeShowtime(List<Movie> MovieList){
    //     /* */
    // }
    


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

}











// catch(FileNotFoundException e){
//     System.out.println(" Error, the file was not found"+ e.getMessage());
//     e.printStackTrace();
//     System.exit(0);
// }

// catch( IOException e){
//     System.out.println("IO Error" + e.getMessage() );
//     e.printStackTrace();
//     System.exit(0);
// }
