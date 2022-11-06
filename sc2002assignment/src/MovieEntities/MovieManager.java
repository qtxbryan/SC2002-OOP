package MovieEntities;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import utilities.RootFinder;
import utilities.Serializer;

public class MovieManager {

    /**
     * 
     * Map Integer movieID to movieObject
     */
    private Map <Integer, Movie> movies; 
    private Scanner sc = new Scanner(System.in);

    private static MovieManager single_instance = null; 
    
    
    /**
     * Instantiates the MovieManager singleton. If no previous instance has been created,
     * one is created. Otherwise, the previous instance created is used.
     * @return an instance of MovieManager.
     */
    
    public static MovieManager getInstance() {
        
        if(single_instance == null)
            single_instance = new MovieManager();
        
        return single_instance; 
    }
    
    private MovieManager() {
        
        this.movies = new HashMap<Integer, Movie>();
        this.load();
        
    }
    
    /**
     * 
     *  Loads Movie object from data files by iterating 
     */
    private void load() {
        
        File folder = new File(RootFinder.findRootPath() + "/data/movies");
        
        File[] listOfFiles = folder.listFiles();
        
        if(listOfFiles != null) {
            
            for(int i = 0; i < listOfFiles.length; i++) {
                
                String filePath = listOfFiles[i].getPath(); //returns full file path with file name and file extension
                Movie newMovie = (Movie) Serializer.deserializeObject(filePath); //load object from dat file 
                movies.put(newMovie.getMovieID(), newMovie); 
                
            }
        }
        
    }
    
    
    public void movieMenuStaff() {
        
        int choice; 
        
        do {
            
            System.out.println("=================== MOVIE MENU (STAFF) ==================\n" +
                    " 1. View/ Edit Movies                                   \n" +
                    " 2. Add Movies                                          \n" +
                    " 3. Search Movies (By Title)                            \n" +
                    " 0. Back to StaffApp......                             \n"+
                    "==========================================================");
            
            System.out.println("Enter choice: ");
            
        
            while(!sc.hasNextInt()) {
                
                System.out.println("Invalid input type. PLease choose a choice from 0 - 3");
                sc.next();
                
            }
            
            choice = sc.nextInt();
            
            switch(choice) {
                
                case 1: 
                    this.listMovies("Staff");
                    break; 
                
                case 2:
                    this.addMovies();
                    break; 
                case 3:
                    this.searchMovie("Staff");
                    break; 
                    
                case 0: 
                    System.out.println("Back to StaffApp.... ");
                    break; 
            }
        } while (choice != 0);
    }
    
    
    public void listMovies(String appType) {
        
        int choice; 
        
        if(appType.equals("Staff")) {
            
            do {
                
                System.out.println("=================== MOVIE MENU (STAFF) ==================\n" +
                        " 1. List all movies                                 \n" +
                        " 2. Coming Soon                                         \n" +
                        " 3. Preview                                         \n" +
                        " 4. Now Showing                                     \n" +
                        " 5. End of Showing                                     \n" +
                        " 0. Back to Staff Movie Menu......                     \n"+
                        "=========================================================");
                
                System.out.println("Enter choice: ");
                
                while(!sc.hasNextInt()){
                    
                    System.out.println("Invalid input type. Please choose a choice from 0 - 5");
                    sc.next();
                    
                }
                
                choice = sc.nextInt();
                
                switch(choice) {
                    
                    case 1:
                        List <Movie> allMovies = new ArrayList<Movie>();
                        
                }
                
            }while (choice != 0);
        }
    }
    
    
    public void addMovies() {
        
        Movie movie = new Movie();
        ArrayList<Genre> genreList = new ArrayList<>();
        ArrayList<String> castList = new ArrayList<>();
        ArrayList<MovieFormat> movieFormatList = new ArrayList<>();
        
        // Input movie title 
        System.out.println("Enter movie title: ");
        sc.nextLine();
        
        String title = sc.nextLine();
        movie.setTitle(title);
        
        
        // Input Genre 
        System.out.println("List of Genres: ");
        
        //list out all the genres
        for(int i = 0; i < Genre.values().length; i++){
            
            System.out.println(i + 1 + ". " + Genre.values()[i].toString());
            
        }
        
        System.out.println("Enter Number of Genres: ");
        
        while(!sc.hasNextInt()) {
            
            System.out.println("Invalid input. Please enter an integer number"); 
            sc.next();
           
        }
        
        int genNo = sc.nextInt();
        
        for(int i = 0; i <genNo; i++) {
            
            System.out.println("Pick genre: ");
            
            while(!sc.hasNextInt()) {
                
                System.out.println("Invalid input. Please enter an integer number");
                sc.next();
                
            }
            
            int choice = sc.nextInt()-1; 
            System.out.println("You picked: " + Genre.values()[choice].toString());
            genreList.add(Genre.values()[choice]);
        }
        
        // Input Director 
        System.out.println("Enter Director name: ");
        sc.nextLine();
        movie.setDirector(sc.nextLine());
        
        // Input casts 
        System.out.println("Enter number of casts members: ");
        
        while(!sc.hasNextInt()) {
            
            System.out.println("Invalid input! Please enter an integer number");
            sc.next();
            
        }
        
        int noOfCast = sc.nextInt();
        sc.nextLine();
        
        for (int i = 0; i < noOfCast; i++) {
            System.out.println("Enter Cast Member name: ");
            String castName = sc.nextLine();
            castList.add(castName);
            
        }
        
        movie.setCasts(castList);
        
        // Input Synopsis
        System.out.println("Enter synopsis");
        movie.setSynopsis(sc.nextLine());
        
        // Input movie rating
        
        
        
        
        
        
    }
    
    private void searchMovie(String appType) {
        
        
    }
    
    
    
    
}
