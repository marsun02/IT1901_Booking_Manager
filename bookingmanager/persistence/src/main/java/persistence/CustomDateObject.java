package persistence;

public class CustomDateObject {
    private String date;
    private String time;
    private String restaurantName;

    public CustomDateObject(String date, String time){
        this.date = date;
        this.time = time;
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
}
