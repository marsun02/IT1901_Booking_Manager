package model;

/**
 * CustomDateObject is a class representing a date and time tailored for this project as other
 * similar classes had problems/redundant info for saving/loading.
 * Can also be seen as a representation of a booking as the bookings datafile only contains a collection of this class.
 */
public class CustomDateObject {
    private String date;
    private String time;
    private String restaurantName;
    private String user;
    private String bookedName;
    private String firstName;
    private String lastName;
    private int id;

    /**
     * Creates a new date object containing a date, time, name of linked restaurant, linked user, first and last name of booking user and an id.

     * @param date date for booking.
     * @param time time of day for booking.
     * @param restaurantName name of linked restaurant.
     * @param user name of linked user.
     * @param firstName first name of user making booking.
     * @param lastName last name of user making booking.
     * @param id id of this date object.
     */
    public CustomDateObject(String date, String time, String restaurantName, String user, String firstName,
            String lastName, int id) {
        this.date = date;
        this.time = time;
        this.restaurantName = restaurantName;
        this.user = user;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bookedName = firstName + " " + lastName;
        this.id = id;
    }

    /**
     * Creates copy of another instance of this class.

     * @param other object of which to make copy of.
     */
    public CustomDateObject(CustomDateObject other){
        this.date = other.getDate();
        this.time = other.getTime();
        this.restaurantName = other.getRestaurantName();
        this.user = other.getUser();
        this.firstName = other.getFirstName();
        this.lastName = other.getLastName();
        this.bookedName = other.getBookedName();
        this.id = other.getId();
    }

    /**
     * Creates instance of this class without the parameters set.
     */
    public CustomDateObject() {
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public String getUser() {
        return user;
    }

    public String getBookedName() {
        return bookedName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getId() { // Integer instead of int so we can use toString() on it
        return id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setBookedName(String bookedName) {
        this.bookedName = bookedName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setId(int id) {
        this.id = id;
    }

}
