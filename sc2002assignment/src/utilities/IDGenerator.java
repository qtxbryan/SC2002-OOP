package utilities;

public class IDGenerator {

    public static String getCurrentID(String fileName) {
        
        String currentID = String.format("%08d", 00000000);
        
        try {
            
            String path = RootFinder.findRootPath();
            
            if(path == null) {
                
                throw new IOException("Cannot find project root");
                
            }else {
                
                path = path + ""
            }
        }
        
    }
}
