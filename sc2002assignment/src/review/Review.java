package review;

import java.time.LocalDateTime;

public class Review {

	//attributes

	private int reviewID; // ID of review
	private String reviewerName; 
	private String reviewTitle; 
	private String reviewBody; 
	private int movieID; // ID of movie being reviewed
	private double score; 
	private LocalDateTime dateTime; //date and time of review  
	
    //Getters
    public int get_reviewID() {
        return reviewID;
    }

    public String get_reviewerName() { 
		return reviewerName; 
	}

    public String get_reviewTitle() {
        return reviewTitle;
    }

    public String get_reviewBody() {
        return reviewBody;
    }

    public int getMovieID() {
    	return movieID;
    }

    public double get_score() {
        return score;
    }

    public LocalDateTime get_dateTime() {
        return dateTime;
    }
    
    //Setters
    public void set_reviewID(int reviewID) {
        this.reviewID = reviewID;
    }

    public void set_reviewerName(String reviewerName) { 
		this.reviewerName = reviewerName; 
	}

    public void set_reviewTitle(String reviewTitle) {
        this.reviewTitle = reviewTitle;
    }

    public void set_reviewBody(String reviewBody) {
        this.reviewBody = reviewBody;
    }

    public void set_movieID(int movieID) {
    	this.movieID = movieID;
    }

    public void set_score(double score) {
        this.score = score;
    }

    public void set_dateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

}
