package ui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import core.ServerCommunication;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.CustomDateObject;
import model.Restaurant;

public class BookingPageController {

    private Stage primaryStage;
    private Scene mainScene;
    private Restaurant restaurant;
    private MainPageController mainPageController;
    private Collection<String> allAvailableTimeslots = new ArrayList<>();
    private boolean currentlyInEditState = false;
    private CustomDateObject bookingToEdit = null;

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

    // There comes spotbugs from these, but Stage and Scene should not be copied,
    // therefore the spotsbugs remain for now
    public void setStage(
            App app,
            Stage primaryStage,
            Scene bookingScene,
            Scene mainScene,
            Scene loginScene) {
        this.primaryStage = primaryStage;
        this.mainScene = mainScene;
    }

    @FXML
    public void bookTable() {
        if (!checkThatAllFieldsAreFilled()) {
            return;
        }

        if (currentlyInEditState) {
            ServerCommunication.saveBooking(new CustomDateObject(date.getValue().toString(), chosenTimeToBook,
                    restaurant.getName(), mainPageController.getCurrentlyLoggedInUser().getName(), name.getText(),
                    surname.getText(), ServerCommunication.updateId("bookingId")));
            ServerCommunication.deleteBooking(bookingToEdit);
            Alert alert = new Alert(AlertType.CONFIRMATION);
            mainPageController.saveRestaurants();
            returnToMainPage();
            alert.setHeaderText("Booking sucessfully changed!");
            alert.showAndWait();
            currentlyInEditState = false;
        } else {
            ServerCommunication.saveBooking(new CustomDateObject(date.getValue().toString(), chosenTimeToBook,
                    restaurant.getName(), mainPageController.getCurrentlyLoggedInUser().getName(), name.getText(),
                    surname.getText(), ServerCommunication.updateId("bookingId")));
            Alert alert = new Alert(AlertType.CONFIRMATION);
            mainPageController.saveRestaurants();
            returnToMainPage();
            alert.setHeaderText("Table successfully booked!");
            alert.showAndWait();
        }
        resetInfo();
    }

    @FXML
    public void resetBookingInfo() {
        resetInfo();
    }

    private void resetInfo() {
        name.setText("");
        surname.setText("");
        date.setValue(null);
    }

    public void reloadTimeSlotPicker() {
        timeSlotsPicker.getItems().clear();
        timeSlotsPicker.getSelectionModel().clearSelection(); // resets logical selection
        timeSlotsPicker.setValue(null); // reset UI selection
        chosenTimeToBook = null; // Reset the chosen time so it doesn't try to save the previously selected time,
                                 // avoid "ghost selection"
        for (String time : allAvailableTimeslots) {
            timeSlotsPicker.getItems().add(time);
        }
    }

    private void removeBookedTimeSlots(String chosenDateString) {
        Collection<CustomDateObject> bookings = ServerCommunication.getBookingsByDateAndRestaurantName(chosenDateString,
                restaurant.getName());
        for (CustomDateObject customDateObject : bookings) {
            allAvailableTimeslots.remove(customDateObject.getTime());
        }
    }

    public void correctlyReloadTimeSlots(String chosenDateString) {
        removeBookedTimeSlots(chosenDateString);
        reloadTimeSlotPicker();
    }

    private boolean checkThatAllFieldsAreFilled() {
        if (name.getText().isEmpty() || surname.getText().isEmpty() || date.getValue() == null
                || timeSlotsPicker.getValue() == null) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("All fields must be filled to complete booking");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    @FXML
    public void pickDateToBook() {
        String chosenDateString;
        if (date.getValue() == null) {
            chosenDateString = "null";
        } else {
            chosenDateString = date.getValue().toString();
        }
        clearAndPopulateTimeslots();
        correctlyReloadTimeSlots(chosenDateString);
    }

    public void setMainPageController(MainPageController controller) {
        this.mainPageController = controller;
    }

    @FXML
    public void returnToMainPage() {
        resetInfo();
        setEditState(false, null); // Makes sure the state is reset in the scenario where one clicks edit then
                                   // doesn't save any changes
        primaryStage.setScene(mainScene);
        mainPageController.loadUserBookings();
        timeSlotsPicker.getSelectionModel().selectedItemProperty().removeListener((observable, oldValue, newValue) -> {
            chosenTimeToBook = newValue;
        });
    }

    public void setCurrentRestaurantDisplayed(Restaurant restaurant) {
        this.restaurant = restaurant;
        restaurantLabel.setText("Velkommen til " + restaurant.getName());
        clearAndPopulateTimeslots();

        // makes Date field un-editable so you must pick a correctly formatted date
        date.getEditor().setDisable(true);
        date.getEditor().setOpacity(1);

        // The following code disables all dates before today, so that a user cannot
        // book a date that has already passed
        // This code was generated by ChatGPT
        date.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (date.isBefore(LocalDate.now())) {
                    setDisable(true);
                }
            }
        });
        // --------------------------------- Ends here
        // Add listener for timeslotspicker only once
        timeSlotsPicker.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            chosenTimeToBook = newValue;
        });
    }

    public void clearAndPopulateTimeslots() {
        allAvailableTimeslots.clear();
        int bookingIntervalLength = (int) (120 / restaurant.getAmountOfTables());
        for (Integer i = 14; i < 24; i++) { // For simplicity we pretend that all restaurants open at 14 and close at
                                            // midnight.
            for (int j = 0; j < 60; j += bookingIntervalLength) {
                allAvailableTimeslots.add(i.toString() + ":" + String.format("%02d", j)); // formats the minute part to
                                                                                          // always be 2 digits.
            }
        }
    }

    public void setEditState(boolean state, CustomDateObject bookingToEdit) {
        currentlyInEditState = state;
        this.bookingToEdit = bookingToEdit;
    }

    public void fillFieldsWithExistingBookingInfo(String name, String surname, String date, String timeSlot) {
        this.name.setText(name);
        this.surname.setText(surname);
        this.date.setValue(LocalDate.parse(date));
        this.timeSlotsPicker.setValue(timeSlot);
    }

}
