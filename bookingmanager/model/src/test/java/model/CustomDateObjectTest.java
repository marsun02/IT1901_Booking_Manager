package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
    assertEquals("Ola Nordmann", obj.getBookedName());
    assertEquals(4, obj.getId());
    }

    @Test
    void testEmptyConstructorAndSetters(){
        CustomDateObject obj = new CustomDateObject();
        obj.setDate("2025-12-23");
        obj.setTime("17.00");
        obj.setRestaurantName("Barbequez");
        obj.setUser("User2");
        obj.setFirstName("Peder");
        obj.setLastName("Aas");
        obj.setBookedName("Peder Aas");
        obj.setId(2);

    assertEquals("2025-12-23", obj.getDate());
    assertEquals("17.00", obj.getTime());
    assertEquals("Barbequez", obj.getRestaurantName());
    assertEquals("User2", obj.getUser());
    assertEquals("Peder", obj.getFirstName());
    assertEquals("Aas", obj.getLastName());
    assertEquals("Peder Aas", obj.getBookedName());
    assertEquals(2, obj.getId());
    }

    @Test
    void testChangesAfterConstruction(){
        CustomDateObject obj = new CustomDateObject(
            "2025-12-24",
            "18.00", 
            "BurgerKing", 
            "initUser", 
            "Ola", 
            "Nordmann", 
            4);
    
        obj.setDate("2025-12-23");
        obj.setTime("17.00");
        obj.setRestaurantName("Barbequez");
        obj.setUser("newUser");
        obj.setFirstName("Peder");
        obj.setLastName("Aas");
        obj.setBookedName("Peder Aas");
        obj.setId(2);

    assertEquals("2025-12-23", obj.getDate());
    assertEquals("17.00", obj.getTime());
    assertEquals("Barbequez", obj.getRestaurantName());
    assertEquals("newUser", obj.getUser());
    assertEquals("Peder", obj.getFirstName());
    assertEquals("Aas", obj.getLastName());
    assertEquals("Peder Aas", obj.getBookedName());
    assertEquals(2, obj.getId());
    }

    @Test
    void testIdNotNull(){
        CustomDateObject obj = new CustomDateObject();
        obj.setId(3);
        assertNotNull(obj.getId());
        assertEquals(Integer.valueOf(3), obj.getId());
        assertEquals("3", obj.getId().toString());
    }

    @Test
    void testInitialize(){
        new CustomDateObject();
    }

    @Test
    void testCopyInitialize(){
        CustomDateObject booking = new CustomDateObject();
        new CustomDateObject(booking);
    }
}