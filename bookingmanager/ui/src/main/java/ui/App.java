package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class App extends Application{

    @Override
    public void start(Stage primaryStage){
        try {
            FXMLLoader mainLoader = new FXMLLoader(App.class.getResource("MainPage.fxml"));
            Scene mainScene = new Scene(mainLoader.load());
            MainPageController mainController = mainLoader.getController();

            FXMLLoader bookingLoader = new FXMLLoader(App.class.getResource("TemplateBookingPage.fxml"));
            Scene bookingScene = new Scene(bookingLoader.load());
            BookingPageController bookingController = bookingLoader.getController();

            mainController.setStage(this, primaryStage, bookingScene, mainScene);
            mainController.setBookingPageController(bookingController);
            bookingController.setStage(this, primaryStage, bookingScene, mainScene);
            bookingController.setMainPageController(mainController);

            primaryStage.setScene(mainScene);
            primaryStage.setTitle("Booking manager");
            primaryStage.show();
        } catch (Exception e){
            System.out.println("Error loading FXML file: " + e.getMessage()); 
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}