package model;

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
    }
}