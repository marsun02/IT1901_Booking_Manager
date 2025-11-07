package core;

import org.eclipse.jetty.server.Server;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.github.tomakehurst.wiremock.WireMockServer;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collection;

import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

import model.CustomDateObject;
import model.Restaurant;
import model.User;

public class ServerCommunicationTest {
    private static WireMockServer server;

    @BeforeAll
    static void setupMockServer(){
        server = new WireMockServer(WireMockConfiguration.options().port(8080));
        server.start();
        configureFor("localhost", 8080);
    }

    @AfterAll
    static void closeMockServer(){
        if (server != null){
            server.stop();
        }
    }

    @Test
    void getAllRestaurantsTest(){
        server.stubFor(get(urlEqualTo("/api/restaurants"))
        .willReturn(aResponse()
        .withStatus(200)
        .withHeader("Content-type", "application/json")
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
        .withHeader("Content-type", "application/json")
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
        .withHeader("Content-type", "application/json")
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
}
