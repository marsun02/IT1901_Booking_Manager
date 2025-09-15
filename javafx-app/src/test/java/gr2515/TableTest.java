package gr2515;

import java.util.Date;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import gr2515.core.Table;


//Unit tests for table class

public class TableTest {
    

//Testing Constructor
@Test
void TestConstructorTable(){
    Table table = new Table(1, 6);

    assertEquals(table.getId(), 1);
    assertEquals(table.getSpots(), 6);
    assertFalse(table.getStatus());
    assertNull(table.getCustomerName());
    assertNull(table.getBookedTime());
}

//Testing book method
@Test
void TestBookTable(){
    Table table = new Table(2, 2);
    Date now = new Date();

    table.book("Ola Nordmann", now);

    assertTrue(table.getStatus());
    assertEquals("Ola Nordmann", table.getCustomerName());
    assertEquals(now, table.getBookedTime());
}

//Testing free method on a table that isn't booked
@Test
void TestFreeTableWhenNotBooked(){
    Table table = new Table(3, 7);

    table.free();

    assertFalse(table.getStatus());
    assertNull(table.getCustomerName());
    assertNull(table.getBookedTime());
}
}
