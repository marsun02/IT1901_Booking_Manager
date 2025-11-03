package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CustomDateObjectTest {
     @Test
    void testConstructor(){
        CustomDateObject obj = new CustomDateObject(
            "2025-12-24",
            "18.00", 
            "BurgerKing", 
            "testUser", 
            "Ola", 
            "Nordmann", 
            4);

    assertEquals("2025-12-24", obj.getDate());
    assertEquals("18.00", obj.getTime());
    assertEquals("BurgerKing", obj.getRestaurantName());
    assertEquals("testUser", obj.getUser());
    assertEquals("Ola", obj.getFirstName());
    assertEquals("Nordmann", obj.getLastName());
    assertEquals(4, obj.getId());
    }
}