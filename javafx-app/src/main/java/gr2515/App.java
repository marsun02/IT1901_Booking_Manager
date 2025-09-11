package gr2515;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
// import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Hello world!
 */
public class App extends Application{

    @Override
    public void start(Stage primaryStage){
        try {
        // Parent root = new FXMLLoader(this.getClass().getResource("StaticProjectExample.fxml")).load();
        // primaryStage.setScene(new Scene(root));
        primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("StaticProjectExample.fxml"))));
        primaryStage.setTitle("Booking page");
        primaryStage.show();
    }
    catch (Exception e){
        e.printStackTrace();
    }
    }  

    public static void main(String[] args) {
        System.out.println("Hello World!");
        launch(args);
    }

}
