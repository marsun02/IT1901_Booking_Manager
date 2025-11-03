package server.controller;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @GetMapping(params = "user")
    public List<CustomDateObject> getBookingsByUser(
        @RequestParam(name = "user") String user
    ) {
        return FileManagement.loadBookingsForSpecificUser(BOOKING_DATA_FILE, user);
    }

    @GetMapping(params = "id")
    public CustomDateObject getBookingById(
        @RequestParam(name = "id") String id
    ){
        return FileManagement.loadSpecificBooking(BOOKING_DATA_FILE, id);
    }

    @GetMapping
    public Collection<CustomDateObject> getAllBookings(){
        return FileManagement.loadAllBookings(BOOKING_DATA_FILE);
    }

    @PostMapping
    public void saveBooking(
        @RequestBody CustomDateObject bookedDate
    ) {
        FileManagement.saveBooking(BOOKING_DATA_FILE, bookedDate);
    }
}
