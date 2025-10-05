package ui;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashSet;

import core.ControllerHelpFunctions;
import core.RestaurantInitialization;
import persistence.FileManagement;
import persistence.Restaurant;
import persistence.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainPageController {
    private Collection<Restaurant> restaurants;
    // private Collection<User> users; // User functionality may be added later
    Path path = Paths.get("../persistence/src/main/resources/persistence/restaurants.json");
    File restaurantDataFile = path.toFile(); // File to store restaurant data

    // private App app;
    private Stage primaryStage;
    private Scene bookingScene;
    // private Scene mainScene;
    private BookingPageController bookingPageController;

    @FXML
    private AnchorPane anchorpane;

    public MainPageController(){
        initialize();
    }

    @FXML
    public void initialize(){
        restaurants = new HashSet<>();
        // users = new HashSet<>();

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

    // There comes spotbugs from these, but Stage and Scene should not be copied, therefore the spotsbugs remain for now
    public void setStage(
        App app,
        Stage primaryStage,
        Scene bookingScene,
        Scene mainScene
    ) {
    //   this.app = app;
        this.primaryStage = primaryStage;
        this.bookingScene = bookingScene;
    //   this.mainScene = mainScene;
    }

    public void setBookingPageController(BookingPageController controller){
        this.bookingPageController = controller;
    }

    @FXML
    public void enterRestaurantPage(ActionEvent event){
        Button source = (Button) event.getSource();
        Restaurant restaurant = ControllerHelpFunctions.getRestaurantByFxid(source.getId(), restaurants);
        if (restaurant == null){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("There was an issue when loading restaurant page");
            alert.showAndWait();
            return;
        }
        primaryStage.setScene(bookingScene);
        bookingPageController.setCurrentRestaurantDisplayed(restaurant);
        // bookingPageController.setCorrectTables(restaurant.getTables()); // Will be used when tables can be chosen in the booking page
    }

    // Updates any restaurant-specific changes made in the booking page
    public void updateRestaurantsInformation(Restaurant bookedRestaurant){
        restaurants = ControllerHelpFunctions.updateRestaurantsInformation(bookedRestaurant, restaurants);
        saveRestaurants();
    }
}
