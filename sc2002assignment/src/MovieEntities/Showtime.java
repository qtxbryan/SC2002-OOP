package MovieEntities;

import CineplexEntities.Cinema;
import CineplexEntities.CinemaStatus;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Showtime object with relevant attributes of a showtime for a specific movie.
 */
public class Showtime implements Serializable {
    /**
     * Unique showtimeID to identify specific showtime.
     */
    private String showtimeID;
    /**
     * LocalDateTime object that specifies date and time of the showtime.
     */
    private LocalDateTime dateTime;
    /**
     * Unique movieID to specify the movie being shown for that showtime.
     */
    private String movieID;
    /**
     * Movie Format of the specific showtime.
     */
    private MovieFormat movieFormat;
    /**
     * Cinema object with access to a seating plan. Cinema held is the cinema at which the movie is showing.
     */
    private Cinema cinema;
    /**
     * Name of the cineplex where the movie is showing at this specific showtime.
     */
    private String cineplexName;
    /**
     * Status of the Showtime, whether its AVAILABLE, or SELLING_FAST etc.
     */
    private CinemaStatus cinemaStatus;


    //Methods

    /**
     * Updates the status of the cinema depending on proportion of seats filled.
     */
    public void updateCinemaStatus() {
    	double percentageFilled = (double) getCinema().getOccupiedSeatsNo() / (double) getCinema().getTotalSeatNo();

    	if (percentageFilled <= 0.50) {
    		setCinemaStatus(CinemaStatus.AVAILABLE);
    	}
    	else if (percentageFilled < 1) {
    		setCinemaStatus(CinemaStatus.SELLING_FAST);
    	}
    	else {
    		setCinemaStatus(CinemaStatus.SOLD_OUT);
    	}
    }


    //Getters

    /**
     *
     * @return unique showtimeID attribute.
     */
    public String getShowtimeID() {return showtimeID;}

    /**
     *
     * @return DateTime of showtime.
     */
    public LocalDateTime getDateTime() {return dateTime;}

    /**
     *
     * @return unique movieID attribute.
     */
    public String getMovieID() {return movieID;}

    /**
     *
     * @return MovieFormat of the showtime.
     */
    public MovieFormat getMovieFormat() {return movieFormat;}

    /**
     *
     * @return Cinema object.
     */
    public Cinema getCinema() {return cinema;}
    public String getCineplexName() {return cineplexName;}

    /**
     *
     * @return CinemaStatus enum.
     */
    public CinemaStatus getCinemaStatus() {return cinemaStatus;}

    //Setters

    /**
     *
     * @param showtimeID to set attribute of Showtime object.
     */
    public void setShowtimeID(String showtimeID) {this.showtimeID = showtimeID;}

    /**
     *
     * @param dateTime to set DateTime attribute of Showtime object.
     */
    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }

    /**
     *
     * @param movieID to set movieID attribute of Showtime object (movie being shown).
     */
    public void setMovieID(String movieID) {this.movieID = movieID;}

    /**
     *
     * @param movieFormat to be set.
     */
    public void setMovieFormat(MovieFormat movieFormat) {this.movieFormat = movieFormat;}

    /**
     *
     * @param cinema Cinema object to be set of attribute of the Showtime.
     */
    public void setCinema(Cinema cinema) {this.cinema = cinema;}

    /**
     *
     * @param cineplexName set as attribute of Showtime.
     */
    public void setCineplexName(String cineplexName) {this.cineplexName = cineplexName;}

    /**
     *
     * @param cinemaStatus Enum to set as attribute of Showtime.
     */
    public void setCinemaStatus(CinemaStatus cinemaStatus) {this.cinemaStatus = cinemaStatus;}

}
