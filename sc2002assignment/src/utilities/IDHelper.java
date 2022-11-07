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
	    	
		String id = String.format("%08d", 00000000);
		
		try {
			// get filepath
			String filePath = RootFinder.findRootPath();
			
			if (filePath == null) {
				throw new IOException("Cannot find root of project.");
				
			} else {
				filePath = filePath + "/data/id/" + fileName + "ID.txt";
			}
									
			FileReader frs = new FileReader( filePath );
			BufferedReader brs = new BufferedReader( frs );
			String input;
	
			input = brs.readLine(); // read in a line
			if (input == null) {
			    id = String.format("%08d", 00000000);
			}
			else {
			    id = input;
			}
			
			brs.close(); // close file
			
			// open file in write mode
			FileWriter fws = new FileWriter(filePath, false); // overwrite file
		    BufferedWriter bws = new BufferedWriter(fws);
		    
		    String newLatestID = String.format("%08d", Integer.valueOf(id) + 1);
		    
		    bws.write(newLatestID);
			
		    bws.close(); // close file
		    
		    return id;
			
		} catch ( FileNotFoundException e ) {
			System.out.println( "File not found! " + e.getMessage() );
			System.exit( 0 );
		} catch ( IOException e ) {
			System.out.println( "IO Error! " + e.getMessage() );
			e.printStackTrace();
			System.exit( 0 );
		}
		
		return id;           
	}

}
