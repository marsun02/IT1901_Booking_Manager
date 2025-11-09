package core;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.github.tomakehurst.wiremock.WireMockServer;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Collection;
import java.util.HashSet;

import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

import model.CustomDateObject;
import model.Restaurant;
import model.User;

public class ServerCommunicationOnlineTest {
    private static WireMockServer server;

    @BeforeAll
    static void setupMockServer(){
        server = new WireMockServer(WireMockConfiguration.options().port(9999));
        server.start();
        configureFor("localhost", 9999);
        System.setProperty("server.base.url", "http://localhost:9999");
    }

    @AfterAll
    static void closeMockServer(){
        if (server != null){
            server.stop();
        }
    }

    @Test
    void testInitialize(){
        new ServerCommunication();
    }


    // GET

    @Test
    void getAllRestaurantsTest(){
        server.stubFor(get(urlEqualTo("/api/restaurants"))
        .willReturn(aResponse()
        .withStatus(200)
        .withHeader("content-type", "application/json")
        // The content inside withBody is just a copy/paste of our json file contents.
        .withBody("[ {\r\n" + //
                        "  \"id\" : 3,\r\n" + //
                        "  \"name\" : \"Barbecuez\",\r\n" + //
                        "  \"amountOfTables\" : 8\r\n" + //
                        "}, {\r\n" + //
                        "  \"id\" : 2,\r\n" + //
                        "  \"name\" : \"BurgerKing\",\r\n" + //
                        "  \"amountOfTables\" : 10\r\n" + //
                        "}, {\r\n" + //
                        "  \"id\" : 5,\r\n" + //
                        "  \"name\" : \"SteakHouse\",\r\n" + //
                        "  \"amountOfTables\" : 9\r\n" + //
                        "}, {\r\n" + //
                        "  \"id\" : 4,\r\n" + //
                        "  \"name\" : \"McDonalds\",\r\n" + //
                        "  \"amountOfTables\" : 12\r\n" + //
                        "}, {\r\n" + //
                        "  \"id\" : 0,\r\n" + //
                        "  \"name\" : \"Frati\",\r\n" + //
                        "  \"amountOfTables\" : 8\r\n" + //
                        "}, {\r\n" + //
                        "  \"id\" : 1,\r\n" + //
                        "  \"name\" : \"Szechuan\",\r\n" + //
                        "  \"amountOfTables\" : 8\r\n" + //
                        "} ]")));
        Collection<Restaurant> restaurants = ServerCommunication.getAllRestaurants();
        assertEquals(6, restaurants.size());
    }

    @Test
    void getAllUsersTest(){
        server.stubFor(get(urlEqualTo("/api/users"))
        .willReturn(aResponse()
        .withStatus(200)
        .withHeader("content-type", "application/json")
        // The content inside withBody is just a copy/paste of our json file contents.
        .withBody("[ {\r\n" + //
                        "  \"id\" : 1,\r\n" + //
                        "  \"name\" : \"testaccountyay\",\r\n" + //
                        "  \"email\" : null,\r\n" + //
                        "  \"password\" : \"password\",\r\n" + //
                        "  \"phoneNumber\" : null\r\n" + //
                        "}, {\r\n" + //
                        "  \"id\" : 0,\r\n" + //
                        "  \"name\" : \"Admin\",\r\n" + //
                        "  \"email\" : null,\r\n" + //
                        "  \"password\" : \"12345\",\r\n" + //
                        "  \"phoneNumber\" : null\r\n" + //
                        "} ]")));
        Collection<User> users = ServerCommunication.getAllUsers();
        assertEquals(2, users.size());
    }

    @Test
    void getAllBookingsTest(){
        server.stubFor(get(urlEqualTo("/api/bookings"))
        .willReturn(aResponse()
        .withStatus(200)
        .withHeader("content-type", "application/json")
        // The content inside withBody is just a copy/paste of our json file contents.
        .withBody("[ {\r\n" + //
                        "  \"date\" : \"2025-10-30\",\r\n" + //
                        "  \"time\" : \"16.30\",\r\n" + //
                        "  \"restaurantName\" : \"Frati\",\r\n" + //
                        "  \"user\" : \"Admin\",\r\n" + //
                        "  \"bookedName\" : \"jajatest woho\",\r\n" + //
                        "  \"firstName\" : \"jajatest\",\r\n" + //
                        "  \"lastName\" : \"woho\",\r\n" + //
                        "  \"id\" : 15\r\n" + //
                        "}, {\r\n" + //
                        "  \"date\" : \"2025-10-31\",\r\n" + //
                        "  \"time\" : \"16:39\",\r\n" + //
                        "  \"restaurantName\" : \"BurgerKing\",\r\n" + //
                        "  \"user\" : \"Admin\",\r\n" + //
                        "  \"bookedName\" : \"testja hais\",\r\n" + //
                        "  \"firstName\" : \"testja\",\r\n" + //
                        "  \"lastName\" : \"hais\",\r\n" + //
                        "  \"id\" : 18\r\n" + //
                        "}, {\r\n" + //
                        "  \"date\" : \"2025-11-12\",\r\n" + //
                        "  \"time\" : \"15:20\",\r\n" + //
                        "  \"restaurantName\" : \"McDonalds\",\r\n" + //
                        "  \"user\" : \"Admin\",\r\n" + //
                        "  \"bookedName\" : \"test2 test2\",\r\n" + //
                        "  \"firstName\" : \"test2\",\r\n" + //
                        "  \"lastName\" : \"test2\",\r\n" + //
                        "  \"id\" : 25\r\n" + //
                        "} ]")));
        Collection<CustomDateObject> bookings = ServerCommunication.getAllBookings();
        assertEquals(3, bookings.size());
    }

    @Test
    void getBookingByIdTest(){
        String id = "3";

        server.stubFor(get(urlPathEqualTo("/api/bookings"))
        .withQueryParam("id", equalTo(id))
        .willReturn(aResponse()
        .withStatus(200)
        .withHeader("content-type", "application/json")
        // The content inside withBody is just a copy/paste of our json file contents.
        .withBody("{\r\n" + //
                        "  \"date\" : \"2025-11-12\",\r\n" + //
                        "  \"time\" : \"17:30\",\r\n" + //
                        "  \"restaurantName\" : \"McDonalds\",\r\n" + //
                        "  \"user\" : \"Admin\",\r\n" + //
                        "  \"bookedName\" : \"test test\",\r\n" + //
                        "  \"firstName\" : \"test\",\r\n" + //
                        "  \"lastName\" : \"test\",\r\n" + //
                        "  \"id\" : 3\r\n" + //
                        "}")));
        CustomDateObject booking = ServerCommunication.getBookingById(id);
        assertNotNull(booking);
        assertEquals(3, booking.getId());
    }

    @Test
    void getBookingsByUserTest(){
        User user = new User(0, "Admin", null, null, "12345");
        String param = user.getName();
        
        server.stubFor(get(urlPathEqualTo("/api/bookings"))
        .withQueryParam("user", equalTo(param))
        .willReturn(aResponse()
        .withStatus(200)
        .withHeader("content-type", "application/json")
        // The content inside withBody is just a copy/paste of our json file contents.
        .withBody("[ {\r\n" + //
                        "  \"date\" : \"2025-10-30\",\r\n" + //
                        "  \"time\" : \"16.30\",\r\n" + //
                        "  \"restaurantName\" : \"Frati\",\r\n" + //
                        "  \"user\" : \"Admin\",\r\n" + //
                        "  \"bookedName\" : \"jajatest woho\",\r\n" + //
                        "  \"firstName\" : \"jajatest\",\r\n" + //
                        "  \"lastName\" : \"woho\",\r\n" + //
                        "  \"id\" : 15\r\n" + //
                        "}, {\r\n" + //
                        "  \"date\" : \"2025-10-31\",\r\n" + //
                        "  \"time\" : \"16:39\",\r\n" + //
                        "  \"restaurantName\" : \"BurgerKing\",\r\n" + //
                        "  \"user\" : \"Admin\",\r\n" + //
                        "  \"bookedName\" : \"testja hais\",\r\n" + //
                        "  \"firstName\" : \"testja\",\r\n" + //
                        "  \"lastName\" : \"hais\",\r\n" + //
                        "  \"id\" : 18\r\n" + //
                        "}, {\r\n" + //
                        "  \"date\" : \"2025-11-12\",\r\n" + //
                        "  \"time\" : \"15:20\",\r\n" + //
                        "  \"restaurantName\" : \"McDonalds\",\r\n" + //
                        "  \"user\" : \"Admin\",\r\n" + //
                        "  \"bookedName\" : \"test2 test2\",\r\n" + //
                        "  \"firstName\" : \"test2\",\r\n" + //
                        "  \"lastName\" : \"test2\",\r\n" + //
                        "  \"id\" : 25\r\n" + //
                        "}, {\r\n" + //
                        "  \"date\" : \"2025-11-12\",\r\n" + //
                        "  \"time\" : \"17:30\",\r\n" + //
                        "  \"restaurantName\" : \"McDonalds\",\r\n" + //
                        "  \"user\" : \"Admin\",\r\n" + //
                        "  \"bookedName\" : \"test test\",\r\n" + //
                        "  \"firstName\" : \"test\",\r\n" + //
                        "  \"lastName\" : \"test\",\r\n" + //
                        "  \"id\" : 24\r\n" + //
                        "} ]")));
        Collection<CustomDateObject> bookings = ServerCommunication.getBookingsByUser(user.getName());
        assertEquals(4, bookings.size());
        for (CustomDateObject booking : bookings) {
            assertEquals(user.getName(), booking.getUser());
            }
    }

    @Test
    void getBookingsByDateAndRestaurantNameTest(){
        String date = "2025-10-31";
        Restaurant restaurant = new Restaurant("SteakHouse", 5, 9);
        
        server.stubFor(get(urlPathEqualTo("/api/bookings"))
        .withQueryParam("date", equalTo(date))
        .withQueryParam("restaurantName", equalTo(restaurant.getName()))
        .willReturn(aResponse()
        .withStatus(200)
        .withHeader("content-type", "application/json")
        // The content inside withBody is just a copy/paste of our json file contents.
        .withBody("[ {\r\n" + //
                        "  \"date\" : \"2025-10-31\",\r\n" + //
                        "  \"time\" : \"22.00\",\r\n" + //
                        "  \"restaurantName\" : \"SteakHouse\",\r\n" + //
                        "  \"user\" : \"KjellMagnar\",\r\n" + //
                        "  \"bookedName\" : \"e a\",\r\n" + //
                        "  \"firstName\" : \"e\",\r\n" + //
                        "  \"lastName\" : \"a\",\r\n" + //
                        "  \"id\" : 16\r\n" + //
                        "}, {\r\n" + //
                        "  \"date\" : \"2025-10-31\",\r\n" + //
                        "  \"time\" : \"18:13\",\r\n" + //
                        "  \"restaurantName\" : \"SteakHouse\",\r\n" + //
                        "  \"user\" : \"Admin\",\r\n" + //
                        "  \"bookedName\" : \"test2 jada\",\r\n" + //
                        "  \"firstName\" : \"test2\",\r\n" + //
                        "  \"lastName\" : \"jada\",\r\n" + //
                        "  \"id\" : 19\r\n" + //
                        "} ]")));
        Collection<CustomDateObject> bookings = ServerCommunication.getBookingsByDateAndRestaurantName(date, restaurant.getName());
        assertEquals(2, bookings.size());
        for (CustomDateObject booking : bookings) {
            assertEquals(date, booking.getDate());
            assertEquals(restaurant.getName(), booking.getRestaurantName());
            }
    }

    @Test
    void updateIdTest(){
        String idType = "userId";

        server.stubFor(get(urlPathEqualTo("/api/ids"))
        .withQueryParam("idType", equalTo(idType))
        .willReturn(aResponse()
        .withStatus(200)
        .withHeader("content-type", "application/json")
        .withBody("1")));
        int id = ServerCommunication.updateId(idType);
        assertNotNull(id);
        assertEquals(1, id);
    }


    // POST
    @Test
    void saveAllRestaurantsTest(){
        server.stubFor(post(urlEqualTo("/api/restaurants"))
        // The content inside withRequestBody is just a copy/paste of our json file contents.
        .withRequestBody(equalToJson("[ {\r\n" + //
                        "  \"id\" : 3,\r\n" + //
                        "  \"name\" : \"Barbecuez\",\r\n" + //
                        "  \"amountOfTables\" : 8\r\n" + //
                        "}, {\r\n" + //
                        "  \"id\" : 2,\r\n" + //
                        "  \"name\" : \"BurgerKing\",\r\n" + //
                        "  \"amountOfTables\" : 10\r\n" + //
                        "}, {\r\n" + //
                        "  \"id\" : 5,\r\n" + //
                        "  \"name\" : \"SteakHouse\",\r\n" + //
                        "  \"amountOfTables\" : 9\r\n" + //
                        "}, {\r\n" + //
                        "  \"id\" : 4,\r\n" + //
                        "  \"name\" : \"McDonalds\",\r\n" + //
                        "  \"amountOfTables\" : 12\r\n" + //
                        "}, {\r\n" + //
                        "  \"id\" : 0,\r\n" + //
                        "  \"name\" : \"Frati\",\r\n" + //
                        "  \"amountOfTables\" : 8\r\n" + //
                        "}, {\r\n" + //
                        "  \"id\" : 1,\r\n" + //
                        "  \"name\" : \"Szechuan\",\r\n" + //
                        "  \"amountOfTables\" : 8\r\n" + //
                        "} ]", true, true))
        .willReturn(aResponse()
        .withStatus(200)));

        Collection<Restaurant> restaurants = new HashSet<>();
        restaurants.add(new Restaurant("Barbecuez",3, 8));
        restaurants.add(new Restaurant("BurgerKing",2, 10));
        restaurants.add(new Restaurant("SteakHouse",5, 9));
        restaurants.add(new Restaurant("McDonalds",4, 12));
        restaurants.add(new Restaurant("Frati",0, 8));
        restaurants.add(new Restaurant("Szechuan",1, 8));
        ServerCommunication.saveAllRestaurants(restaurants);
        
        verify(postRequestedFor(urlEqualTo("/api/restaurants"))
        .withHeader("content-type", equalTo("application/json"))
        .withRequestBody(equalToJson("[ {\r\n" + //
                            "  \"id\" : 3,\r\n" + //
                            "  \"name\" : \"Barbecuez\",\r\n" + //
                            "  \"amountOfTables\" : 8\r\n" + //
                            "}, {\r\n" + //
                            "  \"id\" : 2,\r\n" + //
                            "  \"name\" : \"BurgerKing\",\r\n" + //
                            "  \"amountOfTables\" : 10\r\n" + //
                            "}, {\r\n" + //
                            "  \"id\" : 5,\r\n" + //
                            "  \"name\" : \"SteakHouse\",\r\n" + //
                            "  \"amountOfTables\" : 9\r\n" + //
                            "}, {\r\n" + //
                            "  \"id\" : 4,\r\n" + //
                            "  \"name\" : \"McDonalds\",\r\n" + //
                            "  \"amountOfTables\" : 12\r\n" + //
                            "}, {\r\n" + //
                            "  \"id\" : 0,\r\n" + //
                            "  \"name\" : \"Frati\",\r\n" + //
                            "  \"amountOfTables\" : 8\r\n" + //
                            "}, {\r\n" + //
                            "  \"id\" : 1,\r\n" + //
                            "  \"name\" : \"Szechuan\",\r\n" + //
                            "  \"amountOfTables\" : 8\r\n" + //
                            "} ]", true, true)));
    }

    @Test
    void saveBookingTest(){
        server.stubFor(post(urlEqualTo("/api/bookings/save"))
        // The content inside withRequestBody is just a copy/paste of our json file contents.
        .withRequestBody(equalToJson("{\r\n" + //
                        "  \"date\" : \"2025-10-30\",\r\n" + //
                        "  \"time\" : \"16.30\",\r\n" + //
                        "  \"restaurantName\" : \"Frati\",\r\n" + //
                        "  \"user\" : \"Admin\",\r\n" + //
                        "  \"bookedName\" : \"jajatest woho\",\r\n" + //
                        "  \"firstName\" : \"jajatest\",\r\n" + //
                        "  \"lastName\" : \"woho\",\r\n" + //
                        "  \"id\" : 15\r\n" + //
                        "}", true, true))
        .willReturn(aResponse()
        .withStatus(200)));

        CustomDateObject booking = new CustomDateObject("2025-10-30", "16.30", "Frati", "Admin", "jajatest", "woho", 15);
        ServerCommunication.saveBooking(booking);
        
        verify(postRequestedFor(urlEqualTo("/api/bookings/save"))
        .withHeader("content-type", equalTo("application/json"))
        .withRequestBody(equalToJson("{\r\n" + //
                        "  \"date\" : \"2025-10-30\",\r\n" + //
                        "  \"time\" : \"16.30\",\r\n" + //
                        "  \"restaurantName\" : \"Frati\",\r\n" + //
                        "  \"user\" : \"Admin\",\r\n" + //
                        "  \"bookedName\" : \"jajatest woho\",\r\n" + //
                        "  \"firstName\" : \"jajatest\",\r\n" + //
                        "  \"lastName\" : \"woho\",\r\n" + //
                        "  \"id\" : 15\r\n" + //
                        "}", true, true)));
    }

    @Test
    void deleteBookingTest(){
        server.stubFor(post(urlEqualTo("/api/bookings/delete"))
        // The content inside withRequestBody is just a copy/paste of our json file contents.
        .withRequestBody(equalToJson("{\r\n" + //
                        "  \"date\" : \"2025-10-30\",\r\n" + //
                        "  \"time\" : \"16.30\",\r\n" + //
                        "  \"restaurantName\" : \"Frati\",\r\n" + //
                        "  \"user\" : \"Admin\",\r\n" + //
                        "  \"bookedName\" : \"jajatest woho\",\r\n" + //
                        "  \"firstName\" : \"jajatest\",\r\n" + //
                        "  \"lastName\" : \"woho\",\r\n" + //
                        "  \"id\" : 15\r\n" + //
                        "}", true, true))
        .willReturn(aResponse()
        .withStatus(200)));

        CustomDateObject bookingToDelete = new CustomDateObject("2025-10-30", "16.30", "Frati", "Admin", "jajatest", "woho", 15);
        ServerCommunication.deleteBooking(bookingToDelete);
        
        verify(postRequestedFor(urlEqualTo("/api/bookings/delete"))
        .withHeader("content-type", equalTo("application/json"))
        .withRequestBody(equalToJson("{\r\n" + //
                        "  \"date\" : \"2025-10-30\",\r\n" + //
                        "  \"time\" : \"16.30\",\r\n" + //
                        "  \"restaurantName\" : \"Frati\",\r\n" + //
                        "  \"user\" : \"Admin\",\r\n" + //
                        "  \"bookedName\" : \"jajatest woho\",\r\n" + //
                        "  \"firstName\" : \"jajatest\",\r\n" + //
                        "  \"lastName\" : \"woho\",\r\n" + //
                        "  \"id\" : 15\r\n" + //
                        "}", true, true)));
    }

    @Test
    void saveUserTest(){
        server.stubFor(post(urlEqualTo("/api/users"))
        // The content inside withRequestBody is just a copy/paste of our json file contents.
        .withRequestBody(equalToJson("{\r\n" + //
                        "  \"id\" : 0,\r\n" + //
                        "  \"name\" : \"Admin\",\r\n" + //
                        "  \"email\" : null,\r\n" + //
                        "  \"password\" : \"12345\",\r\n" + //
                        "  \"phoneNumber\" : null\r\n" + //
                        "}", true, true))
        .willReturn(aResponse()
        .withStatus(200)));

        User user = new User(0, "Admin", null, null, "12345");
        ServerCommunication.saveUser(user);
        
        verify(postRequestedFor(urlEqualTo("/api/users"))
        .withHeader("content-type", equalTo("application/json"))
        .withRequestBody(equalToJson("{\r\n" + //
                        "  \"id\" : 0,\r\n" + //
                        "  \"name\" : \"Admin\",\r\n" + //
                        "  \"email\" : null,\r\n" + //
                        "  \"password\" : \"12345\",\r\n" + //
                        "  \"phoneNumber\" : null\r\n" + //
                        "}", true, true)));
    }

}
