package gr2515;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class App extends Application{

    @Override
    public void start(Stage primaryStage){
        try {
        primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("MainPage.fxml"))));
        primaryStage.setScene(new Scene(new AnchorPane(), 800, 600));
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
