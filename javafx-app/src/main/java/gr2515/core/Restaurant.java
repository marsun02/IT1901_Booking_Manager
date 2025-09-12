package gr2515.core;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class Restaurant {
    private Collection<Table> tables = new ArrayList();
    private UUID id;
    private String name;
    private BookingManager bookingManager;

    public Restaurant(String name){
        this.name = name;
        this.id = UUID.randomUUID();
        this.tables = new ArrayList<>();
    }

    public Collection<Table> getTables(){
        return new ArrayList<>(this.tables);
    }

    public void addTable(Table table){
        this.tables.add(table);
    }

    public boolean isTableAvailable(int tableId){
        Table table = findTableById(tableId);
    }

    public BookingManager getBookingManager(){
        return this.bookingManager;
    }
    // getAvailableTables puts all the avaiable tables in a list and returns them 
    public Collection<Table> getAvailableTables(LocalDateTime startTime, LocalDateTime endTime) {
        Collection<Table> available = new ArrayList<>();
        for (Table table : tables) {
            if (bookingManager.isTableAvailable(table, startTime, endTime)) {
                available.add(table);
            }
        }
        return available;
    }
    // Books a table by its ID for the given user and time range.
    public Booking bookTable(User user, int tableId, LocalDateTime stratTime, LocalDateTime endTime){
        Table table = findTableById(tableId);
        if (table != null){
            return bookingManager.makeBooking(user, table, stratTime, endTime);
        }
        return null;
    }

    private Table findTableById(int id){
        for (Table t : tables){
            if (t.getId() == id){
                return t;
            }
        }
        return null;
    }
    // Returns the first available table for the given time, or null if none.
    public Booking bookAnyAvailableTable(User user, LocalDateTime startTime, LocalDateTime endTime) {
        for (Table table : tables) {
            if (bookingManager.isTableAvailable(table, startTime, endTime)) {
                return bookingManager.makeBooking(user, table, startTime, endTime);
            }
        }
        return null; 
    }
   
}
