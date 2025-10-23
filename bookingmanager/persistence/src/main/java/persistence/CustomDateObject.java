package persistence;

public class CustomDateObject {
    private String date;
    private String time;
    private String restaurantName;
    private String user;
    private String bookedName;

    public CustomDateObject(String date, String time, String restaurantName, String user, String bookedName){
        this.date = date;
        this.time = time;
        this.restaurantName = restaurantName;
        this.user = user;
        this.bookedName = bookedName;
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
}
