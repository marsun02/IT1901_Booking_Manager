package gr2515.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

import gr2515.core.Restaurant;
import gr2515.core.Table;
import gr2515.core.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ControllerExample {

    Path path = Paths.get("data", "BookedTables.txt");
    final File absolutepath = path.toFile(); // Universal file path we can use to access BookedTables.txt
    
    @FXML
    private Label label;

    @FXML
    private Button table1;
    
    @FXML
    private Button table2;
    
    @FXML
    private Button table3;

    @FXML
    private Button table4;

    @FXML
    private Button table5;

    @FXML
    private Button table6;

    private Collection<Restaurant> restaurants;
    private Collection<User> users;
    private int userId = 0;

    @FXML
    private void onTableClicked(ActionEvent event){
        Button source = (Button) event.getSource();
        label.setText("Table " + source.getText().charAt(source.getText().length()-1) + " booked!");
        source.setDisable(true);
        writeBookedTableToFile(source.getText());
    }

    public ArrayList<String> alreadyBookedTables(){
        ArrayList<String> bookedTables = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(absolutepath);
            while (scanner.hasNextLine()) {
                bookedTables.add(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bookedTables;
    }

    private void writeBookedTableToFile(String table){
        try {
            FileWriter myWriter = new FileWriter(absolutepath, true);
            myWriter.write(table);
            myWriter.write("\n");
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize(){
        ArrayList<String> bookedTables = alreadyBookedTables();

        List<Button> allTables = List.of(table1,table2,table3,table4,table5,table6);
        for (Button table : allTables) { // this whole for-loop makes sure to disable the tables that are already booked
            if (bookedTables.contains(table.getText())){
                table.setDisable(true);
            }
        }

        // Initialize restaurant and user collections
        // Restaurants collection have temporary contents
        restaurants = new HashSet<>();
        restaurants.add(new Restaurant("Restaurant_0", 0));
        restaurants.add(new Restaurant("Restaurant_1", 1));
        restaurants.add(new Restaurant("Restaurant_2", 2));

        users = new HashSet<>();
    }

    // Add a user with automatically assigned id
    public void addUser(String name, String email, String phoneNumber){
        users.add(new User(userId, name, email, phoneNumber));
        userId++;
    }

    // Add table to a restaurant of choice with automatically assigned id
    public void addTableToRestaurant(Restaurant restaurant, int spots){
        restaurant.addTable(new Table(restaurant.getNextTableId(), spots));
    }

    public void bookTableAsUser(User user, Table table, Restaurant restaurant){
        if (!users.contains(user)){
            // TODO: handle event that user does not exist
        }

        if (!restaurant.getAvailableTables().contains(table)){
            // TODO: handle event that restaurant doesn't contain table
        }

        // Set bookedTime to new Date() temporarily before time functionality is implemented
        table.book(user, new Date());
    }
}
