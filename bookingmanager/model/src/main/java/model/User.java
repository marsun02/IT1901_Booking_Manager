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


    // Constructor
    public User(int id, String name, String email, String phoneNumber, String password){

        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
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

    public boolean checkNamePassword(String name, String password){
        return (this.name.toLowerCase().equals(name.toLowerCase()) && this.password.equals(password));
    }
}