package CineplexEntities;

/**
 * Entity class with typical attributes of a seat.
 */
public class Seat {
    /**
     * Boolean that specifies if a seat is occupied.
     */
    private boolean occupied;
    /**
     * ID that identifies a seat.
     */
    private String seatID;

    /**
     * To check for occupancy of the seat.
     * @return whether the seat is occupied.
     */
    public boolean isOccupied() {
        return occupied;
    }

    /**
     * To set the occupancy of the seat.
     * @param occupied set whether seat is occupied.
     */
    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }


    public String getSeatID() {
        return seatID;
    }

    public void setSeatID(String seatID) {
        this.seatID = seatID;
    }
}
