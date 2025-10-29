package persistence;

public class CustomDateObject {
    private String date;
    private String time;
    private String restaurantName;
    private String user;
    private String bookedName;
    private int id;
    private String firstName;
    private String lastName;

    public CustomDateObject(String date, String time, String restaurantName, String user, String firstName, String lastName){
        this.date = date;
        this.time = time;
        this.restaurantName = restaurantName;
        this.user = user;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bookedName = firstName + " " + lastName;
        this.id = FileManagement.getId("bookingId");
    }

    public CustomDateObject() {}

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public String getUser(){
        return user;
    }

    public String getBookedName(){
        return bookedName;
    }

    public Integer getId(){ // Integer instead of int so we can use toString() on it
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
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

    public void setUser(String user){
        this.user = user;
    }

    public void setBookedName(String bookedName){
        this.bookedName = bookedName;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
