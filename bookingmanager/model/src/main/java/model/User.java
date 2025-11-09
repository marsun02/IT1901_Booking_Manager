package model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class representing a user. The user can log in to the app and make bookings.
 * The user can also see all the bookings made by themselves.
 */
public class User {

    // Private fields
    private int id;
    private String name;
    private String email;
    @JsonProperty // tells jackson to include password since we don't wanna expose it with a
    // getter
    private String password;
    private String phoneNumber;

    /**
     * Creates a user and sets the id, name, email, phone number and password.

     * @param id id used for referencing by other classes.
     * @param name name of the user.
     * @param email email of the user.
     * @param phoneNumber phone number of the user.
     * @param password password used to log in.
     */
    public User(int id, String name, String email, String phoneNumber, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    /**
     * Creates a user object without setting the parameters.
     */
    public User() {

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

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Checks the username and password that are taken in with the actual username and password.
     * Used when logging in.

     * @param name username filled in to login form.
     * @param password password filled in to login form.
     * @return boolean representing whether or not the username and password is matching.
     */
    public boolean checkNamePassword(String name, String password) {
        if (name == null || password == null) {
            return false;
        }
        return (this.name.toLowerCase().equals(name.toLowerCase()) && this.password.equals(password));
    }
}