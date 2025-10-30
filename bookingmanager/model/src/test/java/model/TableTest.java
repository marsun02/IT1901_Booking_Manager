package model;

import java.util.Date;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


//Unit tests for table class

public class TableTest {
    

//Testing Constructor
@Test
void TestConstructorTable(){
    Table table = new Table(1, 6);

    assertEquals(table.getId(), 1);
    assertEquals(table.getSpots(), 6);
    assertFalse(table.isBooked());
    assertNull(table.getBookedTime());
}

//Testing book method
@Test
void TestBookTable(){
    Table table = new Table(2, 2);
    Date now = new Date();
    User user = new User(0, null, null, null, null);

    table.book(user, now);

    assertTrue(table.isBooked());
    assertEquals(now, table.getBookedTime());
}

//Testing free method on a table that isn't booked
@Test
void TestFreeTableWhenNotBooked(){
    Table table = new Table(3, 7);
    User user = new User(0, null, null, null, null);

    table.book(user, null);
    table.freeTable(user);

    assertFalse(table.isBooked());
    assertNull(table.getBookedTime());
}
}
