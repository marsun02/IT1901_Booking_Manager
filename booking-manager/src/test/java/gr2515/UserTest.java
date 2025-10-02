package gr2515;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gr2515.core.Table;
import gr2515.core.User;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    private User user;
    private Table table1;

    @BeforeEach
    void setUp() {
        user = new User(1, "Alice", "alice@example.com", "1234567890", "secret");
        table1 = new Table(101, 4);
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals(1, user.getId());
        assertEquals("Alice", user.getName());
        assertEquals("alice@example.com", user.getEmail());
        assertEquals("1234567890", user.getPhoneNumber());
        assertTrue(user.getBookedTables().isEmpty());
    }

    @Test
    void testCheckNamePasswordCorrect() {
        assertTrue(user.checkNamePassword("Alice", "secret"));
    }

    @Test
    void testCheckNamePasswordIncorrect() {
        assertFalse(user.checkNamePassword("Alice", "wrong"));
        assertFalse(user.checkNamePassword("Bob", "secret"));
        assertFalse(user.checkNamePassword("Bob", "wrong"));
    }

    @Test
    void testAddTable() {
        assertTrue(user.getBookedTables().isEmpty());

        user.addTable(table1);
        assertEquals(1, user.getBookedTables().size());
        assertTrue(user.getBookedTables().contains(table1));
    }

    @Test
    void testAddTableTwice() {
        user.addTable(table1);
        user.addTable(table1); // should not duplicate
        assertEquals(1, user.getBookedTables().size());
    }

    @Test
    void testRemoveTable() {
        user.addTable(table1);
        assertEquals(1, user.getBookedTables().size());

        user.removeTable(table1);
        assertTrue(user.getBookedTables().isEmpty());
    }

    @Test
    void testRemoveTableNotInList() {
        // removing something not booked should not crash
        user.removeTable(table1);
        assertTrue(user.getBookedTables().isEmpty());
    }

    @Test
    void testIntegrationWithTableBooking() {
        // Book table from the Table side
        table1.book(user, new Date());
        assertTrue(table1.isBooked());
        assertEquals(user, table1.getBookingUser());
        assertTrue(user.getBookedTables().contains(table1));

        // Free the table
        table1.freeTable();
        assertFalse(table1.isBooked());
        assertNull(table1.getBookingUser());
        assertFalse(user.getBookedTables().contains(table1));
    }
}
