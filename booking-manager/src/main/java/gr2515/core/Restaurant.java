package gr2515.core;

import java.util.Collection;
import java.util.HashSet;

public class Restaurant {
    // Private fields
    private Collection<Table> tables = new HashSet<>();
    private int id;
    private String name;
    private int tableId = 0;

    // Constructor
    public Restaurant(String name, int id){
        this.name = name;
        this.id = id;
        this.tables = new HashSet<>();
    }

    // empty constructor and setters for Jackson
    // not intended for normal use

    public Restaurant(){

    }

    public void setName(String name){
        this.name = name;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setNextTableId(int nextTableId){
        this.tableId = nextTableId;
    }

    // Getters
    public Collection<Table> getTables(){
        return new HashSet<>(this.tables);
    }

    public String getName() {
        return name;
    }
    
    public int getId(){
        return this.id;
    }

    // Adds table to collection of tables
    public void addTable(int spots){
        Table table = new Table(this.tableId, spots);
        this.tables.add(table);
        incrementTableId();
    }
    
    // Puts all the available tables in a list and returns them
    public Collection<Table> getAvailableTables() {
        Collection<Table> available = new HashSet<>();
        for (Table table : tables) {
            if (!table.isBooked()){
                available.add(table);
            }
        }
        return available;
    }

    // Returns the table of a given id
    public Table getTableById(int tableId){
        for (Table table : tables){
            if (table.getId() == tableId){
                return table;
            }
        }
        return null;
    }

    public int getNextTableId(){
        return tableId;
    }

    private void incrementTableId(){
        tableId++;
    }
}
