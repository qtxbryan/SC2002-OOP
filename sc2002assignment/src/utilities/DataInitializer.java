package utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import MovieEntities.Movie;

public class DataInitializer {
    
    /**
     * 
     * Loads all text files in folder and return list of files 
     * @param folderPath Folder path to be accessed 
     * @return Returns list of all files in folder
     * 
     */
    
    public File[] getAllFiles(String folderPath) {
        
        File directory = new File(folderPath);
        return (directory.listFiles());
        
    }
    
    /** 
     * 
     * Initialize movie data 
     * @param basePath path to find movie files 
     * @return List<Movies> return reference to movies 
     */
    
    public List<Movie> initializeMovie(String basePath){
        
        String folderPath = basePath + "/movies";
        File[] files = getAllFiles(folderPath);
        
        List<Movie> movies = new ArrayList<Movie>();
        
        for(int i =0; i < files.length; i++) {
            
            String filePath = files[i].getPath();
            
            try {
                
                FileReader frs = new FileReader(filePath);
                BufferedReader brs = new BufferedReader(frs);
                String input; 
                
                Movie newMovie = new Movie();
                
                //MovieID
                input = brs.readLine();
                newMovie.setMovieID(Integer.parseInt(input));
                
            }catch (FileNotFoundException e) {
                
                System.out.println("File not found! " + e.getMessage());
                System.exit(0);
                
            }catch (IOException e) {
                
                System.out.println("IO Problem! " + e.getMessage());
                e.printStackTrace();
                System.exit(0);
                
            }
        }
    }

}


class Reset {
    public static void main(String[] args) {

        // Data initializer 
        DataInitializer dataInitialiser = new DataInitializer();
        
        // Get project root
        String initialisationFolderPath = RootFinder.findRootPath() + "/data/initialisation";
        
        System.out.println("Initialised path is: " + initialisationFolderPath);
        
        System.out.println("Initialized!");

    }
}