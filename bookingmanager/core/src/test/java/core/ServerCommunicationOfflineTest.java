package core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import java.util.Collection;

import model.CustomDateObject;
import model.Restaurant;
import model.User;

// All of these tests will trigger printStackTraces in the terminal, however that is intentional as it is supposed to test what happens when the server is down
public class ServerCommunicationOfflineTest {

    @BeforeAll
    static void setupMockServer(){ // But not starting it
        configureFor("localhost", 9999);
        System.setProperty("server.base.url", "http://localhost:9999");
    }

    @Test
    void getAllRestaurantsTest(){
        Collection<Restaurant> restaurants = ServerCommunication.getAllRestaurants();
        assertNotNull(restaurants);
        assertEquals(0, restaurants.size());
    }

    @Test
    void getAllUsersTest(){
        Collection<User> users = ServerCommunication.getAllUsers();
        assertNotNull(users);
        assertEquals(0, users.size());
    }

    @Test
    void getAllBookingsTest(){
        Collection<CustomDateObject> bookings = ServerCommunication.getAllBookings();
        assertNotNull(bookings);
        assertEquals(0, bookings.size());
    }

    @Test
    void getBookingByIdTest(){
        String id = "3";
        CustomDateObject booking = ServerCommunication.getBookingById(id);
        assertNotNull(booking);
        assertEquals(null, booking.getBookedName());
        assertEquals(null, booking.getDate());
        assertEquals(null, booking.getFirstName());
        assertEquals(0, booking.getId());
        assertEquals(null, booking.getLastName());
        assertEquals(null, booking.getRestaurantName());
        assertEquals(null, booking.getTime());
        assertEquals(null, booking.getUser());
    }

    @Test
    void getBookingsByUserTest(){
        User user = new User(0, "Admin", null, null, "12345");
        
        Collection<CustomDateObject> bookings = ServerCommunication.getBookingsByUser(user.getName());
        assertNotNull(bookings);
        assertEquals(0, bookings.size());
    }

    @Test
    void getBookingsByDateAndRestaurantNameTest(){
        String date = "2025-10-31";
        Restaurant restaurant = new Restaurant("SteakHouse", 5, 9);
        
        Collection<CustomDateObject> bookings = ServerCommunication.getBookingsByDateAndRestaurantName(date, restaurant.getName());
        assertNotNull(bookings);
        assertEquals(0, bookings.size());
    }

    @Test
    void updateIdTest(){
        String idType = "userId";
        int id = ServerCommunication.updateId(idType);
        assertNotNull(id);
        assertEquals(-1, id);
    }

    @Test
    void saveUserTest(){
        User user = new User(0, "Admin", null, null, "12345");
        int response = ServerCommunication.saveUser(user);
        assertEquals(1, response);

    }
}
