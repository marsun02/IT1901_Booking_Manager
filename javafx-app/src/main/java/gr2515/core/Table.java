package gr2515.core;

public class Table {
    private boolean isBooked;
    private int id;

    public Table(int id){
        this.id = id;
        this.isBooked = false;
    }

    public boolean getStatus(){
        return isBooked;
    }

    public void book(){
        isBooked = true;
    }

    public void free(){
        isBooked = false;
    }
}
