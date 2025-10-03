package gr2515.core;

import java.util.Collection;

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

    public static Collection<Restaurant> updateRestaurantsInformation(Restaurant bookedRestaurant, Collection<Restaurant> restaurants){
        for (Restaurant restaurant : restaurants) {
            if (restaurant.getName().equals(bookedRestaurant.getName())){
                restaurant = bookedRestaurant;
            }
        }
        return restaurants;
    }




    // BookingPageController
}
