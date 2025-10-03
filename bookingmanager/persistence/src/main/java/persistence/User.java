package persistence;

import java.util.HashSet;
import java.util.Set;

public class User {
    
    // Private fields
    private int id;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private Set<Table> bookedTables;


    // Constructor
    public User(int id, String name, String email, String phoneNumber, String password){

        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.bookedTables = new HashSet<>();
        this.password = password;
    }

    //Getters
    
    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getEmail(){
        return email;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }

    public Set<Table> getBookedTables(){
        return new HashSet<>(bookedTables);
    }

    public void addTable(Table table){
        if (!bookedTables.contains(table) && !table.isBooked()){
            // TODO: handle the event that table is already booked by this or another user
            return;
        }

        this.bookedTables.add(table);
    }

    public void removeTable(Table table){
        if (!this.getBookedTables().contains(table)){
            // TODO: handle event that user tries to remove a table that is not booked
        }

        this.bookedTables.remove(table);
    }

    public boolean checkNamePassword(String name, String password){
        return (this.name.equals(name) && this.password.equals(password));
    }
}