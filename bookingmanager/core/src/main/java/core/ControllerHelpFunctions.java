package core;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import model.CustomDateObject;
import model.Restaurant;

/**
 * Class containig static helper methods for the controllers to avoid
 * cluttering of the controllers.
 */
public class ControllerHelpFunctions {

    /**
     * Retrieve a restaurant by a given fxid.
     * Decodes the string to find a number matching a restaurants id.
     * Used when displaying the restaurants in the main page.

     * @param fxid string containing the id used by javafx.
     * @param restaurants collection in which to search for the restaurant.
     * @return either a restaurant in the collection with id matching the fxid, or null if no such restaurant exists.
     */
    public static Restaurant getRestaurantByFxid(String fxid, Collection<Restaurant> restaurants) {
        int id = fxid.charAt(fxid.length() - 1) - '0'; // fetches last character in fxid as integer

        for (Restaurant restaurant : restaurants) {
            if (restaurant.getId() == id) {
                return restaurant;
            }
        }

        return null;
    }

    /**
     * Sorts all the bookings in the given List by date.
     * If two (or more) bookings are on the same date, sorts them by time.

     * @param bookings List to sort.
     */
    public static void sortBookings(List<CustomDateObject> bookings) {
        bookings.sort(Comparator.comparing((CustomDateObject booking) -> LocalDate.parse(booking.getDate()))
                .thenComparing((CustomDateObject booking) -> {
                    String timeString = booking.getTime().replace(".", ":"); // Replaces the dot with colon so LocalTime
                    // can parse it
                    return LocalTime.parse(timeString);
                }));
    }
}
