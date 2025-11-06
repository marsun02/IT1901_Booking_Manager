package core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.CustomDateObject;
import model.Restaurant;

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

    @Test
    void testSortbookings(){
        List<CustomDateObject> bookings= new ArrayList<>();
        bookings.add(new CustomDateObject("2025-12-24", "18.00", "Burger King", "User", "Ola", "Nordmann", 1));
        bookings.add(new CustomDateObject("2025-12-23", "19.00", "Burger King", "User", "Ola", "Nordmann", 1));
        bookings.add(new CustomDateObject("2025-12-24", "17.00", "Burger King", "User", "Ola", "Nordmann", 1));
        bookings.add(new CustomDateObject("2025-12-23", "17.00", "Burger King", "User", "Ola", "Nordmann", 1));

        ControllerHelpFunctions.sortBookings(bookings);

        assertEquals("2025-12-23", bookings.get(0).getDate());
        assertEquals("17.00", bookings.get(0).getTime());

        assertEquals("2025-12-23", bookings.get(1).getDate());
        assertEquals("19.00", bookings.get(1).getTime());

        assertEquals("2025-12-24", bookings.get(2).getDate());
        assertEquals("17.00", bookings.get(2).getTime());

        assertEquals("2025-12-24", bookings.get(3).getDate());
        assertEquals("18.00", bookings.get(3).getTime());
    }
}
