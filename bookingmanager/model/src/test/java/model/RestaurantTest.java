package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RestaurantTest {
    Restaurant restaurant;

    @BeforeEach
    void setUp(){
        restaurant = new Restaurant("Tataki", 1, 8);
    }

    @Test
    void TestConstructorRestaurant(){
        assertEquals("Tataki", restaurant.getName());
        assertEquals(1, restaurant.getId());
        assertNotNull(restaurant.getAmountOfTables());
    }

    @Test
    void TestGettersAndSetters(){     
        restaurant.setName("test");
        assertEquals("test", restaurant.getName());
        
        restaurant.setId(10);
        assertEquals(10, restaurant.getId());
        
        restaurant.setAmountOfTables(15);
        assertEquals(15, restaurant.getAmountOfTables());

        restaurant.setAmountOfTables(-5);
        assertEquals(0, restaurant.getAmountOfTables());
    }

    @Test
    void testInitialize(){
        new Restaurant();
    }

    @Test
    void testCopyInitialize(){
        new Restaurant(restaurant);
    }

    @Test
    void testCopyConstructorTakesNullParameter(){
        Restaurant restaurant = null;
        Restaurant copy = new Restaurant(restaurant);
        assertEquals(null, copy.getName());
        assertEquals(-1, copy.getId());
        assertEquals(0, copy.getAmountOfTables());
    }
}

