package gr2515;

import java.util.Date;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import gr2515.core.Table;

public class TableTest {
    

    @Test
    void TestBookTable(){
        Table table = new Table(2, 2);
        Date now = new Date();

        table.book("Ola Nordmann", now);

        assertTrue(table.getStatus());
        assertEquals("Ola Nordmann", table.getCustomerName());
        assertEquals(now, table.getBookedTime());
    }

}
