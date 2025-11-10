package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The startup point of the application.
 * The {@Code App} class is responsible for launching the JavaFX application.
 * It loads the FXML files for the different pages (Login, Main, and Booking),
 * sets up their controllers, and defines the initial stage and scene for the application.
 */
public class App extends Application {

    /**
     * Called automatically when the JavaFX application starts.
     * This method loads all necessary FXML files, starts their corresponding controllers,
     * connects the controllers together for communication, and sets up the primary stage.

     * @param primaryStage  the main stage of the application
     */
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

    /**
     * main method to run app.

     * @param args args are needed to run app
     */
    public static void main(String[] args) {
        launch(args);
    }
}