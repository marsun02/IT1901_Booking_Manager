package ui;

import core.ControllerHelpFunctions;
import core.RestaurantInitialization;
import core.ServerCommunication;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.CustomDateObject;
import model.Restaurant;
import model.User;
import persistence.FileManagement;

public class MainPageController {
    Collection<Restaurant> restaurants;
    // private Collection<User> users; // User functionality may be added later
    final Path path = Paths.get("../persistence/src/main/resources/persistence/restaurants.json");
    final File restaurantDataFile = path.toFile(); // File to store restaurant data

    final Path path2 = Paths.get("../persistence/src/main/resources/persistence/allBookings.json");
    final File allBookingsFile = path2.toFile(); // File to store bookings

    private Stage primaryStage;
    private Scene bookingScene;
    private Scene loginScene;
    private BookingPageController bookingPageController;
    private User currentlyLoggedInUser = new User();

    @FXML
    private AnchorPane anchorpane;
    
    @FXML
    private Label loggedInUserLabel;

    @FXML
    private TableView<CustomDateObject> bookingOverview;

    @FXML
    private TableColumn<CustomDateObject, ImageView> tableImage;

    @FXML 
    private TableColumn<CustomDateObject, String> tableName;

    @FXML
    private TableColumn<CustomDateObject, String> tableDate;

    @FXML
    private TableColumn<CustomDateObject, String> tableTime;

    @FXML
    private TableColumn<CustomDateObject, Button> tableEdit;

    @FXML
    private TableColumn<CustomDateObject, Button> tableDelete;

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
        // FileManagement.saveRestaurants(restaurantDataFile, restaurants);
        ServerCommunication.saveAllRestaurants(restaurants);
    }

    // loads restaurants from file to hashSet
    public void loadRestaurants(){
        // restaurants = FileManagement.loadRestaurants(restaurantDataFile);
        restaurants = ServerCommunication.getAllRestaurants();
    }

    // There comes spotbugs from these, but Stage and Scene should not be copied, therefore the spotsbugs remain for now
    public void setStage(
        App app,
        Stage primaryStage,
        Scene bookingScene,
        Scene mainScene,
        Scene loginScene
    ) {
        this.primaryStage = primaryStage;
        this.bookingScene = bookingScene;
        this.loginScene = loginScene;
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

    public void setcurrentlyLoggedInUser(User loggedInUser){
        currentlyLoggedInUser = loggedInUser;
    }

    public void setLoggedInUserLabel(String name){
        loggedInUserLabel.setText(name);
    }

    @FXML
    public void logOutUser(){
        this.currentlyLoggedInUser = null;
        primaryStage.setScene(loginScene);
    }

    
    public User getCurrentlyLoggedInUser(){
        return currentlyLoggedInUser;
    }
   
    public void loadUserBookings(){
        bookingOverview.getItems().clear();
        bookingOverview.getColumns().forEach(col -> col.setStyle("-fx-alignment: CENTER;"));
        // TODO: Add image instead of text
        tableImage.setCellValueFactory(cellData -> {
            final Image editImage = new Image(getClass().getResourceAsStream("/ui/" + cellData.getValue().getRestaurantName() + ".png"));
            final ImageView editImageView = new ImageView(editImage);
            editImageView.setFitWidth(100);
            editImageView.setFitHeight(100);
            // Button imageButton = new Button("", editImageView);
            return new ReadOnlyObjectWrapper<>(editImageView);


        });
        tableName.setCellValueFactory(cellData -> 
        new ReadOnlyObjectWrapper<>(cellData.getValue().getBookedName()));
        tableDate.setCellValueFactory(cellData -> 
        new ReadOnlyObjectWrapper<>(cellData.getValue().getDate()));
        tableTime.setCellValueFactory(cellData -> 
        new ReadOnlyObjectWrapper<>(cellData.getValue().getTime()));
        tableEdit.setCellValueFactory(cellData -> {
            final Image editImage = new Image(getClass().getResourceAsStream("/ui/editIcon.png"));
            final ImageView editImageView = new ImageView(editImage);
            editImageView.setFitWidth(20);
            editImageView.setFitHeight(20);
            Button editButton = new Button("", editImageView);
            editButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    // CustomDateObject bookingToEdit = FileManagement.loadSpecificBooking(allBookingsFile, cellData.getValue().getId());
                    CustomDateObject bookingToEdit = ServerCommunication.getBookingById(cellData.getValue().getId().toString());
                    bookingPageController.setEditState(true, bookingToEdit);
                    bookingPageController.fillFieldsWithExistingBookingInfo(cellData.getValue().getFirstName(), cellData.getValue().getLastName(), cellData.getValue().getDate(), cellData.getValue().getTime());
                    for (Restaurant restaurant : restaurants){
                        if (restaurant.getName().equals(cellData.getValue().getRestaurantName())){
                            bookingPageController.setCurrentRestaurantDisplayed(restaurant);
                            break;
                        }
                    }
                    primaryStage.setScene(bookingScene);
                }
            });
            return new ReadOnlyObjectWrapper<>(editButton);
        });
        tableDelete.setCellValueFactory(cellData -> {
            final Image deleteImage = new Image(getClass().getResourceAsStream("/ui/deleteIcon.png"));
            final ImageView deleteImageView = new ImageView(deleteImage);
            deleteImageView.setFitWidth(20);
            deleteImageView.setFitHeight(20);
            Button deleteButton = new Button("", deleteImageView);
            deleteButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    // FileManagement.deleteBooking(allBookingsFile, cellData.getValue());
                    ServerCommunication.deleteBooking(cellData.getValue());
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setHeaderText("Booking deleted");
                    alert.showAndWait();
                    loadUserBookings();
                }
            });
            return new ReadOnlyObjectWrapper<>(deleteButton);
        });
        
        // List<CustomDateObject> bookings = FileManagement.loadBookingsForSpecificUser(allBookingsFile, currentlyLoggedInUser.getName());
        List<CustomDateObject> bookings = ServerCommunication.getBookingsByUser(currentlyLoggedInUser.getName());
        ControllerHelpFunctions.sortBookings(bookings);
        
        for (CustomDateObject booking : bookings) {
            bookingOverview.getItems().add(booking);
        }
    }
}
