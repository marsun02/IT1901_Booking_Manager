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
        this.name = restaurant.getName();
        this.id = restaurant.getId();
        this.amountOfTables = restaurant.getAmountOfTables();
    }

    /**
     * Sets the name of the restaurant.

     * @param name New name of restaurant.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * gives the restaurant a new id.

     * @param id New id.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Changes the restaurants amount of available tables.

     * @param amount New amound of available tables.
     */
    public void setAmountOfTables(int amount) {
        this.amountOfTables = amount;
    }

    /**
     * Gets the name of the restaurant.

     * @return the restaurants name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the restaurants id.

     * @return restaurants id.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the amount of tables available for booking.

     * @return the amount of tables in this restaurant.
     */
    public int getAmountOfTables() {
        return amountOfTables;
    }
}
