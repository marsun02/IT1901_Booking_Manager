package core;

import java.util.Collection;
import java.util.HashSet;
import model.Restaurant;

/**
 * Class used to initialize the restaurants on app start
 * in the case the loading of restaurants fails or the loaded collection is empty.
 * This is moved out of the controller to avoid cluttering it and the ui module in general.
 */
public class RestaurantInitialization {
    private static Collection<Restaurant> restaurants = new HashSet<>();
    private static String[] restaurantNames = { "Frati", "Szechuan", "BurgerKing", "Barbecuez", "McDonalds",
        "SteakHouse" };
    private static int tablesPerRestaurant = 8;

    /**
     * Creates a collection and fills it with restaurants.
     * The parameters for the restaurants are hard coded in the above fields,
     * as the should always be the same given the design choices of this app.

     * @return new collection containing initial data for the restaurants.
     */
    public static Collection<Restaurant> initializeAllRestaurants() {
        restaurants.clear();
        for (int i = 0; i < restaurantNames.length; i++) {
            Restaurant restaurant = new Restaurant(restaurantNames[i], i, tablesPerRestaurant);
            restaurants.add(restaurant);
        }
        return new HashSet<>(restaurants);
    }
}
