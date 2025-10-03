package ui;

import ui.BookingPageController;
import ui.MainPageController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class App extends Application{

    @Override
    public void start(Stage primaryStage){
        try {
        FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("MainPage.fxml"));
        Scene mainScene = new Scene(mainLoader.load());
        MainPageController mainController = mainLoader.getController();

        FXMLLoader bookingLoader = new FXMLLoader(getClass().getResource("TemplateBookingPage.fxml"));
        Scene bookingScene = new Scene(bookingLoader.load());
        BookingPageController bookingController = bookingLoader.getController();

        mainController.setStage(this, primaryStage, bookingScene, mainScene);
        mainController.setBookingPageController(bookingController);
        bookingController.setStage(this, primaryStage, bookingScene, mainScene);
        bookingController.setMainPageController(mainController);

        primaryStage.setScene(mainScene);
        primaryStage.setTitle("Booking manager");
        primaryStage.show();


        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}