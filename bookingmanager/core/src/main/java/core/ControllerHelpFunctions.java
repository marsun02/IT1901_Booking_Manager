package core;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import persistence.CustomDateObject;
import persistence.Restaurant;

public class ControllerHelpFunctions {

    // MainPageController
    
    public static Restaurant getRestaurantByFxid(String fxid, Collection<Restaurant> restaurants){ 
        int id = fxid.charAt(fxid.length()- 1) - '0'; // fetches last character in fxid as integer

        for (Restaurant restaurant : restaurants){
            if (restaurant.getId() == id){
                return restaurant;
            }
        }

        // TODO: This event needs to be handled as the code crashes if restaurant is null
        return null;
    }

    // This functions sorts bookings based on date first, then on time if they have the same date using a comparator
    public static void sortBookings(List<CustomDateObject> bookings){
        bookings.sort(Comparator.comparing((CustomDateObject booking) -> LocalDate.parse(booking.getDate()))
                                .thenComparing((CustomDateObject booking) -> {
                                    String timeString = booking.getTime().replace(".", ":"); // Replaces the dot with colon so LocalTime can parse it
                                    return LocalTime.parse(timeString);
                                })
        );
    }
    



    // BookingPageController




    // LoginPageController
}
