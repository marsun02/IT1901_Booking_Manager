package gr2515.ui;

import java.io.File;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import gr2515.core.Restaurant;
import gr2515.core.Table;
import gr2515.core.User;
import javafx.fxml.FXML;

public class MainPageController {
    private Collection<Restaurant> restaurants;
    private Collection<User> users;
    private int userId = 0;
    private User loggedInUser;
    private File restaurantDataFile = new File("javafx-app/data/restaurants.json");

    public MainPageController(){
        initialize();
    }

    @FXML
    public void initialize(){
        restaurants = new HashSet<>();
        users = new HashSet<>();

        // loadRestaurants();
    }
    
    public void saveRestaurants(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // enables pretty printing to json file

        try {
            // String jsonString = objectMapper.writeValueAsString(restaurant);
            objectMapper.writeValue(restaurantDataFile, restaurants);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadRestaurants(){
        // TODO: Load contents in json file to collection
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            restaurants = objectMapper.readValue(restaurantDataFile, new TypeReference<HashSet<Restaurant>>() {});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Add a user with automatically assigned id
    public void addUser(String name, String email, String phoneNumber, String password){
        users.add(new User(userId, name, email, phoneNumber, password));
        userId++;
    }

    // Add table to a restaurant of choice with automatically assigned id
    public void addTableToRestaurant(Restaurant restaurant, int spots){
        restaurant.addTable(new Table(restaurant.getNextTableId(), spots));
    }

    // books table for the logged in user
    public void bookTableAsUser(Table table, Restaurant restaurant){
        if (!users.contains(loggedInUser)){
            // TODO: handle event that user does not exist
        }

        if (!restaurant.getAvailableTables().contains(table)){
            // TODO: handle event that restaurant doesn't contain table
        }

        // Set bookedTime to new Date() temporarily before time functionality is implemented
        table.book(loggedInUser, new Date());
    }

    // log in
    public void logIn(String userName, String password){
        User userLoggingIn = null;
        
        for (User user : users){
            if (user.checkNamePassword(userName, password)){
                userLoggingIn = user;
            }
        }

        if (loggedInUser == null){
            // TODO: handle event that no user matches the username and password
        }

        if (this.loggedInUser == userLoggingIn){
            // TODO: handle event that user that tries to log in is already logged in
        }
        
        if (!users.contains(userLoggingIn)){
            // TODO: handle event that user that tries to log in doesn't exist
        }

        this.loggedInUser = userLoggingIn;
    }

    // log out
    public void logOut(){
        this.loggedInUser = null;
    }

    // public static void main(String[] args) {
    //     MainPageController tesController = new MainPageController();
    //     // System.out.println(tesController);
    //     // Restaurant test0 = new Restaurant("test0", 0);
    //     // test0.addTable(new Table(0, 0));
    //     // Restaurant test1 = new Restaurant("test1", 1);
    //     // tesController.restaurants.add(test0);
    //     // tesController.restaurants.add(test1);
    //     // tesController.saveRestaurants();

    //     tesController.loadRestaurants();
    //     for (Restaurant restaurant : tesController.restaurants){
    //         System.out.println(restaurant);
    //     }
        
    // }
}
