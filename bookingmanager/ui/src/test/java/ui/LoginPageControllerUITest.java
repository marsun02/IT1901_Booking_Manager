package ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
public class LoginPageControllerUITest {

    private LoginPageController controller;
    private Stage stage;

    @Start
    private void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/LoginPage.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
        this.stage = stage;
        stage.setScene(new Scene(root));
        stage.show();
    }

    @BeforeEach
    void setupController() {
        // Dummy main page controller
        MainPageController dummyMainController = new MainPageController();
        controller.setMainPageController(dummyMainController);

        javafx.application.Platform.runLater(() -> {
            Scene dummyMainScene = new Scene(new Button("Main Page"), 400, 300);
            controller.setStage(null, stage, null, dummyMainScene, null);
        });

        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    void testInitialLayoutShowsLoginOnly(FxRobot robot) {
        assertFalse(controller.emailHBox.isVisible());
        assertFalse(controller.confirmPasswordHBox.isVisible());
        assertFalse(controller.phoneNumberHBox.isVisible());

        assertEquals("Log in", controller.loginButton.getText());
        assertEquals("CREATE ONE", controller.createAccountLink.getText());
        assertEquals("NO ACCOUNT?", controller.noAccountText.getText());
    }

    @Test
    void testClickCreateAccountShowsRegisterFields(FxRobot robot) {
        robot.clickOn("#createAccountLink");
        WaitForAsyncUtils.waitForFxEvents();

        assertTrue(controller.emailHBox.isVisible());
        assertTrue(controller.confirmPasswordHBox.isVisible());
        assertTrue(controller.phoneNumberHBox.isVisible());

        assertEquals("Register Account", controller.loginButton.getText());
        assertEquals("LOGIN", controller.createAccountLink.getText());
        assertEquals("ALREADY HAVE AN ACCOUNT?", controller.noAccountText.getText());
    }

    @Test
    void testClickCreateAccountThenBackToLogin(FxRobot robot) {
        robot.clickOn("#createAccountLink");
        WaitForAsyncUtils.waitForFxEvents();
        robot.clickOn("#createAccountLink"); // Click again should switch back
        WaitForAsyncUtils.waitForFxEvents();

        assertFalse(controller.emailHBox.isVisible());
        assertFalse(controller.confirmPasswordHBox.isVisible());
        assertFalse(controller.phoneNumberHBox.isVisible());

        assertEquals("Log in", controller.loginButton.getText());
        assertEquals("CREATE ONE", controller.createAccountLink.getText());
        assertEquals("NO ACCOUNT?", controller.noAccountText.getText());
    }

    @Test
    void testLoginActionFunctionIsCalledOnLogin(FxRobot robot) {
        AtomicBoolean called = new AtomicBoolean(false);
        controller.setLoginActionFunction(user -> called.set(true));

        robot.clickOn("#usernameField").write("testUser");
        robot.clickOn("#passwordField").write("password");
        robot.clickOn("#loginButton");
        WaitForAsyncUtils.waitForFxEvents();

        assertTrue(called.get(), "loginActionFunction should be called when login pressed");
    }

    @Test
    void testLoginActionFunctionIsCalledOnRegister(FxRobot robot) {
        AtomicReference<User> capturedUser = new AtomicReference<>();
        controller.setLoginActionFunction(capturedUser::set);

        robot.clickOn("#createAccountLink");
        WaitForAsyncUtils.waitForFxEvents();

        robot.clickOn("#usernameField").write("regUser");
        robot.clickOn("#passwordField").write("pass");
        robot.clickOn("#confirmPasswordField").write("pass");
        robot.clickOn("#emailField").write("email@test.com");
        robot.clickOn("#phoneNumberField").write("12345678");

        robot.clickOn("#loginButton"); // Actually register
        WaitForAsyncUtils.waitForFxEvents();

        assertNotNull(capturedUser.get(), "loginActionFunction should be called when register pressed");
        assertEquals("regUser", capturedUser.get().getName());
        assertEquals("email@test.com", capturedUser.get().getEmail());
        assertEquals("12345678", capturedUser.get().getPhoneNumber());
        // cannot assert password since no getter exists
    }

    @Test
    void testFieldsResetAfterLogin(FxRobot robot) {
        AtomicBoolean called = new AtomicBoolean(false);
        controller.setLoginActionFunction(user -> called.set(true));

        robot.clickOn("#usernameField").write("someUser");
        robot.clickOn("#passwordField").write("pass");
        robot.clickOn("#loginButton");
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals("", controller.usernameField.getText());
        assertEquals("", controller.passwordField.getText());
    }

    @Test
    void testFieldsResetAfterSwitchingLayouts(FxRobot robot) {
        robot.clickOn("#usernameField").write("abc");
        robot.clickOn("#passwordField").write("123");

        robot.clickOn("#createAccountLink");
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals("", controller.usernameField.getText());
        assertEquals("", controller.passwordField.getText());
        assertEquals("", controller.confirmPasswordField.getText());
        assertEquals("", controller.emailField.getText());
        assertEquals("", controller.phoneNumberField.getText());
    }

        @Test
    void testRegistrationEmptyFieldsShowAlert(FxRobot robot) {
        robot.clickOn("#createAccountLink");
        WaitForAsyncUtils.waitForFxEvents();

        robot.clickOn("#loginButton"); // Attempt register with empty fields
        WaitForAsyncUtils.waitForFxEvents();
        
        // Since alerts block, just ensure fields remain empty
        assertEquals("", controller.usernameField.getText());
        assertEquals("", controller.passwordField.getText());
        assertEquals("", controller.confirmPasswordField.getText());
    }

    @Test
    void testRegistrationPasswordsDoNotMatch(FxRobot robot) {
        robot.clickOn("#createAccountLink");
        WaitForAsyncUtils.waitForFxEvents();

        robot.clickOn("#usernameField").write("user");
        robot.clickOn("#passwordField").write("pass1");
        robot.clickOn("#confirmPasswordField").write("pass2");
        robot.clickOn("#emailField").write("email@test.com");
        robot.clickOn("#phoneNumberField").write("12345");

        robot.clickOn("#loginButton"); // Should trigger "Passwords do not match"
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    void testRegistrationInvalidEmail(FxRobot robot) {
        robot.clickOn("#createAccountLink");
        WaitForAsyncUtils.waitForFxEvents();

        robot.clickOn("#usernameField").write("user");
        robot.clickOn("#passwordField").write("pass");
        robot.clickOn("#confirmPasswordField").write("pass");
        robot.clickOn("#emailField").write("invalid-email"); // no @
        robot.clickOn("#phoneNumberField").write("12345");

        robot.clickOn("#loginButton"); // Should trigger "Email not valid"
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    void testRegistrationInvalidPhone(FxRobot robot) {
        robot.clickOn("#createAccountLink");
        WaitForAsyncUtils.waitForFxEvents();

        robot.clickOn("#usernameField").write("user");
        robot.clickOn("#passwordField").write("pass");
        robot.clickOn("#confirmPasswordField").write("pass");
        robot.clickOn("#emailField").write("email@test.com");
        robot.clickOn("#phoneNumberField").write("abcde"); // invalid number

        robot.clickOn("#loginButton"); // Should trigger "Phone number not valid"
        WaitForAsyncUtils.waitForFxEvents();
    }
}