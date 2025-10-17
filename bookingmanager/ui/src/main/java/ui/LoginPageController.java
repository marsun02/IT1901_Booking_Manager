package ui;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import persistence.FileManagement;
import persistence.User;

public class LoginPageController {

    private Stage primaryStage;
    private Scene mainScene;
    private MainPageController mainPageController;
    private boolean currentLayoutIsRegisterAccount = false;

    final Path path = Paths.get("../persistence/src/main/resources/persistence/users.json");
    final File userDataFile = path.toFile(); // File to store user data

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
        // Makes hyperlink take you to correct "page"
        createAccountLink.setOnAction(e -> {
            if (currentLayoutIsRegisterAccount){
                switchBackToLoginLayout();
            } else{
                switchLayoutToRegisterAccount();
            }
        });
    }

    public void setMainPageController(MainPageController controller){
        this.mainPageController = controller;
    }

    @FXML
    private void loginAccount(){
        if (currentLayoutIsRegisterAccount){
            // Makes sure all fields must be filled
            if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty()){
                Alert alert = new Alert(AlertType.ERROR);
                alert.setHeaderText("All fields must be filled to create account");
                alert.showAndWait();
                return;
            }
            // Checks if username is taken
            Collection<User> users = FileManagement.loadUsers(userDataFile);
            for (User user : users) {
                if (user.getName().toLowerCase().equals(usernameField.getText().toLowerCase())){
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setHeaderText("Username already taken");
                    alert.showAndWait();
                    return;
                }
            }
            switchBackToLoginLayout();
            User newUser = new User(0, usernameField.getText(), null, null, passwordField.getText());
            FileManagement.saveUser(userDataFile, newUser);
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setHeaderText("Account created");
            alert.showAndWait();
        } else{
            // TODO: Login user
            // TODO: Make sure all fields are filled
        }
    }

    
    private void switchLayoutToRegisterAccount(){
        loginButton.setText("Register Account");
        noAccountText.setText("ALREADY HAVE AN ACCOUNT?");
        createAccountLink.setText("LOGIN");
        // noAccountText.setOpacity(0);
        // createAccountLink.setOpacity(0);
        // createAccountLink.setDisable(true);
        currentLayoutIsRegisterAccount = true;
        resetInfo();
    }

    private void switchBackToLoginLayout(){
        loginButton.setText("Log in");
        noAccountText.setText("NO ACCOUNT?");
        createAccountLink.setText("CREATE ONE");
        // noAccountText.setOpacity(1);
        // createAccountLink.setOpacity(1);
        // createAccountLink.setDisable(false);
        currentLayoutIsRegisterAccount = false;
        resetInfo();
    }

    private void resetInfo(){
        usernameField.setText("");
        passwordField.setText("");
    }


}
