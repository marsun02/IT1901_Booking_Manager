package ui;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
public class LoginPageControllerTest {

    private LoginPageController controller;
    private Stage stage;
    private Scene mainScene;
    private MainPageController mainPageController;

    @Start
    private void start(Stage stage) {
        this.stage = stage;
        controller = new LoginPageController();
        mainScene = new Scene(new Label("Main Scene"));
        mainPageController = mock(MainPageController.class);
        controller.setMainPageController(mainPageController);

        // Manually initialize FXML fields
        controller.usernameField = new TextField();
        controller.passwordField = new PasswordField();
        controller.confirmPasswordField = new PasswordField();
        controller.emailField = new TextField();
        controller.phoneNumberField = new TextField();
        controller.loginButton = new Button();
        controller.createAccountLink = new Hyperlink();
        controller.noAccountText = new Text();
        controller.emailHBox = new HBox();
        controller.confirmPasswordHBox = new HBox();
        controller.phoneNumberHBox = new HBox();

        Platform.runLater(() -> controller.initialize(null, null));
        WaitForAsyncUtils.waitForFxEvents();

        controller.setStage(null, stage, null, mainScene, null);
    }

    @BeforeEach
    public void setup() {
        controller.resetInfo();
    }

    @Test
    public void testSwitchLayoutToRegisterAndBack() {
        Platform.runLater(() -> controller.createAccountLink.fire());
        WaitForAsyncUtils.waitForFxEvents();

        assertTrue(controller.currentLayoutIsRegisterAccount);
        assertEquals("Register Account", controller.loginButton.getText());
        assertTrue(controller.emailHBox.isVisible());

        Platform.runLater(() -> controller.createAccountLink.fire());
        WaitForAsyncUtils.waitForFxEvents();

        assertFalse(controller.currentLayoutIsRegisterAccount);
        assertEquals("Log in", controller.loginButton.getText());
        assertFalse(controller.emailHBox.isVisible());
    }

    // @Test
    // public void testSuccessfulLogin() {
    //     User mockUser = mock(User.class);
    //     when(mockUser.getName()).thenReturn("Kristoffer");
    //     when(mockUser.checkNamePassword("Kristoffer", "password")).thenReturn(true);

    //     try (MockedStatic<core.ServerCommunication> mockServer = Mockito.mockStatic(core.ServerCommunication.class)) {
    //         mockServer.when(core.ServerCommunication::getAllUsers).thenReturn(List.of(mockUser));

    //         Platform.runLater(() -> {
    //             controller.usernameField.setText("Kristoffer");
    //             controller.passwordField.setText("password");
    //             controller.loginAccount();
    //         });
    //         WaitForAsyncUtils.waitForFxEvents();

    //         verify(mainPageController).setcurrentlyLoggedInUser(mockUser);
    //         verify(mainPageController).setLoggedInUserLabel("Kristoffer");
    //         verify(mainPageController).loadUserBookings();
    //     }
    // }

    @Test
    public void testLoginIncorrectPassword() {
        User mockUser = mock(User.class);
        when(mockUser.getName()).thenReturn("Kristoffer");
        when(mockUser.checkNamePassword("Kristoffer", "wrongPassword")).thenReturn(false);

        try (MockedStatic<core.ServerCommunication> mockServer = Mockito.mockStatic(core.ServerCommunication.class)) {
            mockServer.when(core.ServerCommunication::getAllUsers).thenReturn(List.of(mockUser));

            Platform.runLater(() -> {
                controller.usernameField.setText("Kristoffer");
                controller.passwordField.setText("wrongPassword");
                controller.loginAccount();
            });
            WaitForAsyncUtils.waitForFxEvents();

            verify(mainPageController, never()).setcurrentlyLoggedInUser(any());
        }
    }

    @Test
    public void testLoginNonexistentUser() {
        try (MockedStatic<core.ServerCommunication> mockServer = Mockito.mockStatic(core.ServerCommunication.class)) {
            mockServer.when(core.ServerCommunication::getAllUsers).thenReturn(List.of());

            Platform.runLater(() -> {
                controller.usernameField.setText("GhostUser");
                controller.passwordField.setText("password");
                controller.loginAccount();
            });
            WaitForAsyncUtils.waitForFxEvents();

            verify(mainPageController, never()).setcurrentlyLoggedInUser(any());
        }
    }

    // @Test
    // public void testRegisterAccountSuccess() {
    //     try (MockedStatic<core.ServerCommunication> mockServer = Mockito.mockStatic(core.ServerCommunication.class)) {
    //         mockServer.when(core.ServerCommunication::getAllUsers).thenReturn(List.of());
    //         mockServer.when(() -> core.ServerCommunication.updateId("userId")).thenReturn(1);
    //         mockServer.when(() -> core.ServerCommunication.saveUser(any(User.class))).thenReturn(0);

    //         Platform.runLater(() -> {
    //             controller.createAccountLink.fire();
    //             controller.usernameField.setText("NewUser");
    //             controller.passwordField.setText("pass");
    //             controller.confirmPasswordField.setText("pass");
    //             controller.emailField.setText("test@example.com");
    //             controller.phoneNumberField.setText("12345678");
    //             controller.loginAccount();
    //         });
    //         WaitForAsyncUtils.waitForFxEvents();

    //         mockServer.verify(() -> core.ServerCommunication.saveUser(any(User.class)), times(1));
    //     }
    // }

    @Test
    public void testRegisterUsernameTaken() {
        User existingUser = mock(User.class);
        when(existingUser.getName()).thenReturn("TakenUser");

        try (MockedStatic<core.ServerCommunication> mockServer = Mockito.mockStatic(core.ServerCommunication.class)) {
            mockServer.when(core.ServerCommunication::getAllUsers).thenReturn(List.of(existingUser));

            Platform.runLater(() -> {
                controller.createAccountLink.fire();
                controller.usernameField.setText("TakenUser");
                controller.passwordField.setText("pass");
                controller.confirmPasswordField.setText("pass");
                controller.emailField.setText("test@example.com");
                controller.phoneNumberField.setText("12345678");
                controller.loginAccount();
            });
            WaitForAsyncUtils.waitForFxEvents();

            mockServer.verify(() -> core.ServerCommunication.saveUser(any(User.class)), never());
        }
    }

    @Test
    public void testRegisterAccountPasswordMismatch() {
        Platform.runLater(() -> {
            controller.createAccountLink.fire();
            controller.usernameField.setText("NewUser");
            controller.passwordField.setText("pass");
            controller.confirmPasswordField.setText("wrong");
            controller.emailField.setText("test@example.com");
            controller.phoneNumberField.setText("12345678");
            controller.loginAccount();
        });
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    public void testRegisterAccountInvalidEmail() {
        Platform.runLater(() -> {
            controller.createAccountLink.fire();
            controller.usernameField.setText("NewUser");
            controller.passwordField.setText("pass");
            controller.confirmPasswordField.setText("pass");
            controller.emailField.setText("invalidEmail");
            controller.phoneNumberField.setText("12345678");
            controller.loginAccount();
        });
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    public void testRegisterAccountInvalidPhoneNumber() {
        Platform.runLater(() -> {
            controller.createAccountLink.fire();
            controller.usernameField.setText("NewUser");
            controller.passwordField.setText("pass");
            controller.confirmPasswordField.setText("pass");
            controller.emailField.setText("test@example.com");
            controller.phoneNumberField.setText("invalidPhone");
            controller.loginAccount();
        });
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    public void testRegisterEmptyFields() {
        Platform.runLater(() -> {
            controller.createAccountLink.fire();
            controller.usernameField.setText("");
            controller.passwordField.setText("");
            controller.confirmPasswordField.setText("");
            controller.emailField.setText("");
            controller.phoneNumberField.setText("");
            controller.loginAccount();
        });
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    public void testLoginEmptyFields() {
        Platform.runLater(() -> {
            controller.usernameField.setText("");
            controller.passwordField.setText("");
            controller.loginAccount();
        });
        WaitForAsyncUtils.waitForFxEvents();
    }
}
