package gr2515;

import gr2515.ui.BookingPageController;
import gr2515.ui.MainPageController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class App extends Application{

    @Override
    public void start(Stage primaryStage){
        try {
        FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("test.fxml"));
        Scene mainScene = new Scene(mainLoader.load());
        MainPageController mainController = mainLoader.getController();

        FXMLLoader bookingLoader = new FXMLLoader(getClass().getResource("TemplateBookingPage.fxml"));
        Scene bookingScene = new Scene(bookingLoader.load());
        BookingPageController bookingController = bookingLoader.getController();

        mainController.setStage(this, primaryStage, bookingScene, mainScene);
        bookingController.setStage(this, primaryStage, bookingScene, mainScene);

        primaryStage.setScene(bookingScene);
        primaryStage.setTitle("Booking page");
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
