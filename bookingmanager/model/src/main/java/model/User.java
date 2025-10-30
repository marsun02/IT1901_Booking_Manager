package model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    
    // Private fields
    private int id;
    private String name;
    private String email;

    @JsonProperty // tells jackson to include password since we don't wanna expose it with a getter
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

    // Empty constructor and setters for Jackson
    public User(){

    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setBookedTables(Set<Table> bookedTables) {
        this.bookedTables = bookedTables;
    }
    

    // Getters


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
        if (bookedTables.contains(table) && table.isBooked()){
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
        return (this.name.toLowerCase().equals(name.toLowerCase()) && this.password.equals(password));
    }
}