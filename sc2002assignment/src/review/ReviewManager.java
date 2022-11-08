package review;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import MovieEntities.MovieManager;
import utilities.RootFinder;
import utilities.Serializer;
import utilities.IDHelper;

// ReviewManager for adding, viewing or deleting reviews
 
public class ReviewManager {
	
    // attributes

    private Scanner sc = new Scanner(System.in);
    
	// hashmap of all the available reviews in the system 
    // search out a specific review by its reviewID 
	
    private Map<String, Review> reviews;

	// repeat_instance tracks whether ReviewManager has been instantiated before
     
    private static ReviewManager repeat_instance = null;

	// instantiates ReviewManager. If no previous instance was created, one is created
    // otherwise, the previous instance created is used.
     
    public static ReviewManager getInstance() 
    {
        if (repeat_instance == null)
            repeat_instance = new ReviewManager();
        return repeat_instance;
    }
    
	// constructor of ReviewManager. It loads all available reviews from the system and turns it into a hashmap using the reviewID as a key  
	 
    private ReviewManager() 
    {
    	this.reviews = new HashMap<String, Review>();
    	this.reviews = this.load();
    }
    
	// listMovieReview prints out all the reviews for a specific movie 
    // reviewIDs: array list of the reviewIDs of a movie, to indicate which reviews belong to the movie
    
    public void listMovieReview(List<String> reviewIDs) 
    {
    	int i = 0;
    	
    	for (String reviewID : reviewIDs) 
        {
    		i++;
    		Review review = this.reviews.get(reviewID);
    		
    		System.out.println("================ REVIEW " + i + " ================");
            System.out.println("Name: " + review.get_reviewerName());
            System.out.println("Title: " + review.get_reviewTitle());
            System.out.println("Score: " + review.get_score() + "/5");
            System.out.println("Review: " + review.get_reviewBody());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy, hh.mma");
            System.out.println("Date & Time: " + review.get_dateTime().format(formatter));
            System.out.println("==================================================");
            System.out.println("");
    	}
    	
    	if (i == 0) {
    		System.out.println("No reviews found");
    	}
    }
    
    // addMovieReview allows the customer to add a new review for a specific movie
     
    public void addMovieReview(String movieID) 
    {
    	Review review = new Review();
 	
        System.out.println("Enter your name: ");
        review.set_reviewerName(sc.nextLine());
        
        System.out.println("Enter title of your review: ");
        review.set_reviewTitle(sc.nextLine());
        
        System.out.println("Enter your review: ");
        review.set_reviewBody(sc.nextLine());
        
        System.out.println("Enter a score between 0-5: ");

        while (!sc.hasNextDouble()) 
        {
        	System.out.println("Invalid input. Please enter a numeric value.");
    		sc.next(); 
        }

        review.set_score(sc.nextDouble());
        
        review.set_dateTime(LocalDateTime.now());

        int choice;
        
        do {
            System.out.println(	"========================= ADD REVIEW ====================\n" +
			                    " 1. Submit review	   						    	 	 \n" +
				                " 0. Delete draft, return to Movies List              \n"+
                                "=========================================================");
            
            System.out.println("Your draft: ");
            System.out.println("Name: " + review.get_reviewerName());
            System.out.println("Title: " + review.get_reviewTitle());
            System.out.println("Review: " + review.get_reviewBody());
            System.out.println("Score: " + review.get_score());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy, hh.mma");
            System.out.println("Date & Time: " + review.get_dateTime().format(formatter));
            System.out.println("");
            System.out.println("Enter choice: ");
            
            while (!sc.hasNextInt()) {
            	System.out.println("Invalid input. Please enter an integer value.");
        		sc.next(); 
            }
            
            choice = sc.nextInt();
            sc.nextLine();
        
            switch (choice) {
            case 1:
            	String reviewID = IDHelper.getLatestID("review");
            	review.set_reviewID(reviewID);
            	this.save(review);
            	MovieManager.getInstance().updateReview(movieID, reviewID, review.get_score(), "add");
            	
            	this.reviews.put(review.get_reviewID(), review);
            	
            	System.out.println("Review submitted! Returning to Review Portal......");
            	choice = 0;
            	break;
            case 0:
            	System.out.println("Draft deleted. Returning to Movies List......");
            	break;
        	default:
        		System.out.println("Invalid choice. Please enter a number between 0-1");
        		break;
            }
        
        } while (choice != 0);
    }
    
    // deleteMovieReview is accessible only by a staff account. This is used by staff members to delete review bombs
    // reviewIDs: array list of the reviewIDs of a movie, to indicate which reviews belong to the movie
     
    public void deleteMovieReview(List<String> reviewIDs) 
    {
    	this.listMovieReview(reviewIDs);
    	System.out.println("");
    	
    	int choice;
    	
    	do {
        	System.out.println("Which review would you like to delete? Input 0 to return to Movies List.");
        	
        	while (!sc.hasNextInt()) 
            {
            	System.out.println("Invalid input. Please enter an integer value.");
        		sc.next(); 
            }
        	
        	choice = sc.nextInt();
        	
        	if (choice == 0) 
            {
        		System.out.println("Returning to Movies List......");
        		return;
        	} 
            
            else if (choice <= reviewIDs.size()) 
            {
            	String reviewID = reviewIDs.get(choice-1);       		
        		MovieManager.getInstance().updateReview(this.reviews.get(reviewID).getMovieID(), reviewID, this.reviews.get(reviewID).get_score(), "remove");

        		String root = RootFinder.findRootPath();
        		File file = new File(root + "/data/reviews/review_" + reviewID + ".dat");
        		file.delete();
        		this.reviews = this.load();        		
        		choice = 0;
        	}
            
            else 
            {
        		System.out.println("Invalid input. Please give a number between 0-" + reviewIDs.size());
        	}    		
    	} while (choice != 0);
    }

	// private serialization and deserialization

	// this is used to save a review that has just been added by the customer. It will create a new review data file in the "reviews" folder
	// review: this is the current review that is being written and to be saved
	 
    private void save(Review review) 
    {
		String filePath = RootFinder.findRootPath() + "/data/reviews/review_" + review.get_reviewID() + ".dat";
		Serializer.serializeObject(review, filePath);
	}
    
     // this returns all of the reviews that are currently in the "reviews" folder 
     // this returns a hashmap of all the available reviews in the data files 
    
    public Map<String, Review> load() 
    {
        HashMap<String, Review> loadReviews = new HashMap<String, Review>();
        File folder = new File(RootFinder.findRootPath() + "/data/reviews");

        File[] listOfFiles = folder.listFiles();
        
        if(listOfFiles != null)
        {
          for(int i=0;i<listOfFiles.length;i++)
          {
            String filePath = listOfFiles[i].getPath(); // returns full path incl file name and type
            Review newReview = (Review) Serializer.deserializeObject(filePath);
            String fileID = listOfFiles[i].getName().split("\\.(?=[^\\.]+$)")[0].split("_")[1];
                loadReviews.put(fileID, newReview);
            }
        }
        return loadReviews;
    }    
}