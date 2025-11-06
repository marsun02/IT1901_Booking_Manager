package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User(1, "Alice", "alice@example.com", "1234567890", "secret");
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals(1, user.getId());
        assertEquals("Alice", user.getName());
        assertEquals("alice@example.com", user.getEmail());
        assertEquals("1234567890", user.getPhoneNumber());
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
}
