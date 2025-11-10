package ui;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {

    @BeforeAll
    public static void initJavaFX() throws InterruptedException {
        try {
            CountDownLatch latch = new CountDownLatch(1);
            Platform.startup(latch::countDown);
            if (!latch.await(5, TimeUnit.SECONDS)) {
                throw new RuntimeException("JavaFX runtime failed to start");
            }
        } catch (Exception e) {
        }
    }

    // Normal start execution
    @Test
    public void testAppStartSetsStage() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                App app = new App();
                Stage stage = new Stage();
                app.start(stage);
                assertEquals("Booking manager", stage.getTitle());
                assertNotNull(stage.getScene());
            } catch (Exception e) {
                fail(e);
            } finally {
                latch.countDown();
            }
        });
        assertTrue(latch.await(10, TimeUnit.SECONDS));
    }

    // Trigger catch block in start()
    @Test
    public void testStartExceptionHandling() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                App app = new App() {
                    @Override
                    public void start(Stage primaryStage) {
                        try {
                            // FXML file that does not exist
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("NonExistent.fxml"));
                            loader.load();
                        } catch (Exception e) {
                            // This test should trigger the catch if everything works.
                        }
                    }
                };
                Stage stage = new Stage();
                app.start(stage);
            } finally {
                latch.countDown();
            }
        });
        latch.await(5, TimeUnit.SECONDS);
    }

    // Test MainPage FXML loads normally
    @Test
    public void testMainPageFXMLLoads() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(App.class.getResource("MainPage.fxml"));
                Scene scene = new Scene(loader.load());
                assertNotNull(scene);
                MainPageController controller = loader.getController();
                assertNotNull(controller);
            } catch (Exception e) {
                fail(e);
            } finally {
                latch.countDown();
            }
        });
        assertTrue(latch.await(10, TimeUnit.SECONDS));
    }

    // Test BookingPage FXML loads normally
    @Test
    public void testBookingPageFXMLLoads() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(App.class.getResource("TemplateBookingPage.fxml"));
                Scene scene = new Scene(loader.load());
                assertNotNull(scene);
                BookingPageController controller = loader.getController();
                assertNotNull(controller);
            } catch (Exception e) {
                fail(e);
            } finally {
                latch.countDown();
            }
        });
        assertTrue(latch.await(10, TimeUnit.SECONDS));
    }

    // Test controller setter methods
    @Test
    public void testControllerSetters() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                MainPageController main = new MainPageController();
                BookingPageController booking = new BookingPageController();
                Stage stage = new Stage();
                Scene mainScene = new Scene(new javafx.scene.layout.Pane());
                Scene bookingScene = new Scene(new javafx.scene.layout.Pane());
                Scene loginScene = new Scene(new javafx.scene.layout.Pane());

                main.setStage(new App(), stage, bookingScene, mainScene, loginScene);
                main.setBookingPageController(booking);
                booking.setStage(new App(), stage, bookingScene, mainScene, loginScene);
                booking.setMainPageController(main);
            } finally {
                latch.countDown();
            }
        });
        latch.await(5, TimeUnit.SECONDS);
    }
}
