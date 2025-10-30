package core;

import java.util.Collection;
import java.util.HashSet;

import model.Restaurant;
import model.Table;

public class RestaurantInitialization {
    private static Collection<Restaurant> restaurants = new HashSet<>();
    private static String[] restaurantNames = {"Frati", "Szechuan", "BurgerKing", "Barbecuez", "McDonalds", "SteakHouse"};
    private static int tablesPerRestaurant = 8;

    public static Collection<Restaurant> initializeAllRestaurants(){
        restaurants.clear();
        for (int i = 0; i < restaurantNames.length; i++) {
            Restaurant restaurant = new Restaurant(restaurantNames[i], i);
            restaurants.add(restaurant);
            for (int j = 0; j < tablesPerRestaurant; j++) {
                addTableToRestaurant(restaurant, 6);
            }
        }
        return new HashSet<>(restaurants);
    }

    // Add table to a restaurant of choice with automatically assigned id
    public static void addTableToRestaurant(Restaurant restaurant, int spots){
        restaurant.addTable(new Table(restaurant.getNextTableId(), spots));
    }
}
