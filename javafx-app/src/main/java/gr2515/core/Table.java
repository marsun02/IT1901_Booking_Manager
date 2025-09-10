package gr2515.core;

public class Table {
    private boolean isBooked;
    private int id;
    private int spots;
    private String customerName;

    public Table(int id, int spots){
        this.id = id;
        this.spots = spots;
        this.isBooked = false;
        this.customerName = null;
    }

    public boolean getStatus(){
        return isBooked;
    }

    public void book(String customerName){
        if (this.isBooked){
            // TODO: Display a message that states the table is taken
        }

        this.customerName = customerName;
        this.isBooked = true;
    }

    public void free(){
        if (!this.isBooked){
            // TODO: Display message that states the table already is vacant
        }

        this.isBooked = false;
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
}
