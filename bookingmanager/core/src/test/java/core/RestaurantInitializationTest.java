package core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Restaurant;

public class RestaurantInitializationTest {
    Collection<Restaurant> restaurants;
    Collection<String> restaurantNames;
    
    @BeforeEach
    void setUp() {
        restaurantNames = new HashSet<>(Arrays.asList("Frati", "Szechuan", "BurgerKing", "Barbecuez", "McDonalds", "SteakHouse"));
    }

    @Test
    void testInitializeAllRestaurants(){
        restaurants = RestaurantInitialization.initializeAllRestaurants();
        assertEquals(6, restaurants.size());
        for (Restaurant restaurant : restaurants) {
            assertEquals(8, restaurant.getTables().size());
            assertTrue(restaurantNames.contains(restaurant.getName()));
        }
    }
}
