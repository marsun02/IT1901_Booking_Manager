package gr2515.ui;


import gr2515.App;
import gr2515.core.Restaurant;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class BookingPageController {

    private App app;
    private Stage primaryStage;
    private Scene bookingScene;
    private Scene mainScene;
    private Restaurant restaurant;
    private MainPageController mainPageController;

    @FXML
    Label restaurantLabel;

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

    public void setMainPageController(MainPageController controller){
        this.mainPageController = controller;
    }




    public void returnToMainPage(){
        primaryStage.setScene(mainScene);
    }

    public void setCurrentResturantDisplayed(Restaurant restaurant){
        this.restaurant = restaurant;
        restaurantLabel.setText("Velkommen til " + restaurant.getName());
    }

}
