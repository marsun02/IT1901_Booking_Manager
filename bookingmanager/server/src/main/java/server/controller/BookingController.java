package server.controller;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import model.CustomDateObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import server.persistence.FileManagement;

/**
 * Controller for all api calls regarding the bookings data file.
 */
@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    final Path PATH = Paths.get("../server/src/main/resources/bookings.json");
    final File BOOKING_DATA_FILE = PATH.toFile();

    /**
     * Api call for getting bookings with parameters matching the given ones.

     * @param date date to compare with in search.
     * @param restaurantName restaurant name to compare with in search.
     * @return all bookings matching the given date an restaurant name.
     */
    @GetMapping(params = { "date", "restaurantName" })
    public Collection<CustomDateObject> getBookingsByDateAndRestaurantName(
            @RequestParam(name = "date") String date,
            @RequestParam(name = "restaurantName") String restaurantName) {
        return FileManagement.loadBookingsForSpecificDate(BOOKING_DATA_FILE, date, restaurantName);
    }

    /**
     * Api call for getting all bookings made by given user.

     * @param user user to compare with in search.
     * @return all bookings made by given user.
     */
    @GetMapping(params = "user")
    public List<CustomDateObject> getBookingsByUser(
            @RequestParam(name = "user") String user) {
        return FileManagement.loadBookingsForSpecificUser(BOOKING_DATA_FILE, user);
    }

    /**
     * api call for getting booking with the given id.

     * @param id id to compare with in search.
     * @return booking with given id.
     */
    @GetMapping(params = "id")
    public CustomDateObject getBookingById(
            @RequestParam(name = "id") String id) {
        return FileManagement.loadSpecificBooking(BOOKING_DATA_FILE, id);
    }

    /**
     * api call for getting all saved bookings.

     * @return all saved bookings.
     */
    @GetMapping
    public Collection<CustomDateObject> getAllBookings() {
        return FileManagement.loadAllBookings(BOOKING_DATA_FILE);
    }

    /**
     * api call for adding a booking to saved collection.

     * @param bookedDate booking to be added.
     */
    @PostMapping("/save")
    public void saveBooking(
            @RequestBody CustomDateObject bookedDate) {
        FileManagement.saveBooking(BOOKING_DATA_FILE, bookedDate);
    }

    /**
     * api call to delete a booking from the saved collection.

     * @param bookingToDelete booking to be deleted.
     */
    @PostMapping("/delete")
    public void deleteBooking(
            @RequestBody CustomDateObject bookingToDelete) {
        FileManagement.deleteBooking(BOOKING_DATA_FILE, bookingToDelete);
    }
}
