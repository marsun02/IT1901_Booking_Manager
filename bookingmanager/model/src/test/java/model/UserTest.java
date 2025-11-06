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

    @Test
    void testEmptyconstructorAndSetters(){
        User emptyUser = new User();
        assertNull(emptyUser.getEmail());
        assertEquals(0, emptyUser.getId());
        assertNull(emptyUser.getName());
        assertNull(emptyUser.getPhoneNumber());
        assertFalse(emptyUser.checkNamePassword(null, null)); // Should return false when either name or password is false

        emptyUser.setName("name");
        assertEquals("name", emptyUser.getName());

        emptyUser.setId(1);
        assertEquals(1, emptyUser.getId());

        emptyUser.setEmail("e@mail.test");
        assertEquals("e@mail.test", emptyUser.getEmail());

        emptyUser.setPhoneNumber("12345678");
        assertEquals("12345678", emptyUser.getPhoneNumber());

        emptyUser.setPassword("password");
        assertTrue(emptyUser.checkNamePassword("name", "password"));
    }
}
