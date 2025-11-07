// package core;

// import java.util.Collection;
// import java.util.List;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertFalse;
// import static org.junit.jupiter.api.Assertions.assertNotNull;
// import static org.junit.jupiter.api.Assertions.assertTrue;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;

// import model.CustomDateObject;
// import model.Restaurant;
// import model.User;

// public class ServerCommunicationReadOnlyTest {

//     Collection<Restaurant> restaurants;
//     Collection<User> users;
//     Collection<CustomDateObject> bookings;

//     @BeforeEach
//     void setUp() {
//         restaurants = ServerCommunication.getAllRestaurants();
//         users = ServerCommunication.getAllUsers();
//         bookings = ServerCommunication.getAllBookings();
//     }

//     @Test
//     void testGetAllRestaurants() {
//         assertNotNull(restaurants);
//         assertFalse(restaurants.isEmpty());
//         for (Restaurant r : restaurants) {
//             assertNotNull(r.getName());
//             assertTrue(r.getAmountOfTables() > 0);
//         }
//     }

//     @Test
//     void testGetAllUsers() {
//         assertNotNull(users);
//         for (User u : users) {
//             assertNotNull(u.getName());
//         }
//     }

//     @Test
//     void testGetAllBookings() {
//         assertNotNull(bookings);
//         for (CustomDateObject b : bookings) {
//             assertNotNull(b.getDate());
//         }
//     }

//     @Test
//     void testGetBookingsByDateAndRestaurantName() {
//         Collection<CustomDateObject> result = ServerCommunication.getBookingsByDateAndRestaurantName("2025-01-01", "Frati");
//         assertNotNull(result);
//     }

//     @Test
//     void testGetBookingsByUser() {
//         List<CustomDateObject> result = ServerCommunication.getBookingsByUser("Admin");
//         assertNotNull(result);
//     }

//      @Test
//     void testBookingById() {
//         CustomDateObject booking = ServerCommunication.getBookingById("18");
//         assertNotNull(booking);
//         assertEquals("BurgerKing", booking.getRestaurantName());
//     }

//     @Test
//     void testClassInitialization(){
//         new ServerCommunication();
//     }
    
// }
