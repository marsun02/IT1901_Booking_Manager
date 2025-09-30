package gr2515.core;

import java.util.Date;

public class Table {

    // Private fields

    private boolean isBooked;
    private int id;
    private int spots;
    private User bookingUser;
    private Date bookedTime;

    // Constructor

    public Table(int id, int spots){
        this.id = id;
        this.spots = spots;
        this.isBooked = false;
        this.bookingUser = null;
        this.bookedTime = null;
    }

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

    public User getBookingUser(){
        return bookingUser;
    }

    public Date getBookedTime(){
        return bookedTime;
    }

    // Booking and freeing methods

    public void book(User bookingUser, Date bookedTime){
        if (this.isBooked){
            // TODO: Display a message that states the table is taken
        }

        this.bookedTime = bookedTime;
        this.bookingUser = bookingUser;
        this.isBooked = true;
        bookingUser.addTable(this);
    }

    public void freeTable(){
        if (!this.isBooked){
            // TODO: Display message that states the table already is vacant
        }

        bookingUser.removeTable(this);
        this.bookedTime = null;
        this.bookingUser = null;
        this.isBooked = false;
    }
}