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

import review.ReviewManager;

import utilities.IDHelper;
import utilities.RootFinder;
import utilities.Serializer;

public class MovieManager {

    // maps movieID to movieObject
    
    private Map <String, Movie> movies; 
    private Scanner sc = new Scanner(System.in);

    private static MovieManager single_instance = null; 
    
    // instantiates the MovieManager singleton. If no previous instance was created, one is created. Else, the previous instance is used.
    // returns an instance of MovieManager
     
    
    public static MovieManager getInstance() {
        
        if(single_instance == null)
            single_instance = new MovieManager();
        
        return single_instance; 
    }
    
    private MovieManager() {
        
        this.movies = new HashMap<String, Movie>();
        this.load();
        
    }
    
    public Movie getMovieByID(String movieID) {
        
        return movies.get(movieID);
        
    }
    
    
    
    // loads Movie object from data files by iterating 
     
    private void load() {
        
        File folder = new File(RootFinder.findRootPath() + "/data/movies");
        
        File[] listOfFiles = folder.listFiles();
        
        if(listOfFiles != null) {
            
            for(int i = 0; i < listOfFiles.length; i++) {
                
                String filePath = listOfFiles[i].getPath(); //returns full file path with file name and file extension
                
                System.out.println("Test code: File path: " + filePath);
                
                Movie newMovie = (Movie) Serializer.deserializeObject(filePath); //load object from dat file 
                movies.put(newMovie.getMovieID(), newMovie); 
                
                System.out.println("Test code: Movie Name" + newMovie.getStatus());
            }
        }
        
    }
    
     
    // serialize and save Movie object into dat file 
    // movie: the Movie object to be saved in dat file 
     
    private void save(Movie movie) {
        
        String path = RootFinder.findRootPath() + "/data/movies/movie"+movie.getMovieID()+".dat";
        
        Serializer.serializeObject(movie, path);
        System.out.println("Movie saved lo!");
    
    }
    
    // display menu for user containing movie details such as showtimes and reviews
    
    private void subMenu(Movie movie, String appType) {
        
        if(appType.equals("Staff")) {
            
            int choice; 
            
            do {
              
                System.out.println( "====================== MOVIE CHOICES =====================\n" +
                        " 1. Display/Edit Showtimes                              \n" +
                        " 2. Edit Movie                                          \n" +
                        " 3. Remove Movie                                        \n" +
                        " 4. View Reviews                                        \n" +
                        " 5. Delete Reviews                                      \n" +
                        " 0. Back to Movie Listings                              \n" +
                        "==========================================================");

                System.out.println("Enter choice: ");
                
                while(!sc.hasNextInt()) {
                    
                    System.out.println("Invalid type. Please select from 0 - 5");
                    sc.next();
                    
                }
                
                
                choice = sc.nextInt();
                
                switch(choice) {
                    
                    case 1:
                        break; 
                    
                    case 2: 
                        this.editMovie(movie);
                        break; 
                        
                    case 3: 
                        this.removeMovie(movie);
                        break; 
                        
                    case 4:
                        ReviewManager.getInstance().listMovieReview(movie.getReviews());
                        break; 
                    
                    case 5:
                        ReviewManager.getInstance().deleteMovieReview(movie.getReviews());
                        break; 
                        
                    case 0:
                        System.out.println("Back to movie listings..");
                        break;
                        
                    default:
                        System.out.println("Please select number from 0 -5 ");
                        break; 
                }
            }while (choice != 0);
        
        }else if (appType.equals("Customer") && !movie.getStatus().equals(ShowingStatus.Coming_Soon)) {
            
            int choice; 
            
            
            do {
                
                System.out.println( "====================== MOVIE CHOICES =====================\n" +
                        " 1. Display Showtimes                                   \n" +
                        " 2. View Reviews                                        \n" +
                        " 3. Add Review                                          \n" +
                        " 0. Back to Movie Listings                              \n" +
                        "==========================================================");
                
                System.out.println("Enter choice: ");
                
                while(!sc.hasNextInt()) {
                    
                    System.out.println("Invalid type. Please select from 0 -2");
                    sc.next();
                    
                }
                
                choice = sc.nextInt();
                sc.nextLine();
                
                switch(choice) {
                    
                    case 1:
                        break; 
                        
                    case 2:
                        ReviewManager.getInstance().listMovieReview(movie.getReviews());
                        break; 
                    case 3:
                        ReviewManager.getInstance().addMovieReview(movie.getMovieID());
                        break;
                        
                    case 0:
                        System.out.println("Back to movie listings...");
                        break; 
                       
                    default:
                        System.out.println("Please select a number between 0 - 3");
                        break; 
                        
                }
            }while (choice != 0);
        }
    }
    
    
    // prints Staff main menu and allows many actions such as viewing, editing, adding and searching movies 
     
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
    
    
    // prints out all movies in selected list and allows customer/staff to pick the movie they want
     
    private void selectMovie(List<Movie> selectedMovie, String appType) {
        
        int choice, choice1; 
        
        do {
            
            for(int i = 0; i < selectedMovie.size(); i++) {
                
                System.out.println(i + 1 + ". " + selectedMovie.get(i).getTitle() + " (" + selectedMovie.get(i).getStatus().toString() + ")");
                
            }
            
            do {
                
                System.out.println("Pick a movie: (Pick 0 to exit)");
                
                while(!sc.hasNextInt()) {
                    
                    System.out.printf("Invalid type. Please choose a choice from 0-%d \n", (selectedMovie.size()));
                    
                    sc.next();
                }
                
                choice = sc.nextInt() - 1; 
                
                if(choice == -1) {
                    
                    return; 
                }else if (choice < 0 || choice >= selectedMovie.size()) {
                    
                    System.out.println("Invalid option. Please enter a choice between 0 and " + selectedMovie.size());
                    
                }
                
                
            }while(choice < 0 || choice >= selectedMovie.size());
            
            
            selectedMovie.get(choice).displayMovieDetails();
            
            subMenu(selectedMovie.get(choice), appType);
            
            System.out.println("Enter 0 to return to Movie Menu \t\n" + "Enter 1 - 9 to return to list of movies");
            
            while(!sc.hasNextInt()) {
                
                System.out.println("Invalid type. Please choose a number from 0 - 9");
                sc.next();
                
            }
            
            choice1 = sc.nextInt();
            
            if(choice1 == 0) {
                
               break; 
               
            }
            
        }while (choice1 != 0);
        
    }
    
    
    // prints out menu to see different types of movies 
   
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
                        List <Movie> movieList = new ArrayList<Movie>();
                        
                        for(Map.Entry<String, Movie> entry : movies.entrySet()) {
                            
                            if(entry.getValue().getStatus().equals(ShowingStatus.Coming_Soon) || entry.getValue().getStatus().equals(ShowingStatus.Preview) || entry.getValue().getStatus().equals(ShowingStatus.Now_Showing)) {
                                
                                movieList.add(entry.getValue());
                                
                            }
                        }
                        
                        System.out.println(movieList.isEmpty());
                        
                        for(int i = 0; i < movieList.size(); i++) {
                            
                            System.out.println("Test code Movie Title: " + movieList.get(i).getTitle());
                        }
                        
                        this.selectMovie(movieList, appType);
                        break; 
                        
                    case 2:
                        
                        List<Movie> comingSoon = new ArrayList<Movie>();
                        
                        for(Map.Entry<String, Movie> entry: movies.entrySet()) {
                            
                            if(entry.getValue().getStatus().equals("Coming_Soon")) {
                                
                                comingSoon.add(entry.getValue());
                            }
                        }
                        
                        this.selectMovie(comingSoon, appType);
                        break; 
                        
                    case 3:
                        
                        List<Movie> previewList = new ArrayList<Movie>();
                        
                        for(Map.Entry<String, Movie> entry: movies.entrySet()) {
                            
                            if(entry.getValue().getStatus().equals("Preview")) {
                                
                                previewList.add(entry.getValue());
                                
                            }
                            
                        }
                        
                        this.selectMovie(previewList, appType);
                        break; 
                        
                    case 4:
                        
                        List<Movie> nowShowing = new ArrayList<Movie>();
                        
                        for(Map.Entry<String, Movie> entry: movies.entrySet()) {
                            
                            if(entry.getValue().getStatus().equals("Now_Showing")) {
                                
                                nowShowing.add(entry.getValue());
                                
                            }
                        }
                        
                        this.selectMovie(nowShowing, appType);
                        break; 
                        
                    case 5:
                        
                        List<Movie> endShow = new ArrayList<Movie>();
                        
                        for(Map.Entry<String, Movie> entry: movies.entrySet()) {
                            
                            if(entry.getValue().getStatus().equals("End_Of_Showing")) {
                                
                                endShow.add(entry.getValue());
                                
                            }
                        }
                        
                        this.selectMovie(endShow, appType);
                        break; 
                       
                    case 0:
                        System.out.println("Back to Staff movie menu.. ");
                        break; 
                        
                    default:
                        
                        System.out.println("Please enter a value from 0 - 5");
                        break; 
                         
                }
                
            }while (choice != 0);
        
        }else if (appType.equals("Customer")) {
            
            
            do {
                
                System.out.println("=================== MOVIE MENU (CUSTOMER) ==================\n" +
                        " 1. List all movies                                 \n" +
                        " 2. Coming Soon                                         \n" +
                        " 3. Preview                                         \n" +
                        " 4. Now Showing                                     \n" +
                        " 5. Search for movies                                     \n" +
                        " 0. Back to Customer Movie Menu......                     \n"+
                        "=========================================================");
                
                System.out.println("Enter choice: ");
                
                
                while(!sc.hasNextInt()){
                    
                    System.out.println("Invalid input type. Please choose a choice from 0 - 5");
                    sc.next();
                    
                }
                
                choice = sc.nextInt();
                
                switch(choice) {
                    
                    case 1:
                        List <Movie> movieList = new ArrayList<Movie>();
                        
                        for(Map.Entry<String, Movie> entry : movies.entrySet()) {
                            
                            if(entry.getValue().getStatus().equals("Coming_Soon") || entry.getValue().getStatus().equals("Preview") || entry.getValue().getStatus().equals("Now_Showing")) {
                                
                                movieList.add(entry.getValue());
                                
                            }
                        }
                        
                        this.selectMovie(movieList, appType);
                        break; 
                        
                    case 2:
                        
                        List<Movie> comingSoon = new ArrayList<Movie>();
                        
                        for(Map.Entry<String, Movie> entry: movies.entrySet()) {
                            
                            if(entry.getValue().getStatus().equals("Coming_Soon")) {
                                
                                comingSoon.add(entry.getValue());
                            }
                        }
                        
                        this.selectMovie(comingSoon, appType);
                        break; 
                        
                    case 3:
                        
                        List<Movie> previewList = new ArrayList<Movie>();
                        
                        for(Map.Entry<String, Movie> entry: movies.entrySet()) {
                            
                            if(entry.getValue().getStatus().equals("Preview")) {
                                
                                previewList.add(entry.getValue());
                                
                            }
                            
                        }
                        
                        this.selectMovie(previewList, appType);
                        break; 
                        
                    case 4:
                        
                        List<Movie> nowShowing = new ArrayList<Movie>();
                        
                        for(Map.Entry<String, Movie> entry: movies.entrySet()) {
                            
                            if(entry.getValue().getStatus().equals("Now_Showing")) {
                                
                                nowShowing.add(entry.getValue());
                                
                            }
                        }
                        
                        this.selectMovie(nowShowing, appType);
                        break; 
                        
                    case 5:
                        
                        this.searchMovie(appType);
                        break; 
                       
                    case 0:
                        System.out.println("Back to Customer movie menu.. ");
                        break; 
                        
                    default:
                        
                        System.out.println("Please enter a value from 0 - 5");
                        break; 
                         
                }
                
            }while (choice != 0);
        }
    }
    
    // Staff only: creates new Movie object 
     
    public void addMovies() {
        
        Movie movie = new Movie();
        ArrayList<Genre> genreList = new ArrayList<>();
        ArrayList<String> castList = new ArrayList<>();
        ArrayList<MovieFormat> movieFormatList = new ArrayList<>();
        
        // input movie title 
        System.out.println("Enter movie title: ");
        sc.nextLine();
        
        String title = sc.nextLine();
        movie.setTitle(title);
        
        
        // input genre 
        System.out.println("List of Genres: ");
        
        // list out all the genres
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
        
        // input Director 
        System.out.println("Enter Director name: ");
        sc.nextLine();
        movie.setDirector(sc.nextLine());
        
        // input casts 
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
        
        // input synopsis
        System.out.println("Enter synopsis");
        movie.setSynopsis(sc.nextLine());
        
        // input movie rating
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
        
        
        // input Movie Format
        
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
        
        // input Movie Duration 
        System.out.println("Enter Movie Duration");
        
        while(!sc.hasNextInt()) {
            
            System.out.println("Invalid input. Please enter an integer value");
            sc.next();
            
        }
        
        movie.setDuration(sc.nextInt());
        
        // input showing status 
        
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
        
        // input release date 
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
                    String movieId = IDHelper.getLatestID("movie");
                    movie.setMovieID(movieId);
                    System.out.println("Movie ID; " + movieId);
                    this.save(movie);
                    this.movies.put(movie.getMovieID(), movie);
                    
                    System.out.println("Movie created! Going back to Movie Menu");
                    choice = 0; 
                    break; 
                    
                case 2: 
                    this.editMovie(movie);
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
    
    // print menu for editing movie details for Staff and then save it in dat file  
     
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
    
    
    // search for movies 
     
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
        
        selectMovie(moviesFound, appType);
    }
    
    // remove movie by changing status to END_OF_SHOWING
    
    private void removeMovie(Movie movie) {
        
        movie.setStatus(ShowingStatus.End_Of_Showing);
        this.save(movie);
    }
    
    /**
     * 
     * Staff only: View top 5 movies by sales
     */
    public void viewTop5Sales() {
        
        
        ArrayList<Movie> top5 = new ArrayList<Movie>();
        
        for(Map.Entry<String, Movie> entry : movies.entrySet()) {
            
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
    public void viewTop5Review() {
        
        ArrayList<Movie> top5 = new ArrayList<Movie>();
        
        for(Map.Entry<String, Movie> entry: movies.entrySet()) {
            
            if(entry.getValue().getStatus().equals("Preview") || entry.getValue().getStatus().equals("Now_Showing")) {
                
                top5.add(entry.getValue());
                
            }
        }
        
        
    }
   
     // adds review to list of reviews that a movie has and updates the movie's total number of reviews
     // total review score and average review score
     // reviewScore: score of the review to be added
     // function: specifies whether to add or remove a review from the review list
     
    public void updateReview(String movieID, String reviewID, double reviewScore, String function)
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

    public Movie getMoviebyID(String ID){
        
        return movies.get(ID);
    }

    public void updateTicketsSold(String currMovieID, int size) {
        
        for(Map.Entry<String, Movie> entry: movies.entrySet()) {
            
            if(entry.getValue().getMovieID().equals(currMovieID)) {
                
                int ticketSold = entry.getValue().getTicketsSold();
                ticketSold = ticketSold + size;
                entry.getValue().setTicketsSold(ticketSold);
                
            }
        }
    }

    public void updateGrossProfit(String currMovieID, double totalPrice) {
        for(Map.Entry<String, Movie> entry: movies.entrySet()) {
            
            if(entry.getValue().getMovieID().equals(currMovieID)) {
                
                double profit = entry.getValue().getProfit();
                profit = profit + totalPrice;
                entry.getValue().setProfit(profit);
                
            }
        }
    }

    void updateShowtimes(String movieID, String showtime) {
        this.movies.get(movieID).addShowtime(showtime);

        this.save(this.movies.get(movieID));

    }
}
