package core;

import java.util.Collection;
import java.util.HashSet;

import model.Restaurant;

public class RestaurantInitialization {
    private static Collection<Restaurant> restaurants = new HashSet<>();
    private static String[] restaurantNames = {"Frati", "Szechuan", "BurgerKing", "Barbecuez", "McDonalds", "SteakHouse"};
    private static int tablesPerRestaurant = 8;

    public static Collection<Restaurant> initializeAllRestaurants(){
        restaurants.clear();
        for (int i = 0; i < restaurantNames.length; i++) {
            Restaurant restaurant = new Restaurant(restaurantNames[i], i, tablesPerRestaurant);
            restaurants.add(restaurant);
        }
        return new HashSet<>(restaurants);
    }
}
