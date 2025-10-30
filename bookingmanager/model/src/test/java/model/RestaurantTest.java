package model;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RestaurantTest {
    Restaurant restaurant;
    Table table1;
    Table table2;

    @BeforeEach
    void setUp(){
        restaurant = new Restaurant("Tataki", 1);
        table1 = new Table(1, 8);
        table2 = new Table(2, 4);
    }

    @Test
    void TestConstructorRestaurant(){
        assertEquals("Tataki", restaurant.getName());
        assertEquals(1, restaurant.getId());
        assertNotNull(restaurant.getTables());
        assertTrue(restaurant.getTables().isEmpty());
    }

    @Test
    void TestAddTablesAndGettersAndSetters(){
        Collection<Table> tables = restaurant.getTables();
        assertTrue(tables.isEmpty());

        restaurant.addTable(table1);
        restaurant.addTable(table2);

        assertEquals(2, restaurant.getAvailableTables().size());
        assertEquals(8, restaurant.getTableById(1).getSpots());
        
        restaurant.setName("test");
        assertEquals("test", restaurant.getName());
        
        restaurant.setId(10);
        assertEquals(10, restaurant.getId());
        
        restaurant.setNextTableId(20);
        assertEquals(20, restaurant.getNextTableId());
        assertEquals(null, restaurant.getTableById(13));

        Table table3 = new Table(5, 8);
        table3.setBooked(true);
        restaurant.addTable(table3);
        assertEquals(2, restaurant.getAvailableTables().size());



        
    }
}

