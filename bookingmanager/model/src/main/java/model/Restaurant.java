package model;

/**
 * Represents a restaurant with id, name and tables.
 * 
 * <p>The restaurants have a set amount of tables which decides the available time slots for booking.</p>
 */

public class Restaurant {
    // Private fields

    /** Id used for referencing by other classes. */
    private int id;

    /** Name of the restaurant. */
    private String name;

    /** Amount of tables available for bookings. */
    private int amountOfTables;

    /**
     * Creates a new {@Code Restaurant}-object with a name, id and amount of tables.

     * @param name Name of the restaurant.
     * @param id Id used for referencing by other classes.
     * @param amountOfTables Amount of tables available for booking.
     */
    public Restaurant(String name, int id, int amountOfTables) {
        this.name = name;
        this.id = id;
        this.amountOfTables = amountOfTables;
    }

    // empty constructor and setters for Jackson
    // not intended for normal use

    /**
     * Creates restaurant without any parameters.
     */
    public Restaurant() {}

    /**
     * Creates a copy of a restaurant by taking in another instance of this class.

     * @param restaurant The restaurant of which to make a copy of.
     */
    public Restaurant(Restaurant restaurant) {
        if (restaurant == null){
            this.name = null;
            this.id = -1;
            this.amountOfTables = 0;
        } else {
            this.name = restaurant.getName();
            this.id = restaurant.getId();
            this.amountOfTables = restaurant.getAmountOfTables();
        }
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
