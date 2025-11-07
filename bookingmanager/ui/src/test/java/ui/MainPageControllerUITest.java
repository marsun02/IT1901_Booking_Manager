package ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.CustomDateObject;
import model.Restaurant;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import core.ServerCommunication;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
public class MainPageControllerUITest {

    private MainPageController controller;
    private Stage stage;

    @Start
    private void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/MainPage.fxml"));
        Parent root = loader.load();
        controller = loader.getController(); // Get the controller after loading
        this.stage = stage;
        stage.setScene(new Scene(root));
        stage.show();
    }

    @BeforeEach
    void setupUser() {
    User testUser = new User();
    testUser.setName("Kristoffer");
    controller.setcurrentlyLoggedInUser(testUser);

    // Ensure UI updates happen on the JavaFX thread
    javafx.application.Platform.runLater(() -> {
        controller.setLoggedInUserLabel(testUser.getName());
    });
}

    @Test
    void testLoggedInUserLabelIsSet(FxRobot robot) {
        Label label = robot.lookup("#loggedInUserLabel").queryAs(Label.class);
        assertEquals("Kristoffer", label.getText());
    }

    @Test
    void testLoadUserBookings_populatesTable(FxRobot robot) {
        try (MockedStatic<ServerCommunication> serverMock = mockStatic(ServerCommunication.class)) {
            List<CustomDateObject> mockBookings = new ArrayList<>(List.of(
                new CustomDateObject("2025-11-10", "18:00", "Frati", "Kristoffer", "John", "Doe", 1),
                new CustomDateObject("2025-11-11", "19:00", "Szechuan", "Kristoffer", "Jane", "Smith", 2)
            ));
            serverMock.when(() -> ServerCommunication.getBookingsByUser("Kristoffer"))
                      .thenReturn(mockBookings);

            controller.loadUserBookings();

            TableView<CustomDateObject> table = robot.lookup("#bookingOverview").queryTableView();
            assertEquals(2, table.getItems().size());
            assertEquals("John", table.getItems().get(0).getFirstName());
        }
    }

    @Test
    void testYourBookingsLabelUpdates(FxRobot robot) {
        try (MockedStatic<ServerCommunication> serverMock = mockStatic(ServerCommunication.class)) {
            serverMock.when(() -> ServerCommunication.getBookingsByUser("Kristoffer"))
                      .thenReturn(new ArrayList<>());

            controller.loadUserBookings();

            Label label = robot.lookup("#yourBookingsLabel").queryAs(Label.class);
            assertEquals("YOUR BOOKINGS", label.getText());
        }
    }

    @Test
    void testLogOutButtonSwitchesScene(FxRobot robot) {
        // Set a dummy login scene to switch to
        Scene loginScene = new Scene(new Label("Login Page"), 400, 300);
        controller.setStage(null, stage, null, null, loginScene);

        // Click the log out button
        robot.clickOn("Log out");

        // Check that the scene has changed
        assertEquals(loginScene, stage.getScene());
    }

    // @Test
    // void testRestaurantButtonClickLoadsBookingScene(FxRobot robot) {
    //     // Setup mock restaurant and booking controller
    //     Restaurant mockRestaurant = new Restaurant("Frati", 10, 4);
    //     controller.restaurants = List.of(mockRestaurant);
    //     BookingPageController mockBookingPageController = mock(BookingPageController.class);
    //     controller.setBookingPageController(mockBookingPageController);

    //     // Set a dummy booking scene
    //     Scene bookingScene = new Scene(new Label("Booking Page"), 400, 300);
    //     controller.setStage(null, stage, bookingScene, null, null);

    //     // Click on restaurant0 (Frati)
    //     robot.clickOn("#restaurant0");

    //     // Verify scene switched
    //     assertEquals(bookingScene, stage.getScene());

    //     // Verify controller was updated
    //     verify(mockBookingPageController).setCurrentRestaurantDisplayed(mockRestaurant);
    //     verify(mockBookingPageController).reloadTimeSlotPicker();
    // }
}