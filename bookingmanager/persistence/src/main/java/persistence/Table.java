package persistence;

import java.util.Date;

public class Table {

    // Private fields

    private boolean isBooked;
    private int id;
    private int spots;
    private Date bookedTime;

    // Constructor

    public Table(int id, int spots){
        this.id = id; // TODO: Make sure that id has to be unique
        this.spots = spots;
        this.isBooked = false;
        this.bookedTime = null;
    }

    // empty constructors and setters for Jackson
    // not intended for normal use

    public Table(){

    }

    public void setId(int id){
        this.id = id;
    }

    public void setSpots(int spots){
        this.spots = spots;
    }

    public void setBooked(boolean isBooked){
        this.isBooked = isBooked;
    }

    // Getters

    public boolean isBooked(){
        return isBooked;
    }

    public int getId(){
        return this.id;
    }

    public int getSpots(){
        return this.spots;
    }

    public Date getBookedTime(){
        if (bookedTime == null){
            return null;
        }
        return new Date(bookedTime.getTime());
    }

    // Booking and freeing methods

    public void book(User bookingUser, Date bookedTime){
        if (this.isBooked){
            // TODO: Display a message that states the table is taken
            return;
        }
        if (bookedTime == null){
            this.bookedTime = null; // set to null because bookedTime.getTime() gives error when it's null
        } else {
            this.bookedTime = new Date(bookedTime.getTime()); 
        }
        this.isBooked = true;
        bookingUser.addTable(this);
    }

    public void freeTable(User bookingUser){
        if (!this.isBooked){
            // TODO: Display message that states the table already is vacant
            return;
        }

        bookingUser.removeTable(this);
        this.bookedTime = null;
        this.isBooked = false;
    }
}