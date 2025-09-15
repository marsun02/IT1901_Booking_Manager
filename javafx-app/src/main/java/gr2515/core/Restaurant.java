package gr2515.core;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
<<<<<<< HEAD

public class Restaurant {
    // Private fields
    private Collection<Table> tables = new HashSet<>();
    private int id;
    private String name;

    // Constructor
    public Restaurant(String name, int id){
        this.name = name;
        this.id = id;
        this.tables = new HashSet<>();
    }

    // Getters
    public Collection<Table> getTables(){
        return new HashSet<>(this.tables);
=======
import java.util.UUID;

public class Restaurant {
    // Private fields
    private static int counter = 1;
    private Collection<Table> tables = new HashSet<>();
    private int id;
    private String name;
    private BookingManager bookingManager;

    // Constructor
    public Restaurant(String name, BookingManager bookingManager){
        this.name = name;
        this.id = counter ++;
        this.tables = new HashSet<>();
        this.bookingManager = bookingManager;
    }
    // Getters and Setters
    public Collection<Table> getTables(){
        return new ArrayList<>(this.tables);
>>>>>>> c4e3eb31cf3b698143b5ae95cc215a5ac5988a84
    }

    public String getName() {
        return name;
    }
    
<<<<<<< HEAD
    public int getId(){
        return this.id;
    }

    // Adds table to collection of tables
    public void addTable(Table table){
        this.tables.add(table);
    }
    
    // Puts all the available tables in a list and returns them
    public Collection<Table> getAvailableTables() {
        Collection<Table> available = new HashSet<>();
        for (Table table : tables) {
            if (table.getStatus()){
=======

    public BookingManager getBookingManager() {
        return bookingManager;
    }
    
    public void setBookingManager(BookingManager bookingManager) {
        this.bookingManager = bookingManager;
    }
    public int getId(){
        return this.id;
    }
    // Adds table to set of tables
    public void addTable(Table table){
        this.tables.add(table);
    }
   // Checks if the table with the given ID is available in the specified time range and returns true if it is, false otherwise.
    public boolean isTableAvailable(int tableId, LocalDateTime startTime, LocalDateTime endTime){
        Table table = getTableById(tableId);
        if (table != null) {
            return bookingManager.isTableAvailable(table, startTime, endTime);
        }
        return false;
    }

    
    // Puts all the available tables in a list and returns them
    public Collection<Table> getAvailableTables(LocalDateTime startTime, LocalDateTime endTime) {
        Collection<Table> available = new ArrayList<>();
        for (Table table : tables) {
            if (bookingManager.isTableAvailable(table, startTime, endTime)) {
>>>>>>> c4e3eb31cf3b698143b5ae95cc215a5ac5988a84
                available.add(table);
            }
        }
        return available;
    }
<<<<<<< HEAD

    // Returns the table of a given id
    public Table getTableById(int tableId){
        for (Table table : tables){
            if (table.getId() == tableId){
                return table;
=======
    // Books a table by its ID for the given user and time range with enough spots.
public Booking bookTable(User user, int tableId, LocalDateTime startTime, LocalDateTime endTime, int persons){
    Table table = getTableById(tableId);
    if (table != null){
        if (table.getSpots() >= persons) {
            return bookingManager.makeBooking(user, table, startTime, endTime);
        } else {
            // I'm not sure if we need to have this one, but it could be nice to know the reason why you couldn't book the table
            throw new IllegalArgumentException("There's not enough spots for " + persons + " at this table");
        }
    }
    return null;
}

    // Returns the table of a given id
    private Table getTableById(int tableId){
        for (Table t : tables){
            if (t.getId() == tableId){
                return t;
>>>>>>> c4e3eb31cf3b698143b5ae95cc215a5ac5988a84
            }
        }
        return null;
    }
<<<<<<< HEAD
=======
    // Returns the first available table for the given time if enough spots, or null if none.
public Booking bookAnyAvailableTable(User user, LocalDateTime startTime, LocalDateTime endTime, int persons) {
    for (Table table : tables) { // Iterates through all the tables
        if (table.getSpots() >= persons && 
            bookingManager.isTableAvailable(table, startTime, endTime)) { // Checks if the table is both available and has enough spots
            return bookingManager.makeBooking(user, table, startTime, endTime); // Books the table if it's the case
        }
    }
    return null; // Otherwise return null
}

   
>>>>>>> c4e3eb31cf3b698143b5ae95cc215a5ac5988a84
}
