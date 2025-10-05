package ui;


import java.util.Collection;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import persistence.Restaurant;
import persistence.Table;
import persistence.User;

public class BookingPageController {

    private Stage primaryStage;
    private Scene mainScene;
    private Restaurant restaurant;
    private MainPageController mainPageController;
    // private Collection<Table> tables; // Will be used when tables can be chosen in the booking page

    @FXML
    Label restaurantLabel;

    @FXML
    TextField name;

    @FXML
    TextField surname;

    @FXML
    DatePicker date;
 
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

        for(Table table : restaurant.getTables()){
            if (table.equals(firstAvailableTable)){
                table.book(new User(0, name.getText() + " " + surname.getText(), null, null, null), null);
                Alert alert = new Alert(AlertType.CONFIRMATION);
                mainPageController.updateRestaurantsInformation(restaurant);
                returnToMainPage();
                alert.setHeaderText("Table successfully booked!");
                alert.showAndWait();
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
        name.setText("");
        surname.setText("");
        date.setValue(null);
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

    // Will be used when tables can be chosen in the booking page
    // public void setCorrectTables(Collection<Table> tables){ 
    //     this.tables = tables;
    // }

}
