package gr2515.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ControllerExample {

    @FXML
    private Button table2;

    @FXML
    private Button table3;

    @FXML
    private Button table4;

    @FXML
    private Label label;

    @FXML
    private void onTableClicked(ActionEvent event){
        Button source = (Button) event.getSource();
        System.out.println(source.getText());
        label.setText("Table " + source.getText().charAt(source.getText().length()-1) + " booked!");
    }
}
