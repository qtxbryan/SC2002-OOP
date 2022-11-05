package movie;

//Movie class
import java.util.List;
import java.util.ArrayList;

enum MovieRating {G, PG, PG13, NC16, M18, R21;}
enum Genre {Action, Animation, Blockbuster, Comedy, Crime, Drama, Fanatasy, Historical, Horror, Romance, SciFi, Thriller;}
enum ShowingStatus {Coming_Soon, Preview, Now_Showing, End_Of_Showing;}
enum MovieFormat {TwoD, ThreeD, Imax;}

public class Movie{
    //attributes
    private int movieID;
    private String title;
    public List<Genre> genres;
    private String director;
    public List<String> casts;
    private String synopsis;
    private MovieRating movieRating;
    public List<MovieFormat> movieFormat;
    private int duration;
    public List<Review> reviews;
    private int totalReviewNo;
    private double totalReviewScore;
    private double averageReviewScore;
    private ShowingStatus status;
    private DateTime releaseDate;
    private int ticketsSold;
    private double profit;
    public List<ShowTime> showtimes;
    
    //getters
    public int get_movieID(){
        return movieID;
    }
    public String get_title(){
        return title;
    }
    public String get_director(){
        return director;
    }
    public String get_synopsis(){
        return synopsis;
    }
    public MovieRating get_movieRating(){
        return movieRating;
    }
    public int get_duration(){
        return duration;
    }
    public int get_totalReviewNo(){
        return totalReviewNo;
    }
    public double get_totalReviewScore(){
        return totalReviewScore;
    }
    public double get_averageReviewScore(){
        return averageReviewScore;
    }
    public ShowingStatus get_status(){
        return status;
    }
    public DateTime get_releaseDate(){
        return releaseDate;
    }
    public int get_ticketsSold(){
        return ticketsSold;
    }
    public double get_profit(){
        return profit;
    }
    
    //setters
    public void set_movieID(int ID){
        movieID = ID;
    }
    public void set_title(String title){
        this.title = title;
    }
    public void set_director(String director){
        this.director = director;
    }
    public void set_synopsis(String synopsis){
        this.synopsis = synopsis;
    }
    public void set_movieRating(MovieRating movieRating){
        this.movieRating = movieRating;
    }
    public void set_duration(int duration){
        this.duration = duration;
    }
    public void set_totalReviewNo(int totalReviewNo){
        this.totalReviewNo = totalReviewNo;
    }
    public void set_totalReviewScore(double totalReviewScore){
        this.totalReviewScore = totalReviewScore;
    }
    public void set_averageReviewScore(double averageReviewScore){
        this.averageReviewScore = averageReviewScore;
    }
    public void set_status(ShowingStatus status){
        this.status = status;
    }
    public void set_releaseDate(DateTime releaseDate){
        this.releaseDate = releaseDate;
    }
    public void set_ticketsSold(int ticketsSold){
        this.ticketsSold = ticketsSold;
    }
    public void set_profit(double profit){
        this.profit = profit;
    }
    //constuctors
    
    //operations
    public void displayMovieDetails(){
        System.out.println("Movie Title: "+title);
        System.out.println("Genres: "+genres);
        System.out.println("------------------------");
        System.out.println("Director: "+director);
        System.out.println("Casts: "+casts);
        System.out.println("");
        System.out.println("Synopsis: ");
        System.out.println(synopsis);
        System.out.println("");
        System.out.println("MovieRating: "+movieRating);
        System.out.println("MovieFormat: "+movieFormat);
        System.out.println("Duration: "+duration);
        System.out.println("Status: "+status);
        System.out.println("ReleaseDate: "+releaseDate);
        System.out.println();
        System.out.println();
    }
}
