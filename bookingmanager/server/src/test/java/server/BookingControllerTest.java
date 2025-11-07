package server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import model.CustomDateObject;
// import model.Restaurant;
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
    void getBookingsTest(){
        Collection<CustomDateObject> bookingsByDateAndRestaurant = controller.getBookingsByDateAndRestaurantName("2025-11-12", "McDonalds");
        assertEquals(bookingsByDateAndRestaurant.size(), 2);
        List<CustomDateObject> bookingsByUser = controller.getBookingsByUser( "KjellMagnar");
        assertEquals(bookingsByUser.size(), 1);
        CustomDateObject bookingById = controller.getBookingById("18");
        assertEquals(bookingById.getBookedName(), "testja hais");
        Collection<CustomDateObject> allbookings = controller.getAllBookings();
        assertEquals(allbookings.size(), 8);
        
    } 
}
