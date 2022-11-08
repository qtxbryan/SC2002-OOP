package MovieEntities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
// movie class
import java.util.List;

import review.Review;

enum MovieRating {G, PG, PG13, NC16, M18, R21;}
enum ShowingStatus {Coming_Soon, Preview, Now_Showing, End_Of_Showing;}

public class Movie implements Serializable{
    // attributes
    private String movieID;
    private String title;
    public List<Genre> genres;
    private String director;
    public List<String> casts;
    private String synopsis;
    private MovieRating movieRating;
    public List<MovieFormat> movieFormat;
    private int duration;
    public List<String> reviews;
    private int totalReviewNo;
    private double totalReviewScore;
    private double averageReviewScore;
    private ShowingStatus status;
    private LocalDate releaseDate;
    private int ticketsSold;
    private double profit;
//  public List<ShowTime> showtimes;
    
    
    public Movie() {
        
        this.genres = new ArrayList<Genre>();
        this.casts = new ArrayList<String>();
        this.movieFormat = new ArrayList<MovieFormat>();
        this.reviews = new ArrayList<String>();
        this.averageReviewScore = 0;
        this.totalReviewNo = 0; 
        this.totalReviewScore = 0;
    }
    
    // getters
    public String getMovieID() {
        return movieID;
    }
    public String getTitle() {
        return title;
    }
    public List<Genre> getGenres() {
        return genres;
    }
    public String getDirector() {
        return director;
    }
    public List<String> getCasts() {
        return casts;
    }
    public String getSynopsis() {
        return synopsis;
    }
    public MovieRating getMovieRating() {
        return movieRating;
    }
    public List<MovieFormat> getMovieFormat() {
        return movieFormat;
    }
    public int getDuration() {
        return duration;
    }
    public List<String> getReviews() {
        return reviews;
    }
    public int getTotalReviewNo() {
        return totalReviewNo;
    }
    public double getTotalReviewScore() {
        return totalReviewScore;
    }
    public double getAverageReviewScore() {
        return averageReviewScore;
    }
    public ShowingStatus getStatus() {
        return status;
    }
    public LocalDate getReleaseDate() {
        return releaseDate;
    }
    public int getTicketsSold() {
        return ticketsSold;
    }
    public double getProfit() {
        return profit;
    }
    public List<ShowTime> getShowtimes() {
        return showtimes;
    }
    
    
    // setters 
    public void setMovieID(String movieID) {
        this.movieID = movieID;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }
    public void setDirector(String director) {
        this.director = director;
    }
    public void setCasts(List<String> casts) {
        this.casts = casts;
    }
    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }
    public void setMovieRating(MovieRating movieRating) {
        this.movieRating = movieRating;
    }
    public void setMovieFormat(List<MovieFormat> movieFormat) {
        this.movieFormat = movieFormat;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }
    public void setReviews(List<String> reviews) {
        this.reviews = reviews;
    }
    public void setTotalReviewNo(int totalReviewNo) {
        this.totalReviewNo = totalReviewNo;
    }
    public void setTotalReviewScore(double totalReviewScore) {
        this.totalReviewScore = totalReviewScore;
    }
    public void setAverageReviewScore(double averageReviewScore) {
        this.averageReviewScore = averageReviewScore;
    }
    public void setStatus(ShowingStatus status) {
        this.status = status;
    }
    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
    public void setTicketsSold(int ticketsSold) {
        this.ticketsSold = ticketsSold;
    }
    public void setProfit(double profit) {
        this.profit = profit;
    }
//  public void setShowtimes(List<ShowTime> showtimes) {
//      this.showtimes = showtimes;
//  }
   
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

    // adders

    // adds a new review into review array list
  
    public void addMovieReview(String reviewID) {
        this.reviews.add(reviewID);
    }

    // removes a review from review array list
     
    public void removeMovieReview(String reviewID) {
    	int i;
    	
    	for (i=0;i<this.getReviews().size(); i++)
    		if (this.getReviews().get(i).equals(reviewID)) {
    			this.reviews.remove(i);
    		}
    }
}
