package CineplexEntities;

//Seat Class

enum SeatType {Couple, Regular;}

public class Seat{
    //attributes
    private boolean occupied;
    private int seatID;
    private SeatType seatType;

    //getters
    public boolean get_occupied(){
        return occupied;
    }
    public int get_seatID(){
        return seatID;
    }
    public SeatType get_seatType(){
        return seatType;
    }

    //setters
    public void set_occupied(boolean v){
        occupied = v;
    }
    public void set_seatID(int ID){
        seatID = ID;
    }
    public void set_seatType(SeatType st){
        seatType = st;
    }

    //constructors
    Seat(int ID, SeatType st){
        occupied = false;
        seatID = ID;
        seatType = st;
    } 
}
