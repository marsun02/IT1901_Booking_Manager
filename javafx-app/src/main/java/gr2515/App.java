package gr2515;

import java.util.Collection;
import java.util.HashSet;

import gr2515.core.Restaurant;
import gr2515.core.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class App extends Application{

    private Collection<Restaurant> restaurants;
    private Collection<User> users;

    @Override
    public void start(Stage primaryStage){
        try {
        primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("StaticProjectExample.fxml"))));
        primaryStage.setTitle("Booking page");
        primaryStage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public App(){
        restaurants.add(new Restaurant("Restaurant_0", 0));
        restaurants.add(new Restaurant("Restaurant_1", 1));
        restaurants.add(new Restaurant("Restaurant_2", 2));

        users = new HashSet<>();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
