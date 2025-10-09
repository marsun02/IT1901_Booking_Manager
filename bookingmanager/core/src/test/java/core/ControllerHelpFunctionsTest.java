package core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Collection;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import persistence.Restaurant;

public class ControllerHelpFunctionsTest {
    Collection<Restaurant> restaurants;
    Restaurant restaurant0;
    Restaurant restaurant1;

    @BeforeEach
    void setUp() {
        restaurants = new HashSet<>();
        restaurant0 = new Restaurant("restaurant0", 0);
        restaurant1 = new Restaurant("restaurant1", 1);
        restaurants.add(restaurant0);
        restaurants.add(restaurant1);
    }

    @Test
    void testGetRestaurantByFxid(){
        assertEquals(restaurant0, ControllerHelpFunctions.getRestaurantByFxid("restaurant0", restaurants));
        assertNotEquals(restaurant1, ControllerHelpFunctions.getRestaurantByFxid("restaurant0", restaurants));
        assertNull(ControllerHelpFunctions.getRestaurantByFxid("restaurant2", restaurants));
    }
}
