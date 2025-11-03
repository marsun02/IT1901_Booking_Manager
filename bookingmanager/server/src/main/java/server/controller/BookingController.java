package server.controller;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import model.CustomDateObject;
import server.persistence.FileManagement;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    final Path PATH = Paths.get("../server/src/main/resources/bookings.json");
    final File BOOKING_DATA_FILE = PATH.toFile();

    @GetMapping(params = {"date", "restaurantName"})
    public Collection<CustomDateObject> getBookingsByDateAndRestaurantName(
            @RequestParam(name = "date") String date,
            @RequestParam(name = "restaurantName") String restaurantName
        ) {
        return FileManagement.loadBookingsForSpecificDate(BOOKING_DATA_FILE, date, restaurantName);
    }

    @GetMapping
    public Collection<CustomDateObject> getAllBookings(){
        return FileManagement.loadAllBookings(BOOKING_DATA_FILE);
    }
}
