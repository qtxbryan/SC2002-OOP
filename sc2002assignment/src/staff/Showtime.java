package staff;

import java.time.LocalDate;

enum MovieFormat{
    TwoD,
    ThreeD,
    Imax,
};

public class Showtime {
	
    private int showTimeID;
	private int movieID;
	private MovieFormat movieFormat;
	private Cinema cinema;
	private int cineplexID;
	private CinemaStatus cinemaStatus;

    //private DateTime dateTime;

	/**
	 * @param movieFormat the movieFormat to set
	 */
	public void setMovieFormat(MovieFormat movieFormat) {
		this.movieFormat = movieFormat;
	}

	/**
	 * @return the cinema
	 */
	public Cinema getCinema() {
		return cinema;
	}

	/**
	 * @param cinema the cinema to set
	 */
	public void setCinema(Cinema cinema) {
		this.cinema = cinema;
	}

	/**
	 * @return the cineplexID
	 */
	public int getCineplexID() {
		return cineplexID;
	}

	/**
	 * @param cineplexID the cineplexID to set
	 */
	public void setCineplexID(int cineplexID) {
		this.cineplexID = cineplexID;
	}

	/**
	 * @return the cinemaStatus
	 */
	public CinemaStatus getCinemaStatus() {
		return cinemaStatus;
	}

	/**
	 * @param cinemaStatus the cinemaStatus to set
	 */
	public void setCinemaStatus(CinemaStatus cinemaStatus) {
		this.cinemaStatus = cinemaStatus;
	}

}
