package gr2515.core;

import java.util.Set;

public class User {
    
    // Private fields
    private int id;
    private String name;
    private String email;
    private String phoneNumber;
    private Set<Booking> bookings; // Bookings made by this user


    // Constructor
    public User(int id, String name, String email, String phoneNumber){

        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.bookings = new HashSet<>();
    }


    //Setters
    public void setId(int id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setPhoneNumber(String phonenumber){
        this.phoneNumber = phonenumber;
    }


    //Getters
    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getEmail(){
        return email;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }

    public List<Booking> getBookings(){ //Returns a copy, instead of the orginal list, to prevent external modification
        return new ArrayList<>(bookings);
    }


    //Add a booking
    public void addBooking(Booking booking){
            bookings.add(booking);
    }


    //Remove a booking
    public void removeBooking(Booking booking){
            bookings.remove(booking);
    }
}
