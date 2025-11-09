package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader mainLoader = new FXMLLoader(App.class.getResource("MainPage.fxml"));
            Scene mainScene = new Scene(mainLoader.load());
            MainPageController mainController = mainLoader.getController();

            FXMLLoader bookingLoader = new FXMLLoader(App.class.getResource("TemplateBookingPage.fxml"));
            Scene bookingScene = new Scene(bookingLoader.load());
            BookingPageController bookingController = bookingLoader.getController();

            FXMLLoader loginLoader = new FXMLLoader(App.class.getResource("LoginPage.fxml"));
            Scene loginScene = new Scene(loginLoader.load());
            LoginPageController loginController = loginLoader.getController();

            loginController.setStage(this, primaryStage, bookingScene, mainScene, loginScene);
            mainController.setStage(this, primaryStage, bookingScene, mainScene, loginScene);
            bookingController.setStage(this, primaryStage, bookingScene, mainScene, loginScene);
            loginController.setMainPageController(mainController);
            mainController.setBookingPageController(bookingController);
            bookingController.setMainPageController(mainController);

            primaryStage.setScene(loginScene);
            primaryStage.setTitle("Booking manager");
            primaryStage.show();
        } catch (Exception e) {
            System.out.println("Error loading FXML file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}