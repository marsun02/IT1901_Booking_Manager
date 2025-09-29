package gr2515;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import gr2515.core.Restaurant;

public class RestaurantTest {

    @Test
    void TestConstructorRestaurant(){
        Restaurant restaurant = new Restaurant("Tataki", 1);
        // Test for name and id
        assertEquals("Tataki", restaurant.getName());
        assertEquals(1, restaurant.getId());

        // Test if there is tables in the resturant and checks that is not null
        assertNotNull(restaurant.getTables());
        assertTrue(restaurant.getTables().isEmpty());

    }
    
    

    
}
