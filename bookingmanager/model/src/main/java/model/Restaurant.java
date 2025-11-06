package model;

public class Restaurant {
    // Private fields
    private int id;
    private String name;
    private int amountOfTables;

    // Constructor
    public Restaurant(String name, int id, int amountOfTables) {
        this.name = name;
        this.id = id;
        this.amountOfTables = amountOfTables;
    }

    // empty constructor and setters for Jackson
    // not intended for normal use

    public Restaurant() {

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAmountOfTables(int amount) {
        this.amountOfTables = amount;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getAmountOfTables() {
        return amountOfTables;
    }
}
