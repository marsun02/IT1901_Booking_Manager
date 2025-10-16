package ui;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginPageController {

    private Stage primaryStage;
    private Scene mainScene;
    private MainPageController mainPageController;
    private boolean currentLayoutIsRegisterAccount = false;

    @FXML
    TextField usernameField;

    @FXML
    TextField passwordField;

    @FXML
    Button loginButton;

    @FXML
    Hyperlink createAccountLink;

    @FXML
    Text noAccountText;


    // There comes spotbugs from these, but Stage and Scene should not be copied, therefore the spotsbugs remain for now
    public void setStage(
        App app,
        Stage primaryStage,
        Scene bookingScene,
        Scene mainScene
    ) {
        this.primaryStage = primaryStage;
        this.mainScene = bookingScene;
    }

    public void setMainPageController(MainPageController controller){
        this.mainPageController = controller;
    }

    @FXML
    private void loginAccount(){
        if (currentLayoutIsRegisterAccount){
            switchBackToLoginLayout();
            // TODO: Save account created
            currentLayoutIsRegisterAccount = false;
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setHeaderText("Account created");
            alert.showAndWait();
        } else{
            // TODO: Login user
        }
    }

    @FXML
    private void switchLayoutToRegisterAccount(){
        loginButton.setText("Register Account");
        noAccountText.setOpacity(0);
        createAccountLink.setOpacity(0);
        createAccountLink.setDisable(true);
        currentLayoutIsRegisterAccount = true;
    }

    private void switchBackToLoginLayout(){
        loginButton.setText("Log in");
        noAccountText.setOpacity(1);
        createAccountLink.setOpacity(1);
        createAccountLink.setDisable(false);
    }


}
