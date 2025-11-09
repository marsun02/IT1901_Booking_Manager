package ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
public class BookingPageControllerUITest {

    private BookingPageController controller;
    private Stage stage;

    @Start
    private void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/TemplateBookingPage.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
        this.stage = stage;
        stage.setScene(new Scene(root));
        stage.show();
    }

    @BeforeEach
    void setupController() {
        Restaurant testRestaurant = new Restaurant("Frati", 10, 4);
        User testUser = new User();
        testUser.setName("Kristoffer");

        MainPageController mockMainPageController = mock(MainPageController.class);
        when(mockMainPageController.getCurrentlyLoggedInUser()).thenReturn(testUser);
        doNothing().when(mockMainPageController).saveRestaurants();
        doNothing().when(mockMainPageController).loadUserBookings();

        controller.setMainPageController(mockMainPageController);

        javafx.application.Platform.runLater(() -> {
            controller.setCurrentRestaurantDisplayed(testRestaurant);
            controller.clearAndPopulateTimeslots();
            Scene dummyMainScene = new Scene(new Label("Main Page"), 400, 300);
            controller.setStage(null, stage, null, dummyMainScene, null);
        });

        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    void testRestaurantLabelIsSet(FxRobot robot) {
        Label label = robot.lookup("#restaurantLabel").queryAs(Label.class);
        assertTrue(label.getText().contains("Frati"));
    }

    @Test
    void testBookingSucceedsWithValidInput(FxRobot robot) {
        Consumer<CustomDateObject> mockSaveBooking = mock(Consumer.class);
        controller.setSaveBookingFunction(mockSaveBooking);

        robot.clickOn("#name").write("Pusete");
        robot.clickOn("#surname").write("Pus");

        javafx.application.Platform.runLater(() -> {
            controller.date.setValue(LocalDate.now().plusDays(1));
            controller.clearAndPopulateTimeslots();
            controller.timeSlotsPicker.setValue(controller.timeSlotsPicker.getItems().get(0));
        });
        WaitForAsyncUtils.waitForFxEvents();

        robot.clickOn("#bookButton");
        WaitForAsyncUtils.waitForFxEvents();

        try { robot.clickOn("OK"); } catch (Exception ignored) {}
        try { robot.clickOn("Bekreft"); } catch (Exception ignored) {}

        verify(mockSaveBooking, times(1)).accept(any());
    }

    @Test
    void testBookingFailsWithEmptyFields(FxRobot robot) {
        Consumer<CustomDateObject> mockSaveBooking = mock(Consumer.class);
        controller.setSaveBookingFunction(mockSaveBooking);

        robot.clickOn("#bookButton");
        WaitForAsyncUtils.waitForFxEvents();

        verify(mockSaveBooking, never()).accept(any());
    }

    @Test
    void testReturnToMainPageResetsFields(FxRobot robot) {
        robot.clickOn("#name").write("Paul");
        robot.clickOn("#surname").write("Atreides");

        javafx.application.Platform.runLater(() -> {
            controller.date.setValue(LocalDate.now().plusDays(1));
            controller.timeSlotsPicker.setValue("18:00");
        });
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals("Paul", controller.name.getText());
        assertEquals("Atreides", controller.surname.getText());
        assertEquals(LocalDate.now().plusDays(1), controller.date.getValue());

        robot.clickOn("#closeButton");
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals("", controller.name.getText());
        assertEquals("", controller.surname.getText());
        assertNull(controller.date.getValue());
    }

    @Test
    void testBookingSucceedsWhenEditing(FxRobot robot) {
        Consumer<CustomDateObject> mockSaveBooking = mock(Consumer.class);
        controller.setSaveBookingFunction(mockSaveBooking);

        CustomDateObject dummyBooking = new CustomDateObject(
                LocalDate.now().plusDays(1).toString(), "14:00", "Frati", "Kristoffer",
                "Cat", "Meow", 1
        );

        controller.setEditState(true, dummyBooking);

        robot.clickOn("#name").write("Please");
        robot.clickOn("#surname").write("Save me");

        javafx.application.Platform.runLater(() -> {
            controller.date.setValue(LocalDate.now().plusDays(1));
            controller.clearAndPopulateTimeslots();
            controller.timeSlotsPicker.setValue(controller.timeSlotsPicker.getItems().get(0));
        });
        WaitForAsyncUtils.waitForFxEvents();

        robot.clickOn("#bookButton");
        WaitForAsyncUtils.waitForFxEvents();

        try { robot.clickOn("OK"); } catch (Exception ignored) {}
        try { robot.clickOn("Bekreft"); } catch (Exception ignored) {}

        verify(mockSaveBooking, times(1)).accept(any());
        assertFalse(controller.currentlyInEditState); // ensures flag reset after edit
    }

    @Test
    void testFillFieldsWithExistingBookingInfo(FxRobot robot) {
        String testName = "Eren";
        String testSurname = "Yeager";
        String testDate = LocalDate.now().plusDays(2).toString();
        String testTime = "15:00";

        javafx.application.Platform.runLater(() ->
                controller.fillFieldsWithExistingBookingInfo(testName, testSurname, testDate, testTime)
        );
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals(testName, controller.name.getText());
        assertEquals(testSurname, controller.surname.getText());
        assertEquals(LocalDate.parse(testDate), controller.date.getValue());
        assertEquals(testTime, controller.timeSlotsPicker.getValue());
    }

    @Test
    void testDatePickerDisablesPastDates() {
        javafx.application.Platform.runLater(() -> {
            var factory = controller.date.getDayCellFactory();
            assertNotNull(factory);

            LocalDate past = LocalDate.now().minusDays(1);
            LocalDate future = LocalDate.now().plusDays(1);

            // Past date cell
            var pastCell = factory.call(controller.date);
            pastCell.updateItem(past, false);
            assertTrue(pastCell.isDisable(), "Past date cell should be disabled");

            // Future date cell (fresh instance)
            var futureCell = factory.call(controller.date);
            futureCell.updateItem(future, false);
            assertFalse(futureCell.isDisable(), "Future date cell should not be disabled");
        });

        WaitForAsyncUtils.waitForFxEvents();
    }
}