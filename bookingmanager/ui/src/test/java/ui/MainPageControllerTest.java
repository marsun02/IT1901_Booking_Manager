package ui;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.CustomDateObject;
import model.Restaurant;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;
import persistence.FileManagement;
import core.ControllerHelpFunctions;

import java.io.File;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
public class MainPageControllerTest {

    private MainPageController controller;
    private Stage stage;
    private Scene bookingScene;
    private Scene loginScene;
    private BookingPageController bookingPageController;

    @Start
    private void start(Stage stage) {
        this.stage = stage;
        controller = new MainPageController();
        bookingPageController = mock(BookingPageController.class);
        bookingScene = new Scene(new Label("Booking Page"));
        loginScene = new Scene(new Label("Login Page"));
        controller.setStage(null, stage, bookingScene, null, loginScene);
        controller.setBookingPageController(bookingPageController);
        controller.loggedInUserLabel = new Label();
        controller.bookingOverview = new TableView<>();
        controller.tableEdit = new TableColumn<>();
        controller.tableDelete = new TableColumn<>();
        controller.tableImage = new TableColumn<>();
        controller.tableName = new TableColumn<>();
        controller.tableDate = new TableColumn<>();
        controller.tableTime = new TableColumn<>();
    }

    @BeforeEach
    public void setup() {
        controller.setcurrentlyLoggedInUser(new User(1, "Kristoffer", null, null, null));
        Platform.runLater(controller::initialize);
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    public void testLoadUserBookings_fullCoverage() {
        CustomDateObject booking = new CustomDateObject("2025-11-03", "18.00", "MockPlace", "Kristoffer", "John", "Doe", 1);
        List<CustomDateObject> bookings = List.of(booking);
        Restaurant restaurant = new Restaurant("MockPlace", 1);
        controller.restaurants = Set.of(restaurant);

        try (MockedStatic<FileManagement> mockFM = Mockito.mockStatic(FileManagement.class);
             MockedStatic<ControllerHelpFunctions> mockCH = Mockito.mockStatic(ControllerHelpFunctions.class)) {

            mockFM.when(() -> FileManagement.loadBookingsForSpecificUser(any(File.class), eq("Kristoffer")))
                  .thenReturn(bookings);
            mockFM.when(() -> FileManagement.loadSpecificBooking(any(File.class), eq(1)))
                  .thenReturn(booking);
            mockFM.when(() -> FileManagement.deleteBooking(any(File.class), eq(booking)))
                  .thenAnswer(inv -> null);

            mockCH.when(() -> ControllerHelpFunctions.sortBookings(bookings)).then(inv -> null);

            Platform.runLater(() -> {
                controller.loadUserBookings();

                // tableImage
                ImageView imageView = controller.tableImage.getCellValueFactory().call(

                    new TableColumn.CellDataFeatures<>(controller.bookingOverview, controller.tableImage, booking)
                ).getValue();
                assertNotNull(imageView);
                assertEquals(100, imageView.getFitWidth());

                // tableName
                String name = controller.tableName.getCellValueFactory().call(
                    new TableColumn.CellDataFeatures<>(controller.bookingOverview, controller.tableName, booking)
                ).getValue();
                assertEquals("John Doe", name);

                // tableDate
                String date = controller.tableDate.getCellValueFactory().call(
                    new TableColumn.CellDataFeatures<>(controller.bookingOverview, controller.tableDate, booking)
                ).getValue();
                assertEquals("2025-11-03", date);

                // tableTime
                String time = controller.tableTime.getCellValueFactory().call(
                    new TableColumn.CellDataFeatures<>(controller.bookingOverview, controller.tableTime, booking)
                ).getValue();
                assertEquals("18.00", time);

                // tableEdit
                Button editButton = controller.tableEdit.getCellValueFactory().call(
                    new TableColumn.CellDataFeatures<>(controller.bookingOverview, controller.tableEdit, booking)
                ).getValue();
                assertNotNull(editButton);
                editButton.getOnMouseClicked().handle(null);

                verify(bookingPageController).setEditState(true, booking);
                verify(bookingPageController).fillFieldsWithExistingBookingInfo("John", "Doe", "2025-11-03", "18.00");
                verify(bookingPageController).setCurrentRestaurantDisplayed(restaurant);
                assertEquals(bookingScene, stage.getScene());

                // tableDelete
                Button deleteButton = controller.tableDelete.getCellValueFactory().call(
                    new TableColumn.CellDataFeatures<>(controller.bookingOverview, controller.tableDelete, booking)
                ).getValue();
                assertNotNull(deleteButton);
                deleteButton.getOnMouseClicked().handle(null);

                mockFM.verify(() -> FileManagement.deleteBooking(any(File.class), eq(booking)), times(1));
            });

            WaitForAsyncUtils.waitForFxEvents();
        }
    }
}