package MovieEntities;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
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
    
    
    /**
     * 
     * Serialize and save movie object into dat file 
     * @param movie The Movie object to be saved in dat file 
     */
    private void save(Movie movie) {
        
        String path = RootFinder.findRootPath() + "/data/movies/movie"+movie.getMovieID()+".dat";
        Serializer.serializeObject(movie, path);
        System.out.println("Movie saved lo!");
        
    }
    
    /**
     * 
     * Prints Staff main menu and allow many actions like view, edit, add and search movies 
     */
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
    
    /**
     * 
     *  Staff only: creates new Movie object 
     */
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
        System.out.println("Pick Movie Rating: ");
        
        for(int i = 0; i < MovieRating.values().length; i++) {
            
            System.out.println(i + 1 + ". " + MovieRating.values()[i].toString());
            
        }
        
        while(!sc.hasNextInt()) {
            
            System.out.println("Invalid input. Please enter a integer value");
            sc.next();
        }
        
        int movieRating = sc.nextInt() - 1; 
        System.out.println("You picked " + MovieRating.values()[movieRating].toString());
        movie.setMovieRating(MovieRating.values()[movieRating]);
        
        
        // Input Movie Format
        
        System.out.println("List of movie formats: ");
        for(int i = 0; i < MovieFormat.values().length; i++) {
            
            System.out.println(i + 1 + ". " + MovieFormat.values()[i].toString());
            
        }
        
        System.out.println("Enter number of movie formats: ");
        while(!sc.hasNextInt()) {
            
            System.out.println("Invalid input. Please enter an integer value");
            sc.next();
            
        }
        
        int movieFormat = sc.nextInt();
        
        for(int i = 0; i <movieFormat; i++) {
            
            System.out.println("Pick Movie Format: ");
            
            while(!sc.hasNextInt()) {
                
                System.out.println("Invalid input. PLease enter an integer value");
                sc.next();
            }
            
            int choice = sc.nextInt() - 1;
            System.out.println("You picked " + MovieFormat.values()[choice].toString());
            movieFormatList.add(MovieFormat.values()[choice]);
        }
        
        movie.setMovieFormat(movieFormatList);
        
        // Input Movie Duration 
        System.out.println("Enter Movie Duration");
        
        while(!sc.hasNextInt()) {
            
            System.out.println("Invalid input. Please enter an integer value");
            sc.next();
            
        }
        
        movie.setDuration(sc.nextInt());
        
        // Input showing status 
        
        System.out.println("Pick showing status: ");
        for(int i = 0; i < ShowingStatus.values().length; i++) {
            
            System.out.println(i + 1 +  ". " + ShowingStatus.values()[i].toString());
            
        }
        
        while(!sc.hasNextInt()) {
            
            System.out.println("Invalid input. Please enter an integer value");
            sc.next();
        }
        
        int showingStatus = sc.nextInt() -1; 
        System.out.println("You picked " + ShowingStatus.values()[showingStatus].toString());
        
        movie.setStatus(ShowingStatus.values()[showingStatus]);
        
        // Input Release date 
        System.out.println("Enter release date: ");
        String releaseDate = sc.next();
        
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse(releaseDate, dateFormat);
        movie.setReleaseDate(date);
        
        
        int choice; 
        
        do {
            
            System.out.println("Movie: ");
            System.out.println("Movie Title: " + movie.getTitle());
            System.out.println("Genres: ");
            for(int i = 0; i < movie.getGenres().size(); i++) {
                
                System.out.print(movie.getGenres().get(i).toString());
                
                if(i + 1 < movie.getGenres().size()) {
                    
                    System.out.print(", ");
                }
            }
            
            System.out.println();
            System.out.println("Rating " + movie.getMovieRating());
            System.out.println("Duration " + movie.getDuration());
            System.out.println("Movie Format: ");
            
            for(int i = 0; i < movie.getMovieFormat().size(); i++) {
                
                System.out.print(movie.getMovieFormat().get(i).toString());
                
                if(i + 1 < movie.getMovieFormat().size()) {
                    
                    System.out.print(", ");
                    
                }
            }
            
            System.out.println();
            System.out.println("Showing Status: " + movie.getStatus());
            System.out.println("Synopsis: " + movie.getSynopsis());
            System.out.println("Cast: ");
            
            for(int i = 0; i < movie.getCasts().size(); i++) {
                
                System.out.print(movie.getCasts().get(i).toString());
                
                if(i + 1 < movie.getCasts().size()) {
                    
                    System.out.print(", ");
                }
            }
            
            System.out.println();
            System.out.println("Director: " + movie.getDirector());
            
            System.out.println();
            
            System.out.println( "========================= ADD MOVIE ====================\n" +
                    " 1. Submit movie                                      \n" +
                    " 2. Edit movie                                        \n" +
                    " 0. Discard movie, back to Movie Menu                 \n"+
                    "========================================================");
            
            System.out.println("Enter choice: ");
            
            while(!sc.hasNextInt()) {
                
                System.out.println("Invalid tpye. Please enter an integer value between 0 - 2");
                sc.next();
                
            }
            
            choice = sc.nextInt();
            
            
            switch(choice) {
                
                case 1: 
                    break; 
                    
                case 2: 
                    break; 
                    
                case 0: 
                    System.out.println("Done ... Back to main menu");
                    break;
                    
                 default:
                     System.out.println("Invalid! Please enter an integer value between 0 - 2");
                     break; 
                     
                    
            }
            
        }while (choice != 0);
        
        
        
        
    }
    
    /**
     * 
     * Print menu for editing movie details for Staff and the save it in dat file  
     * @param movie 
     */
    private void editMovie(Movie movie) {
        
        int choice; 
        
        do {
            
            System.out.println("Showing selected movie details: ");
            movie.displayMovieDetails();
            
            System.out.println("=================== EDIT MOVIES (STAFF) ==================\n" +
                    " 1. Edit Title                                           \n" +
                    " 2. Edit Genres                                          \n" +
                    " 3. Edit Director                                       \n" +
                    " 4. Edit Cast                                           \n" +
                    " 5. Edit Synopsis                                       \n" +
                    " 6. Edit Rating                                         \n" +
                    " 7. Edit Formats                                        \n" +
                    " 8. Edit Duration                                       \n" +
                    " 9. Edit Showing Status                                 \n" +
                    " 10. Edit Release Date                                  \n" +
                    " 0. Finish Editing Movie                                \n"+
                    "=========================================================");
            
            System.out.println("Enter choice: ");
            
            while(!sc.hasNextInt()) {
                
                System.out.println("Invalid type. Please enter an integer between 0 - 10");
                sc.next();
                
            }
            
            choice = sc.nextInt();
            sc.nextLine();
            
            switch(choice) {
                
                case 1: 
                    System.out.println("Enter new movie title: ");
                    String movieTitle = sc.nextLine();
                    movie.setTitle(movieTitle);
                    break; 
                    
                case 2: 
                    System.out.println("List of Genres: "); 
                    for(int i = 0; i < Genre.values().length; i++) {
                        
                        System.out.println(i + 1 + ". " + Genre.values()[i].toString());
                        
                    }
                    
                    System.out.println("Enter number of Genres: ");
                    
                    while(!sc.hasNextInt()) {
                        
                        System.out.println("Invalid type. Please enter an integer value");
                        sc.next();
                    }
                    
                    int noOfGenres = sc.nextInt();
                    ArrayList<Genre> genreList = new ArrayList<>();
                    
                    for(int i = 0; i < noOfGenres; i++) {
                        
                        System.out.println("Pick genre: ");
                        
                        while(!sc.hasNextInt()) {
                            
                            System.out.println("Invalid type. Please enter an integer value");
                            sc.next();
                            
                        }
                        
                        int genre = sc.nextInt() - 1; 
                        System.out.println("You picked: " + Genre.values()[genre].toString());
                        genreList.add(Genre.values()[genre]);
                        
                    }
                    
                    break; 
                    
                case 3:
                    System.out.println("Enter new director name: ");
                    movie.setDirector(sc.nextLine());
                    break; 
                case 4: 
                    
                    System.out.println("Enter number of cast members: ");
                    
                    while(!sc.hasNextInt()) {
                        
                        System.out.println("Invalid type. Please enter integer value");
                        sc.next();
                        
                    }
                    
                    int numOfCasts = sc.nextInt();
                    sc.nextLine();
                    
                    ArrayList<String> castList = new ArrayList<>();
                    
                    for(int i = 0; i < numOfCasts; i++) {
                      
                        System.out.println("Enter cast member name: ");
                        String castName = sc.nextLine();
                        castList.add(castName);
                        
                    }
                    
                    movie.setCasts(castList);
                    break; 
                    
                case 5: 
                    System.out.println("Enter new sypnosis: ");
                    movie.setSynopsis(sc.nextLine());
                    break; 
                    
                case 6: 
                    System.out.println("Enter new movie rating: ");
                    
                    for(int i = 0; i < MovieRating.values().length; i++) {
                        
                        System.out.println(i + 1 + ". " + MovieRating.values()[i].toString());

                    }
                    
                    while(!sc.hasNextInt()) {
                        
                        System.out.println("Invalid type. Please enter an integer value");
                        sc.next();

                    }
                    
                    int movieRating = sc.nextInt() - 1; 
                    System.out.println("You picked: " + MovieRating.values()[movieRating].toString());
                    movie.setMovieRating(MovieRating.values()[movieRating]);
                    break; 
                    
                case 7: 
                    
                    ArrayList<MovieFormat> movieFormatList = new ArrayList<>();
                    System.out.println("List of Movie Formats: ");
                    
                    for(int i = 0; i < MovieFormat.values().length; i++) {
                        
                        System.out.println(i + 1 + ". " + MovieFormat.values()[i].toString());
                        
                    }
                    
                    while (!sc.hasNextInt()) {
                        
                        System.out.println("Invald type. Please enter an integer value");
                        sc.next();
                    }
                    
                    int formatSize = sc.nextInt();
                    
                    for(int i = 0; i < formatSize; i++) {
                        
                        System.out.println("Enter new movie format: ");
                        
                        while(!sc.hasNextInt()) {
                            
                            System.out.println("Invalid type. Please enter an integer value");
                            sc.next();
                            
                        }
                        
                        int format = sc.nextInt() - 1; 
                        
                        System.out.println("You picked: " + MovieFormat.values()[format].toString());
                        movieFormatList.add(MovieFormat.values()[format]);
                        
                    }
                    
                    movie.setMovieFormat(movieFormatList);
                    break; 
                    
                case 8: 
                    
                    System.out.println("Enter new duration: ");
                    
                    while(!sc.hasNextInt()) {
                        

                        System.out.println("Invald type. Please enter an integer value");
                        sc.next();
                        
                    }
                    int duration = sc.nextInt();
                    movie.setDuration(duration);
                    break; 
                    
                case 9: 
                    
                    System.out.println("Enter showing status: ");
                    for(int i = 0; i < ShowingStatus.values().length; i++) {
                        
                        System.out.println(i + 1 + ". " + ShowingStatus.values().toString());
                        
                    }
                    
                    while(!sc.hasNextInt()) {

                        System.out.println("Invald type. Please enter an integer value");
                        sc.next();
                    }
                    
                    int status = sc.nextInt() - 1; 
                    System.out.println("You picked " + ShowingStatus.values()[status].toString());
                    movie.setStatus(ShowingStatus.values()[status]);
                    break; 
                
                case 10: 
                    System.out.println("Enter new release date: ");
                    String newDate = sc.next();
                    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    
                    LocalDate date = LocalDate.parse(newDate, dateFormat);
                    movie.setReleaseDate(date);
                    break; 
                    
                case 0:
                    System.out.println("Edit Finish");
                    break; 
                
                    default: 
                        System.out.println("Please enter an value from 0 - 10");
                        break; 
            }
        }while (choice != 0);
        
        this.save(movie);
        
    }
    
    
    private void searchMovie(String appType) {
        
        System.out.println("Enter a movie to search: ");
        sc.nextLine();
        
        String movieTitle = sc.nextLine();
       
        List<Movie> moviesFound = new ArrayList<>();
        String upper = movieTitle.toUpperCase();
        
        for(Movie movie: movies.values()) {
            
            if(movie.getTitle().toUpperCase().contains(upper)) {
                
                moviesFound.add(movie);
            }
            
        }
        
        if(moviesFound.size() == 0) {
            
            System.out.println("Movie not found! ");
            return; 
        }
        
        
    }

    // saves a movie object and serializes it
    // movie: The movie object to be saved
     
    private void save(Movie movie) 
    {
        String filepath = RootFinder.findRootPath() + "/data/movies/movie_"+movie.getMovieID()+".dat";
        Serializer.serializeObject(movie, filepath);
        System.out.println("Movies Saved!");
    }
    
    /**
     * Remove movie by changing status to END_OF_SHOWING
     * @param movie
     */
    private void removeMovie(Movie movie) {
        
        movie.setStatus(ShowingStatus.End_Of_Showing);
        this.save(movie);
    }
    
    /**
     * 
     * Staff only: View top 5 movies by sales
     */
    private void viewTop5Sales() {
        
        
        ArrayList<Movie> top5 = new ArrayList<Movie>();
        
        for(Map.Entry<Integer, Movie> entry : movies.entrySet()) {
            
            if(entry.getValue().getStatus().equals("Preview") || entry.getValue().getStatus().equals("Now_Showing")) {
                
                top5.add(entry.getValue());
                
            }
        }
        
        top5.sort(Comparator.comparingDouble(Movie::getProfit).reversed());
        
        if(top5.size() == 0) {
            
            System.out.println("Movies not available");
            return;
            
        }else {
            for (int i = 0; i < Math.min(5, top5.size()); i++) {
                
                System.out.println(i + 1 + ". " + top5.get(i).getTitle() + " (Sales: " + top5.get(i).getProfit()+ ")");
                
            }
            
        }
    }
    
    /**
     * 
     * Staff only: View top 5 movies 
     */
    private void viewTop5Review() {
        
        ArrayList<Movie> top5 = new ArrayList<Movie>();
        
        for(Map.Entry<Integer, Movie> entry: movies.entrySet()) {
            
            if(entry.getValue().getStatus().equals("Preview") || entry.getValue().getStatus().equals("Now_Showing")) {
                
                top5.add(entry.getValue());
                
            }
        }
        
        
    }

    /**
     * Updates number of Tickets sold of a specific movie when a ticket for the movie is bought.
     * @param movieID MovieID of the Movie.
     * @param ticketsSold Number of tickets sold for the Movie.
     */
    void updateTicketsSold(String movieID, long ticketsSold){
        movies.get(movieID).setTicketsSold(movies.get(movieID).getTicketsSold() + ticketsSold);
    }

   
     // adds review to list of reviews that a movie has and updates the movie's total number of reviews
     // total review score and average review score
     // reviewScore: Score of the review to be added
     // function: Specifies whether to add or remove a review from the review list
     
    public void updateReview(int movieID, String reviewID, double reviewScore, String function)
    {
        if (function.equals("add")) 
        {
        	Movie movie = movies.get(movieID);
            movie.setTotalReviewNo(movie.getTotalReviewNo()+1);
            movie.setTotalReviewScore(movie.getTotalReviewScore()+reviewScore);
            movie.addMovieReview(reviewID);
            movie.setAverageReviewScore(movie.getTotalReviewScore()/movie.getTotalReviewNo());
            this.save(movie);
        } 
        
        else if (function.equals("remove")) 
        {
        	Movie movie = movies.get(movieID);
            movie.setTotalReviewNo(movie.getTotalReviewNo()-1);
            movie.setTotalReviewScore(movie.getTotalReviewScore()-reviewScore);
            movie.removeMovieReview(reviewID);
            movie.setAverageReviewScore(movie.getTotalReviewScore()/movie.getTotalReviewNo());
            this.save(movie);
        }
    }
}
