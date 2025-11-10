package ui;

import core.ServerCommunication;
import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.User;

/**
 * Controller class for the login page in the application.
 * This class manages both the login and registration workflows. It handles 
 * users that exists in the system, input validation, account creation, and switching
 * between the login and registration layouts.
 */
public class LoginPageController implements Initializable {

    private Stage primaryStage;
    private Scene mainScene;
    private MainPageController mainPageController;
    private boolean currentLayoutIsRegisterAccount = false;

    @FXML
    TextField usernameField;

    @FXML
    PasswordField passwordField;

    @FXML
    PasswordField confirmPasswordField;

    @FXML
    HBox confirmPasswordhBox;

    @FXML
    TextField emailField;

    @FXML
    HBox emailhBox;

    @FXML
    TextField phoneNumberField;

    @FXML
    HBox phoneNumberhBox;

    @FXML
    Button loginButton;

    @FXML
    Hyperlink createAccountLink;

    @FXML
    Text noAccountText;

    /**
     * Sets up the stage and scene references for the controller.
     * This method is called once when initializing the UI. It also attaches an
     * event handler to the "Create Account" hyperlink, allowing the user to
     * toggle between login and registration layouts.

     * @param app           reference to the main application
     * @param primaryStage  the main stage used by the application
     * @param bookingScene  the booking page
     * @param mainScene     the main page
     * @param loginScene    the login page
     */
    public void setStage(
            App app,
            Stage primaryStage,
            Scene bookingScene,
            Scene mainScene,
            Scene loginScene) {
        this.primaryStage = primaryStage;
        this.mainScene = mainScene;
        // Makes hyperlink take you to correct "page"
        createAccountLink.setOnAction(e -> {
            if (currentLayoutIsRegisterAccount) {
                switchBackToLoginLayout();
            } else {
                switchLayoutToRegisterAccount();
            }
        });
    }

    /**
     * Connects the main page controller to this controller.

     * @param controller the main page controller instance
     */
    public void setMainPageController(MainPageController controller) {
        this.mainPageController = controller;
    }

    /**
     * Handles user login or account creation depending on the current layout.
     * - In login mode, it validates input against existing users and
     *   navigates to the main page if the user already exists.
     * - In registration mode, it validates all input fields, ensures username
     *   is unique, checks password, email and phone number format, and creates
     *   a new user if all checks pass.
     */
    @FXML
    private void loginAccount() {
        if (currentLayoutIsRegisterAccount) {
            // Makes sure all fields must be filled
            if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty()
                    || confirmPasswordField.getText().isEmpty() || emailField.getText().isEmpty()
                    || phoneNumberField.getText().isEmpty()) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setHeaderText("All fields must be filled to create account");
                alert.showAndWait();
                return;
            }
            // Checks if username is taken
            Collection<User> users = ServerCommunication.getAllUsers();
            for (User user : users) {
                if (user.getName().toLowerCase().equals(usernameField.getText().toLowerCase())) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setHeaderText("Username already taken");
                    alert.showAndWait();
                    return;
                }
            }
            // Check that passwords match
            if (!passwordField.getText().equals(confirmPasswordField.getText())) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setHeaderText("Passwords do not match");
                alert.showAndWait();
                return;
            }

            if (!emailField.getText().contains("@") || emailField.getText().startsWith("@")
                    || emailField.getText().endsWith("@")) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setHeaderText("Email not valid");
                alert.showAndWait();
                return;
            }

            try {
                Integer.parseInt(phoneNumberField.getText());
            } catch (Exception e) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setHeaderText("Phone number not valid");
                alert.showAndWait();
                return;
            }

            User newUser = new User(ServerCommunication.updateId("userId"), usernameField.getText(), null, null,
                    passwordField.getText());
            int returnValue = ServerCommunication.saveUser(newUser);
            if (returnValue == 0) {
                switchBackToLoginLayout();
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setHeaderText("Account created");
                alert.showAndWait();

            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setHeaderText("Error creating new account");
                alert.showAndWait();
                return;
            }
        } else {
            // Makes sure all fields must be filled
            if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty()) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setHeaderText("All fields must be filled to login");
                alert.showAndWait();
                return;
            }
            // Login the user and pass that user to the main page
            Collection<User> users = ServerCommunication.getAllUsers();
            for (User user : users) {
                if (user.getName().toLowerCase().equals(usernameField.getText().toLowerCase())) {
                    if (user.checkNamePassword(usernameField.getText(), passwordField.getText())) {
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

    /**
     * Switches the layout from login to account registration mode.
     * This method updates button labels, and shows the extra input fields
     * (email, confirm password, and phone number), and resets the form.
     */
    private void switchLayoutToRegisterAccount() {
        loginButton.setText("Register Account");
        noAccountText.setText("ALREADY HAVE AN ACCOUNT?");
        createAccountLink.setText("LOGIN");
        currentLayoutIsRegisterAccount = true;
        resetInfo();
        emailhBox.setVisible(true);
        emailhBox.setManaged(true);
        confirmPasswordhBox.setVisible(true);
        confirmPasswordhBox.setManaged(true);
        phoneNumberhBox.setVisible(true);
        phoneNumberhBox.setManaged(true);
    }

    /**
     * Switches the layout from account registration back to login mode.
     * This method hides the additional input fields and resets the form.
     */
    private void switchBackToLoginLayout() {
        loginButton.setText("Log in");
        noAccountText.setText("NO ACCOUNT?");
        createAccountLink.setText("CREATE ONE");
        currentLayoutIsRegisterAccount = false;
        resetInfo();
        emailhBox.setVisible(false);
        emailhBox.setManaged(false);
        confirmPasswordhBox.setVisible(false);
        confirmPasswordhBox.setManaged(false);
        phoneNumberhBox.setVisible(false);
        phoneNumberhBox.setManaged(false);
    }

    /**
     * Clears all text fields in the form.
     */
    private void resetInfo() {
        usernameField.setText("");
        passwordField.setText("");
        confirmPasswordField.setText("");
        emailField.setText("");
        phoneNumberField.setText("");
    }

    /**
     * Initializes the controller when the FXML file is loaded.
     * By default, it hides the registration-specific input fields so that
     * the layout starts in login mode.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        emailhBox.setVisible(false);
        emailhBox.setManaged(false);
        confirmPasswordhBox.setVisible(false);
        confirmPasswordhBox.setManaged(false);
        phoneNumberhBox.setVisible(false);
        phoneNumberhBox.setManaged(false);
    }

}
