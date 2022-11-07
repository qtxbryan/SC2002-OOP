package utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


// generates latest ID available so there is no overlap of IDs
 
public class IDHelper {
	public static String getLatestID(String fileName) {
	    	
		String latestID = String.format("%08d", 00000000);
		
		try {
			// get filepath
			String filePath = RootFinder.findRootPath();
			
			if (filePath == null) {
				throw new IOException("Cannot find root of project.");
			} else {
				filePath = filePath + "/data/ids/" + fileName + "_id.txt";
			}
			
			// open file and traverse it						
			FileReader frStream = new FileReader( filePath );
			BufferedReader brStream = new BufferedReader( frStream );
			String inputLine;
	
			inputLine = brStream.readLine(); // read in a line
			if (inputLine == null) {
				latestID = String.format("%08d", 00000000);
			}
			else {
				latestID = inputLine;
			}
			
			brStream.close(); // close file
			
			// open file in write mode
			FileWriter fwStream = new FileWriter(filePath, false); // overwrite file
		    BufferedWriter bwStream = new BufferedWriter(fwStream);
		    
		    String newLatestID = String.format("%08d", Integer.valueOf(latestID) + 1);
		    
		    bwStream.write(newLatestID);
			
		    bwStream.close(); // close file
		    
		    return latestID;
			
		} catch ( FileNotFoundException e ) {
			System.out.println( "File not found! " + e.getMessage() );
			System.exit( 0 );
		} catch ( IOException e ) {
			System.out.println( "IO Error! " + e.getMessage() );
			e.printStackTrace();
			System.exit( 0 );
		}
		
		return latestID;           
	}

}
