package ui;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import persistence.CustomDateObject;
import persistence.FileManagement;
import persistence.Restaurant;
import persistence.Table;
import persistence.User;

public class BookingPageController {

    private Stage primaryStage;
    private Scene mainScene;
    private Restaurant restaurant;
    private MainPageController mainPageController;
    private Collection<String> allAvailableTimeslots = new ArrayList<>(); 

    final Path path = Paths.get("../persistence/src/main/resources/persistence/allBookings.json");
    final File allBookingsFile = path.toFile(); // File to store bookings
    // private Collection<Table> tables; // Will be used when tables can be chosen in the booking page

    @FXML
    Label restaurantLabel;

    @FXML
    TextField name;

    @FXML
    TextField surname;

    @FXML
    DatePicker date;

    @FXML
    ChoiceBox<String> timeSlotsPicker;

    private String chosenTimeToBook;
 
    // There comes spotbugs from these, but Stage and Scene should not be copied, therefore the spotsbugs remain for now
    public void setStage(
        App app,
        Stage primaryStage,
        Scene bookingScene,
        Scene mainScene
    ) {
        this.primaryStage = primaryStage;
        this.mainScene = mainScene;
    }

    @FXML
    public void bookTable(){
        Table firstAvailableTable = null;
        
        for (Table table : restaurant.getAvailableTables()) { 
            firstAvailableTable = table;
            break;
        }

        // TODO: Make sure that all fields must be filled before booking
        for (Table table : restaurant.getTables()) {
            if (table.equals(firstAvailableTable)){
                table.book(new User(0, name.getText() + " " + surname.getText(), null, null, null), null); // TODO: Add actual booked time
                FileManagement.saveBooking(allBookingsFile, new CustomDateObject(date.getValue().toString(), chosenTimeToBook, restaurant.getName()));
                Alert alert = new Alert(AlertType.CONFIRMATION);
                mainPageController.saveRestaurants();
                returnToMainPage();
                alert.setHeaderText("Table successfully booked!");
                alert.showAndWait();
                // reset so it doesn't keep information for next time you enter page. Using the FXML function resetBookingInfo crashed the app.
                resetInfo();
                return;
            }
        }

        // this alert is only here since the user cannot see the available time slots yet
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setHeaderText("Unfortunately there were no available tables");
        alert.showAndWait();
    }

    @FXML
    public void resetBookingInfo(){
        resetInfo();
    }

    private void resetInfo(){
        name.setText("");
        surname.setText("");
        date.setValue(null);
    }

    @FXML
    public void pickDateToBook(){
        clearAndPopulateTimeslots();
        String chosenDateString;
        if (date.getValue() == null){
            chosenDateString = "null";
        } else{
            chosenDateString = date.getValue().toString();
        }
        Collection<CustomDateObject> bookings = FileManagement.loadBookingsForSpecificDate(allBookingsFile, chosenDateString, restaurant.getName());
        for (CustomDateObject customDateObject : bookings) {
                allAvailableTimeslots.remove(customDateObject.getTime());
        }
        timeSlotsPicker.getItems().clear();
        for (String time : allAvailableTimeslots) {
            timeSlotsPicker.getItems().add(time);
        }
        timeSlotsPicker.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {chosenTimeToBook = newValue;});
    }



    public void setMainPageController(MainPageController controller){
        this.mainPageController = controller;
    }

    @FXML
    public void returnToMainPage(){
        primaryStage.setScene(mainScene);
    }

    public void setCurrentRestaurantDisplayed(Restaurant restaurant){
        this.restaurant = restaurant;
        restaurantLabel.setText("Velkommen til " + restaurant.getName());
    }

    public void clearAndPopulateTimeslots(){ // Chose to hardcode timeslots for now
        allAvailableTimeslots.clear();
        for (Integer i = 14; i < 24; i++) { // 14 is the opening hour, 24 closing hour
            allAvailableTimeslots.add(i.toString() + ".00");
            allAvailableTimeslots.add(i.toString() + ".15");
            allAvailableTimeslots.add(i.toString() + ".30");
            allAvailableTimeslots.add(i.toString() + ".45");
        }
    }

    // Will be used when tables can be chosen in the booking page
    // public void setCorrectTables(Collection<Table> tables){ 
    //     this.tables = tables;
    // }

}
