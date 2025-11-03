package ui;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.User;
import persistence.FileManagement;

public class LoginPageController implements Initializable{

    private Stage primaryStage;
    private Scene mainScene;
    private MainPageController mainPageController;
    private boolean currentLayoutIsRegisterAccount = false;

    final Path path = Paths.get("../persistence/src/main/resources/persistence/users.json");
    final File userDataFile = path.toFile(); // File to store user data

    @FXML
    TextField usernameField;

    @FXML
    PasswordField passwordField;

    @FXML
    PasswordField confirmPasswordField;

    @FXML
    HBox confirmPasswordHBox;

    @FXML
    TextField emailField;

    @FXML
    HBox emailHBox;

    @FXML
    TextField phoneNumberField;

    @FXML
    HBox phoneNumberHBox;

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
        Scene mainScene,
        Scene loginScene
    ) {
        this.primaryStage = primaryStage;
        this.mainScene = mainScene;
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
            if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty() || confirmPasswordField.getText().isEmpty() || emailField.getText().isEmpty() || phoneNumberField.getText().isEmpty()){
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
            // Check that passwords match
            if (!passwordField.getText().equals(confirmPasswordField.getText())){
                Alert alert = new Alert(AlertType.ERROR);
                alert.setHeaderText("Passwords do not match");
                alert.showAndWait();
                return;
            }
            User newUser = new User(FileManagement.getId("userId"), usernameField.getText(), emailField.getText(), phoneNumberField.getText(), passwordField.getText());
            FileManagement.saveUser(userDataFile, newUser);
            switchBackToLoginLayout();
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setHeaderText("Account created");
            alert.showAndWait();
        } else{
            // Makes sure all fields must be filled
            if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty()){
                Alert alert = new Alert(AlertType.ERROR);
                alert.setHeaderText("All fields must be filled to login");
                alert.showAndWait();
                return;
            }
            // Login the user and pass that user to the main page
            Collection<User> users = FileManagement.loadUsers(userDataFile);
            for (User user : users) {
                if (user.getName().toLowerCase().equals(usernameField.getText().toLowerCase())){
                    if (user.checkNamePassword(usernameField.getText(), passwordField.getText())){
                        primaryStage.setScene(mainScene);
                        mainPageController.setcurrentlyLoggedInUser(user);
                        mainPageController.setLoggedInUserLabel(user.getName());
                        resetInfo();
                        mainPageController.loadUserBookings();
                        return;
                    }
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setHeaderText("Username and password do not match");
                    alert.showAndWait();
                    return;
                }
            }
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("No such user found");
            alert.showAndWait();
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
        emailHBox.setVisible(true);
        emailHBox.setManaged(true);
        confirmPasswordHBox.setVisible(true);
        confirmPasswordHBox.setManaged(true);
        phoneNumberHBox.setVisible(true);
        phoneNumberHBox.setManaged(true);
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
        emailHBox.setVisible(false);
        emailHBox.setManaged(false);
        confirmPasswordHBox.setVisible(false);
        confirmPasswordHBox.setManaged(false);
        phoneNumberHBox.setVisible(false);
        phoneNumberHBox.setManaged(false);
    }

    private void resetInfo(){
        usernameField.setText("");
        passwordField.setText("");
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        emailHBox.setVisible(false);
        emailHBox.setManaged(false);
        confirmPasswordHBox.setVisible(false);
        confirmPasswordHBox.setManaged(false);
        phoneNumberHBox.setVisible(false);
        phoneNumberHBox.setManaged(false);
    }


}
