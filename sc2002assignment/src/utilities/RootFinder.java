package utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class RootFinder {
    
    public static String findRootPath() {
        
        try {
            
            String rootFolder = "sc2002assignment";
            File file = new File(".");
            
            if(file.getCanonicalFile().getName().equals(rootFolder) == true) {
                
                return file.getCanonicalPath();
                
                
            }else {
                
                while(file.getName().equals(rootFolder) != true) {
                    
                    file = file.getCanonicalFile().getParentFile();
                    
                }
                
                String rootPath = file.getCanonicalPath();
                return rootPath; 
                
            }
        }catch (FileNotFoundException e) {
            
            System.out.println("Item not found! " + e.getMessage());
            System.exit(0);
            return null; 
            
        }catch (IOException e) {
            
            System.out.println("IO Error! " + e.getMessage());
            e.printStackTrace();
            System.exit(0);
            return null; 
            
        }
    }
}
