package Staff;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import utilities.RootFinder;

/**
 * This is the StaffManager. For now it is a simple manager that will handle the login issues of staff
 *
 */
public class StaffManager {
    // Attributes
    /**
     * single_instance tracks whether StaffManager has been instantiated before.
     */
    private static StaffManager single_instance = null;

    /**
     * Instantiates the StaffManager singleton. If no previous instance has been created,
     * one is created. Otherwise, the previous instance created is used.
     * @return an instance of StaffManager.
     */
    public static StaffManager getInstance()
    {
        if (single_instance == null)
            single_instance = new StaffManager();
        return single_instance;
    }   
    
    
    // Constructor
    /**
     * Constructor of StaffManager. Doesn't do much
     */
    private StaffManager() {
        
    }
    
    
    // Public exposed methods to app
    /**
     * Validation for the staff app. Only authorised staff can login and access staff functions.
     * We did not implement any form of password encryption or password hiding since that is not the focus of this project
     * Therefore passwords are simple and in plaintext. For an actual application used by a company, these would of course be encrypted
     * 
     * @param username This is the username that the staff will key in
     * @param password This is the corresponding password to that username 
     * @return This will tell use whether the login attempt was successful or not
     */
    public boolean login(String username, String password) {
        try {
            String filepath = RootFinder.findRootPath() + "/data/staffs/staff_accounts.txt";
          
            BufferedReader br = new BufferedReader(new FileReader(filepath));
            String line;
            String line2;
            do {
                line = br.readLine();
                line2 = br.readLine();
                if (line.equals(username)&&line2.equals(password))
                    return true;
            } while(line != null );
            br.close();
            return false;
        } catch ( FileNotFoundException e ) {
            System.out.println( "Error opening the input file!" + e.getMessage() );
            System.exit( 0 );
        } catch ( IOException e ) {
            System.out.println( "IO Error!" + e.getMessage() );
            e.printStackTrace();
            System.exit( 0 );
        }   
        
        return false;
    }
    
    
    /**
     * This will logout the staff and ensure that the next person who tries to enter the staff app also has to login. 
     * @return This returns a boolean flag to reset the login status and force the next person entering staffapp to login too
     */
    public boolean logout(){
        return false;
    }
}

