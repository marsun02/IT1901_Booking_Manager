package gr2515.ui;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import gr2515.App;
import gr2515.core.Restaurant;
import gr2515.core.Table;
import gr2515.core.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainPageController {
    private Collection<Restaurant> restaurants;
    private Collection<User> users;
    private int userId = 0;
    private User loggedInUser;
    String[] restaurantNames = {"Frati", "Szechuan", "Burger King", "Barbecuez", "McDonalds", "Steak House"};
    Path path = Paths.get("data", "restaurants.json");
    private final File restaurantDataFile = path.toFile(); // Universal file path we can use to access BookedTables.txt

    private App app;
    private Stage primaryStage;
    private Scene bookingScene;
    private Scene mainScene;
    private BookingPageController bookingPageController;

    @FXML
    private AnchorPane anchorpane;

    public MainPageController(){
        initialize();
    }

    @FXML
    public void initialize(){
        restaurants = new HashSet<>();
        users = new HashSet<>();

        loadRestaurants();

        // If the data file does not contain the correct amount of restaurants, it creates 6 new ones
        if (restaurants.size() != 6){ 
            initializeAllRestaurants();
        }
    }

    private void initializeAllRestaurants(){
        restaurants.clear();
        for (int i = 0; i < restaurantNames.length; i++) {
            Restaurant restaurant = new Restaurant(restaurantNames[i], i);
            restaurants.add(restaurant);
            for (int j = 0; j < 10; j++) {
                addTableToRestaurant(restaurant, 6);
            }
        }
        saveRestaurants();
    }
    
    // saves the current restaurants in restaurants hashSet to file
    public void saveRestaurants(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // enables pretty printing to json file

        try {
            objectMapper.writeValue(restaurantDataFile, restaurants);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // loads restaurants from file to hashSet
    public void loadRestaurants(){
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

    public void setStage(
        App app,
        Stage primaryStage,
        Scene bookingScene,
        Scene mainScene
    ) {
      this.app = app;
      this.primaryStage = primaryStage;
      this.bookingScene = bookingScene;
      this.mainScene = mainScene;
    }

    public void setBookingPageController(BookingPageController controller){
        this.bookingPageController = controller;
    }
    
    public Restaurant getRestaurantByFxid(String fxid){ 
        int id = fxid.charAt(fxid.length()- 1) - '0'; // fetches last character in fxid as integer

        for (Restaurant restaurant : restaurants){
            if (restaurant.getId() == id){
                return restaurant;
            }
        }

        // TODO: This event needs to be handled as the code crashes if restaurant is null
        return null;
    }

    @FXML
    public void enterRestaurantPage(ActionEvent event){
        primaryStage.setScene(bookingScene);
        Button source = (Button) event.getSource();
        bookingPageController.setCurrentRestaurantDisplayed(getRestaurantByFxid(source.getId()));
        bookingPageController.setCorrectTables(getRestaurantByFxid(source.getId()).getTables());
    }

    public void updateRestaurantsInformation(Restaurant bookedRestaurant){
        for (Restaurant restaurant : restaurants) {
            if (restaurant.getName().equals(bookedRestaurant.getName())){
                restaurant = bookedRestaurant;
            }
        }
        saveRestaurants();
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
