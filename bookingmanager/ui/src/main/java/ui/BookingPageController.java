package ui;

import core.ServerCommunication;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.CustomDateObject;
import model.Restaurant;

/**
 * Controller class for booking page in the application
 * Handles all logic related to making a booking reservation, including displaying available
 * time slots, validating user input, saving new bookings, editing 
 * existing bookings, and navigating back to the main page.
 */

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

    /**
     * Sets this controllers stage and scene to given parameters.

     * @param app           reference to the main application
     * @param primaryStage  the primary stage of the application
     * @param bookingScene  the booking page
     * @param mainScene     the main page
     * @param loginScene    the login page
     */
    public void setStage(
            App app,
            Stage primaryStage,
            Scene bookingScene,
            Scene mainScene,
            Scene loginScene) {
        this.primaryStage = primaryStage;
        this.mainScene = mainScene;
    }

    /**
     * Handles the booking process.
     * If the user is editing an existing booking, the old one is replaced with
     * the new details. Otherwise, a new booking is created. After saving, the
     * method resets the form and returns to the main page.
     */
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

    /**
     * Resets all booking input fields.
     */
    @FXML
    public void resetBookingInfo() {
        resetInfo();
    }

    /**
     * Clears the text fields and date picker selections.
     */
    private void resetInfo() {
        name.setText("");
        surname.setText("");
        date.setValue(null);
    }

    /**
     * Refreshes the list of available time slots in the time slot picker.
     * Clears any previous selections to prevent unintended booking of old time slots.
     */
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

    /**
     * Removes time slots that are already booked for a given date and restaurant.

     * @param chosenDateString the selected date as a string
     */
    private void removeBookedTimeSlots(String chosenDateString) {
        Collection<CustomDateObject> bookings = ServerCommunication.getBookingsByDateAndRestaurantName(chosenDateString,
                restaurant.getName());
        for (CustomDateObject customDateObject : bookings) {
            allAvailableTimeslots.remove(customDateObject.getTime());
        }
    }

    /**
     * Updates available time slots for the selected date by removing already booked times.

     * @param chosenDateString the selected date as a string
     */
    public void correctlyReloadTimeSlots(String chosenDateString) {
        removeBookedTimeSlots(chosenDateString);
        reloadTimeSlotPicker();
    }

    /**
     * Checks that all required fields are filled before saving a booking.

     * @return true if all fields are filled, false otherwise
     */
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

    /**
     * Called when a user selects a date. Updates the available time slots accordingly.
     */
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
    
    /**
     * Links the main page controller to the booking page controller.

     * @param controller the main page controller instance
     */
    public void setMainPageController(MainPageController controller) {
        this.mainPageController = controller;
    }

    /**
     * Returns the user to the main page and resets the booking form.
     * Also clears any event listeners to prevent memory leaks
     */
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

    /**
     * Displays information for the selected restaurant and sets up the booking form.
     * Disables past dates in the date picker and attaches a listener for selecting time slots.

     * @param restaurant the restaurant to display
     */
    public void setCurrentRestaurantDisplayed(Restaurant restaurant) {
        this.restaurant = new Restaurant(restaurant);
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

    /**
     * Generates all possible time slots based on the restaurant's number of tables.
     * Restaurants are assumed to be open from 14:00 to 24:00
     */
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

    /**
     * Sets whether the booking page is in edit mode and stores the booking being edited.

     * @param state         true if editing an existing booking, false otherwise
     * @param bookingToEdit the booking object to edit
     */
    public void setEditState(boolean state, CustomDateObject bookingToEdit) {
        currentlyInEditState = state;
        this.bookingToEdit = new CustomDateObject(bookingToEdit);
    }

    /**
     * Fills the booking form with data from an existing booking.

     * @param name      the first name of the person who booked
     * @param surname   the surname of the person who booked
     * @param date      the date of the booking
     * @param timeSlot  the time slot of the booking
     */
    public void fillFieldsWithExistingBookingInfo(String name, String surname, String date, String timeSlot) {
        this.name.setText(name);
        this.surname.setText(surname);
        this.date.setValue(LocalDate.parse(date));
        this.timeSlotsPicker.setValue(timeSlot);
    }

}
