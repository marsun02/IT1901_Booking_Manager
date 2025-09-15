package gr2515.core;

import java.util.Set;

public class User {
    
    // Private fields
    private int id;
    private String name;
    private String email;
    private String phoneNumber;


    // Constructor
    public User(int id, String name, String email, String phoneNumber){

        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }


    //Setters
    public void setId(int id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setPhoneNumber(String phonenumber){
        this.phoneNumber = phonenumber;
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