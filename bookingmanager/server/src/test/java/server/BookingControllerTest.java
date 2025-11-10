package server;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import model.CustomDateObject;
import server.controller.BookingController;

public class BookingControllerTest {
    private final static Path ORIGINAL = Paths.get("../server/src/main/resources/bookings.json");
    private static Path tempBackupFile;
    BookingController controller = new BookingController();

    @BeforeAll
    private static void createTempBackupFile() throws IOException{
        try {
            tempBackupFile = Files.createTempFile("bookingsBackup", ".json");
            Files.copy(ORIGINAL, tempBackupFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println("Could not create backup!");
            throw new IOException("Aborting tests");
        }
    }

    @AfterAll
    private static void restoreOriginalFile() throws IOException{
        if (Files.exists(tempBackupFile)){
            Files.copy(tempBackupFile, ORIGINAL, StandardCopyOption.REPLACE_EXISTING);
            Files.deleteIfExists(tempBackupFile);
        } else{
            System.out.println("Error restoring original file - could not find backup");
        }
    }

    @Test 
    void gettersAndDeleteBookingAndSaveBookingTest(){
        Collection<CustomDateObject> bookings = controller.getAllBookings();
        CustomDateObject booking = new CustomDateObject("2026-10-12", "16.30", "Frati", "admin", "ad", "min", 30);
        controller.saveBooking(booking);
        Collection<CustomDateObject> bookingsWithAddedBooking = controller.getAllBookings();
        assertEquals(bookings.size() + 1, bookingsWithAddedBooking.size());
        CustomDateObject bookingById = controller.getBookingById("30");
        assertEquals(booking.getBookedName(), bookingById.getBookedName());
        Collection<CustomDateObject> bookingsByDateAndRestaurant = controller.getBookingsByDateAndRestaurantName("2026-10-12", "Frati");
        assertNotNull(bookingsByDateAndRestaurant, "getBookingsByDateAndRestaurantName returnerte null");
        assertTrue(bookingsByDateAndRestaurant.stream().anyMatch(b -> b.getUser().equalsIgnoreCase(booking.getUser().trim()) && b.getDate().equals(booking.getDate()) && b.getRestaurantName().equalsIgnoreCase(booking.getRestaurantName())));
        List<CustomDateObject> bookingsByUser = controller.getBookingsByUser("admin");
        assertTrue(bookingsByUser.stream().anyMatch(b -> b.getUser().equalsIgnoreCase(booking.getUser().trim()) && b.getDate().equals(booking.getDate()) && b.getRestaurantName().equalsIgnoreCase(booking.getRestaurantName())), "Booking not found by user");
        controller.deleteBooking(booking);
        Collection<CustomDateObject> bookingsAfterDelete = controller.getAllBookings();
        assertEquals(bookingsWithAddedBooking.size() - 1, bookingsAfterDelete.size());
    }
}
