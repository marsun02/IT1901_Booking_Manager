package ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.CustomDateObject;
import model.Restaurant;
import model.User;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
public class BookingPageControllerTest {

    private static final Path ORIGINAL_IDS = Paths.get("../server/src/main/resources/ids.json");
    private static final Path ORIGINAL_BOOKINGS = Paths.get("../server/src/main/resources/bookings.json");
    private BookingPageController controller;
    private Stage stage;
    private static Path tempBackupIdsFile;
    private static Path tempBackupBookingsFile;

    @Start
    private void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/TemplateBookingPage.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
        this.stage = stage;
        stage.setScene(new Scene(root));
        stage.show();
    }

    @BeforeAll
    private static void createTempBackupFile() throws IOException {
        try {
            tempBackupIdsFile = Files.createTempFile("idsBackup", ".json");
            tempBackupBookingsFile = Files.createTempFile("bookingsBackup", ".json");
            Files.copy(ORIGINAL_IDS, tempBackupIdsFile, StandardCopyOption.REPLACE_EXISTING);
            Files.copy(ORIGINAL_BOOKINGS, tempBackupBookingsFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println("Could not create backup!");
            throw new IOException("Aborting tests");
        }
    }

    @AfterAll
    private static void restoreOriginalFile() throws IOException {
        if (Files.exists(tempBackupIdsFile)) {
            Files.copy(tempBackupIdsFile, ORIGINAL_IDS, StandardCopyOption.REPLACE_EXISTING);
            Files.deleteIfExists(tempBackupIdsFile);
        } else {
            System.out.println("Error restoring original file - could not find backup");
        }

        if (Files.exists(tempBackupBookingsFile)) {
            Files.copy(tempBackupBookingsFile, ORIGINAL_BOOKINGS, StandardCopyOption.REPLACE_EXISTING);
            Files.deleteIfExists(tempBackupBookingsFile);
        } else {
            System.out.println("Error restoring original file - could not find backup");
        }
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
    void testBookingFailsWithEmptyFields(FxRobot robot) {
        @SuppressWarnings("unchecked")
        Consumer<CustomDateObject> mockSaveBooking = mock(Consumer.class);
        controller.setSaveBookingFunction(mockSaveBooking);

        robot.clickOn("#bookButton");
        WaitForAsyncUtils.waitForFxEvents();

        verify(mockSaveBooking, never()).accept(any());
    }

    @Test
    void testFillFieldsWithExistingBookingInfo(FxRobot robot) {
        String testName = "Eren";
        String testSurname = "Yeager";
        String testDate = LocalDate.now().plusDays(2).toString();
        String testTime = "15:00";

        javafx.application.Platform.runLater(
                () -> controller.fillFieldsWithExistingBookingInfo(testName, testSurname, testDate, testTime));
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