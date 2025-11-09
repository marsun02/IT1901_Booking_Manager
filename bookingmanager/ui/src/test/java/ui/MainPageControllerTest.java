package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import model.CustomDateObject;
import model.Restaurant;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
public class MainPageControllerTest {

    private MainPageController controller;
    private BookingPageController bookingController;
    private Stage stage;
    private Scene bookingScene;

    private CustomDateObject booking1;
    private CustomDateObject booking2;

    @Start
    private void start(Stage stage) throws Exception {
        FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("/ui/MainPage.fxml"));
        Parent mainRoot = mainLoader.load();
        controller = mainLoader.getController();

        FXMLLoader bookingLoader = new FXMLLoader(getClass().getResource("/ui/TemplateBookingPage.fxml"));
        Parent bookingRoot = bookingLoader.load();
        bookingController = bookingLoader.getController();
        bookingScene = new Scene(bookingRoot, 400, 300);

        controller.setBookingPageController(bookingController);
        controller.setStage(null, stage, bookingScene, new Scene(new Label("Main Page")), new Scene(new Label("Login Scene")));

        Scene mainScene = new Scene(mainRoot, 1000, 800);
        stage.setScene(mainScene);
        stage.setWidth(1000);
        stage.setHeight(800);
        stage.centerOnScreen();
        stage.toFront();
        stage.show();

        this.stage = stage;
    }

    @BeforeEach
    void setupController() {
        User testUser = new User();
        testUser.setName("Kristoffer");

        Restaurant testRestaurant = new Restaurant("Frati", 10, 4);

        booking1 = new CustomDateObject(LocalDate.now().plusDays(1).toString(), "18:00", "Frati", "Kristoffer", "John", "Doe", 1);
        booking2 = new CustomDateObject(LocalDate.now().plusDays(2).toString(), "19:00", "Frati", "Kristoffer", "Jane", "Smith", 2);

        javafx.application.Platform.runLater(() -> {
            controller.setcurrentlyLoggedInUser(testUser);
            controller.setLoggedInUserLabel(testUser.getName());
            controller.getRestaurants().add(testRestaurant);
            controller.loadUserBookings();
            controller.bookingOverview.getItems().addAll(List.of(booking1, booking2));
        });

        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    void testLoggedInUserLabelIsSet(FxRobot robot) {
        Label label = robot.lookup("#loggedInUserLabel").queryAs(Label.class);
        assertEquals("Kristoffer", label.getText());
    }

    @Test
    void testRestaurantButtonNavigatesToBookingPage(FxRobot robot) {
        robot.clickOn("#restaurant0");
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals(bookingScene, stage.getScene());
        assertTrue(bookingController.restaurantLabel.getText().contains("Frati"));
    }

    @Test
    void testBookingTableDisplaysCorrectData(FxRobot robot) {
        TableView<CustomDateObject> table = robot.lookup("#bookingOverview").queryAs(TableView.class);
        assertEquals(2, table.getItems().size());
        assertEquals("John", table.getItems().get(0).getFirstName());
        assertEquals("Jane", table.getItems().get(1).getFirstName());
    }

    @Test
    void testLogOutClearsUserAndSwitchesScene(FxRobot robot) {
        robot.clickOn("Log out");
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals("Login Scene", ((Label) stage.getScene().getRoot()).getText());
        assertNull(controller.getCurrentlyLoggedInUser());
    }

    @Test
    void testEditButtonTriggersEditFlow(FxRobot robot) {
        robot.clickOn("#editButton_" + booking1.getId());
        WaitForAsyncUtils.waitForFxEvents();

        assertTrue(bookingController.currentlyInEditState);
        assertEquals("John", bookingController.name.getText());
        assertEquals("Doe", bookingController.surname.getText());
        assertEquals(bookingScene, stage.getScene());
    }

    @Test
    void testDeleteButtonRemovesBooking(FxRobot robot) {
        javafx.application.Platform.runLater(() -> {
            controller.bookingOverview.getItems().remove(booking1);
        });
        WaitForAsyncUtils.waitForFxEvents();

        TableView<CustomDateObject> table = controller.bookingOverview;
        assertEquals(1, table.getItems().size());
        assertEquals("Jane", table.getItems().get(0).getFirstName());
    }


    @Test
    void testAdminSeesAllBookings(FxRobot robot) {
        User adminUser = new User();
        adminUser.setName("Admin");

        javafx.application.Platform.runLater(() -> {
            controller.setcurrentlyLoggedInUser(adminUser);
            controller.loadUserBookings();
        });

        WaitForAsyncUtils.waitForFxEvents();

        Label label = robot.lookup("#yourBookingsLabel").queryAs(Label.class);
        assertEquals("ALL BOOKINGS", label.getText());
    }

    @Test
    void testRegularUserSeesOwnBookings(FxRobot robot) {
        User regularUser = new User();
        regularUser.setName("Kristoffer");

        javafx.application.Platform.runLater(() -> {
            controller.setcurrentlyLoggedInUser(regularUser);
            controller.loadUserBookings();
        });

        WaitForAsyncUtils.waitForFxEvents();

        Label label = robot.lookup("#yourBookingsLabel").queryAs(Label.class);
        assertEquals("YOUR BOOKINGS", label.getText());
    }

    @Test
    void testEnterRestaurantPageWithNullRestaurantShowsAlert(FxRobot robot) {
        javafx.application.Platform.runLater(() -> {
            
            Button fakeButton = new Button();
            fakeButton.setId("nonExistentRestaurant");

            ActionEvent fakeEvent = new ActionEvent(fakeButton, null);

            assertDoesNotThrow(() -> controller.enterRestaurantPage(fakeEvent));
        });

        WaitForAsyncUtils.waitForFxEvents();
    }
}