package ui;

import core.ControllerHelpFunctions;
import core.RestaurantInitialization;
import core.ServerCommunication;
import java.util.ArrayList;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.CustomDateObject;
import model.Restaurant;
import model.User;

public class MainPageController {
    Collection<Restaurant> restaurants;

    Stage primaryStage;
    Scene bookingScene;
    Scene loginScene;
    BookingPageController bookingPageController;
    private User currentlyLoggedInUser = new User();
    private final static String SUPER_USER_NAME = "Admin"; // Username of the user that has access to all bookings made in the
    // entire app

    @FXML Label loggedInUserLabel;

    @FXML Label yourBookingsLabel;

    @FXML TableView<CustomDateObject> bookingOverview;

    @FXML TableColumn<CustomDateObject, ImageView> tableImage;

    @FXML TableColumn<CustomDateObject, String> tableName;

    @FXML TableColumn<CustomDateObject, String> tableDate;

    @FXML TableColumn<CustomDateObject, String> tableTime;

    @FXML TableColumn<CustomDateObject, Button> tableEdit;

    @FXML TableColumn<CustomDateObject, Button> tableDelete;

    public MainPageController() {
        initialize();
    }

    @FXML
    public void initialize() {
        restaurants = new HashSet<>();

        loadRestaurants();

        // If the data file does not contain the correct amount of restaurants, it creates 6 new ones
        if (restaurants.size() != 6) {
            initializeAllRestaurants();
        }
    }

    private void initializeAllRestaurants() {
        restaurants = RestaurantInitialization.initializeAllRestaurants();
        saveRestaurants();
    }

    // saves the current restaurants in restaurants hashSet to file
    public void saveRestaurants() {
        ServerCommunication.saveAllRestaurants(restaurants);
    }

    // loads restaurants from file to hashSet
    public void loadRestaurants() {
        restaurants = ServerCommunication.getAllRestaurants();
    }

    // There comes spotbugs from these, but Stage and Scene should not be copied,
    // therefore the spotsbugs remain for now
    public void setStage(
            App app,
            Stage primaryStage,
            Scene bookingScene,
            Scene mainScene,
            Scene loginScene) {
        this.primaryStage = primaryStage;
        this.bookingScene = bookingScene;
        this.loginScene = loginScene;
    }

    public void setBookingPageController(BookingPageController controller) {
        this.bookingPageController = controller;
    }

    @FXML
    public void enterRestaurantPage(ActionEvent event) {
        Button source = (Button) event.getSource();
        Restaurant restaurant = ControllerHelpFunctions.getRestaurantByFxid(source.getId(), restaurants);
        if (restaurant == null) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("There was an issue when loading restaurant page");
            alert.showAndWait();
            return;
        }
        bookingPageController.setCurrentRestaurantDisplayed(restaurant);
        bookingPageController.reloadTimeSlotPicker();

        primaryStage.setScene(bookingScene);
    }

    public void setcurrentlyLoggedInUser(User loggedInUser) {
        currentlyLoggedInUser = loggedInUser;
    }

    public void setLoggedInUserLabel(String name) {
        loggedInUserLabel.setText(name);
    }

    @FXML
    public void logOutUser() {
        this.currentlyLoggedInUser = null;
        primaryStage.setScene(loginScene);
    }

    public User getCurrentlyLoggedInUser() {
        return currentlyLoggedInUser;
    }

    public Collection<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void loadUserBookings() {
        bookingOverview.getItems().clear();
        bookingOverview.getColumns().forEach(col -> col.setStyle("-fx-alignment: CENTER;"));

        tableImage.setCellValueFactory(cellData -> {
            final Image editImage = new Image(
                    getClass().getResourceAsStream("/ui/" + cellData.getValue().getRestaurantName() + ".png"));
            final ImageView editImageView = new ImageView(editImage);
            editImageView.setFitWidth(100);
            editImageView.setFitHeight(100);
            return new ReadOnlyObjectWrapper<>(editImageView);
        });

        tableName.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getBookedName()));
        tableDate.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getDate()));
        tableTime.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getTime()));

        tableEdit.setCellValueFactory(cellData -> {
            final Image editImage = new Image(getClass().getResourceAsStream("/ui/editIcon.png"));
            final ImageView editImageView = new ImageView(editImage);
            editImageView.setFitWidth(20);
            editImageView.setFitHeight(20);
            Button editButton = new Button("", editImageView);

            // ✅ Added fx:id for testing
            editButton.setId("editButton_" + cellData.getValue().getId());

            editButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    for (Restaurant restaurant : restaurants) {
                        if (restaurant.getName().equals(cellData.getValue().getRestaurantName())) {
                            bookingPageController.setCurrentRestaurantDisplayed(restaurant);
                            break;
                        }
                    }
                    CustomDateObject bookingToEdit = ServerCommunication
                            .getBookingById(cellData.getValue().getId().toString());
                    bookingPageController.setEditState(true, bookingToEdit);
                    bookingPageController.correctlyReloadTimeSlots(bookingToEdit.getDate());
                    bookingPageController.fillFieldsWithExistingBookingInfo(cellData.getValue().getFirstName(),
                            cellData.getValue().getLastName(), cellData.getValue().getDate(),
                            cellData.getValue().getTime());
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

            // ✅ Added fx:id for testing
            deleteButton.setId("deleteButton_" + cellData.getValue().getId());

            deleteButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    ServerCommunication.deleteBooking(cellData.getValue());
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setHeaderText("Booking deleted");
                    alert.showAndWait();
                    loadUserBookings();
                }
            });
            return new ReadOnlyObjectWrapper<>(deleteButton);
        });

        List<CustomDateObject> bookings;

        if (currentlyLoggedInUser.getName().equalsIgnoreCase(SUPER_USER_NAME)) {
            bookings = new ArrayList<>(ServerCommunication.getAllBookings());
            yourBookingsLabel.setText("ALL BOOKINGS");
        } else {
            bookings = ServerCommunication.getBookingsByUser(currentlyLoggedInUser.getName());
            yourBookingsLabel.setText("YOUR BOOKINGS");
        }

        ControllerHelpFunctions.sortBookings(bookings);

        for (CustomDateObject booking : bookings) {
            bookingOverview.getItems().add(booking);
        }
    }
}