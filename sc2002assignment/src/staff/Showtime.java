package staff;

enum MovieFormat{
    TwoD,
    ThreeD,
    Imax,
};

public class Showtime {
    
    private int showTimeID;

    private DateTime dateTime;

    private int movieID;

    private MovieFormat movieFormat;

    private Cinema cinema;

    private int cineplexID;

    private CinemaStatus cinemaStatus;

}
