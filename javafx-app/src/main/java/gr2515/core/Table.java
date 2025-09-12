package gr2515.core;

import java.util.Date;
import java.util.HashSet;

public class Table {

    // Private fields

    private int id;
    private int spots;
    private Set<Booking> bookings;

    // Constructor

    public Table(int id, int spots){
        this.id = id;
        this.spots = spots;
        this.bookings = new HashSet<>();
    }

    // Getters and setters


    public int getId(){
        return this.id;
    }

    public int getSpots(){
        return this.spots;
    }

    public Set<Booking> getBookings(){
        return new HashSet<>(bookings); //Return copy instead of the original list, to prevent external modification
    }

    public void addBooking(Booking booking){
        bookings.add(booking); //HashSets prevents duplicates by default.
    }

    public void removeBooking(Booking booking){
        bookings.remove(booking);
    }
}
