package gr2515.ui;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashSet;

import gr2515.App;
import gr2515.core.ControllerHelpFunctions;
import gr2515.core.Restaurant;
import gr2515.core.RestaurantInitialization;
import gr2515.core.User;
import gr2515.persistence.FileManagement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainPageController {
    private Collection<Restaurant> restaurants;
    private Collection<User> users; // User functionality may be added later
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
        restaurants = RestaurantInitialization.initializeAllRestaurants();
        saveRestaurants();
    }
    
    // saves the current restaurants in restaurants hashSet to file
    public void saveRestaurants(){
        FileManagement.saveRestaurants(restaurantDataFile, restaurants);
    }

    // loads restaurants from file to hashSet
    public void loadRestaurants(){
        restaurants = FileManagement.loadRestaurants(restaurantDataFile);
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
        return ControllerHelpFunctions.getRestaurantByFxid(fxid, restaurants);
    }

    @FXML
    public void enterRestaurantPage(ActionEvent event){
        primaryStage.setScene(bookingScene);
        Button source = (Button) event.getSource();
        bookingPageController.setCurrentRestaurantDisplayed(getRestaurantByFxid(source.getId()));
        bookingPageController.setCorrectTables(getRestaurantByFxid(source.getId()).getTables());
    }

    // Updates any restaurant-specific changes made in the booking page
    public void updateRestaurantsInformation(Restaurant bookedRestaurant){
        restaurants = ControllerHelpFunctions.updateRestaurantsInformation(bookedRestaurant, restaurants);
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
