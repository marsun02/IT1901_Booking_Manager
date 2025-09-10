package gr2515.core;

public class Table {
    private boolean isBooked;
    private int id;
    private int spots;

    public Table(int id, int spots){
        this.id = id;
        this.spots = spots;
        this.isBooked = false;
    }

    public boolean getStatus(){
        return isBooked;
    }

    public void book(){
        this.isBooked = true;
    }

    public void free(){
        this.isBooked = false;
    }

    public int getId(){
        return this.id;
    }

    public int getSpots(){
        return this.spots;
    }
}
