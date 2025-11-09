package server;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collection;

import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
        CustomDateObject bookingById = controller.getBookingById("30");
        assertEquals(booking.getBookedName(), bookingById.getBookedName());
        assertEquals(bookingsWithAddedBooking.size(), bookings.size() + 1);
        
        controller.deleteBooking(booking);
        Collection<CustomDateObject> bookingsAfterDelete = controller.getAllBookings();
        assertEquals(bookingsAfterDelete.size(), bookingsWithAddedBooking.size() - 1);
    }
}
