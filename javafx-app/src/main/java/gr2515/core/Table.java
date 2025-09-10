package gr2515.core;

import java.util.Date;

public class Table {

    // Private fields

    private boolean isBooked;
    private int id;
    private int spots;
    private String customerName;
    private Date bookedTime;

    // Constructor

    public Table(int id, int spots){
        this.id = id;
        this.spots = spots;
        this.isBooked = false;
        this.customerName = null;
        this.bookedTime = null;
    }

    // Getters and setters

    public boolean getStatus(){
        return isBooked;
    }

    public int getId(){
        return this.id;
    }

    public int getSpots(){
        return this.spots;
    }

    public String getCustomerName(){
        return customerName;
    }

    public Date getBookedTime(){
        return bookedTime;
    }

    // Booking and freeing methods

    public void book(String customerName, Date bookedTime){
        if (this.isBooked){
            // TODO: Display a message that states the table is taken
        }

        this.bookedTime = bookedTime;
        this.customerName = customerName;
        this.isBooked = true;
    }

    public void free(){
        if (!this.isBooked){
            // TODO: Display message that states the table already is vacant
        }

        this.bookedTime = null;
        this.customerName = null;
        this.isBooked = false;
    }
}
