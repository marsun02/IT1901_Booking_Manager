package persistence;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.util.Collection;
import java.util.HashSet;

public class FileManagement {
    public static void saveRestaurants(File restaurantDataFile, Collection<Restaurant> restaurants) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // enables pretty printing to json file

        try {
            objectMapper.writeValue(restaurantDataFile, restaurants);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Collection<Restaurant> loadRestaurants(File restaurantDataFile) {
        ObjectMapper objectMapper = new ObjectMapper();
        Collection<Restaurant> restaurants;
        try {
            restaurants = objectMapper.readValue(restaurantDataFile, new TypeReference<HashSet<Restaurant>>() {
            });
        } catch (Exception e) {
            restaurants = new HashSet<>();
        }
        return restaurants;
    }



   
    public static void saveBooking(File allBookingsFile, CustomDateObject bookedDate) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // enables pretty printing to json file
        try {
            objectMapper.writeValue(allBookingsFile, bookedDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Collection<CustomDateObject> loadBookingsForSpecificDate(File allBookingsFile, String date, String restaurantName) {
        System.out.println("nå er vi inne i fil-loading funksjonen");
        ObjectMapper objectMapper = new ObjectMapper();
        Collection<CustomDateObject> allBookings;
        Collection<CustomDateObject> matchingDateBookings = new HashSet<>();
        try {
            System.out.println("Nå er vi inne i try");
            allBookings = objectMapper.readValue(allBookingsFile, new TypeReference<HashSet<CustomDateObject>>() {
            });
            for (CustomDateObject customDateObject : allBookings) {
                System.out.println(customDateObject.getDate());
                System.out.println(date);
                if (customDateObject.getDate().equals(date) && customDateObject.getRestaurantName().equals(restaurantName)){
                    matchingDateBookings.add(customDateObject);
                }
        }
        } catch (Exception e) {
            System.out.println("nå er vi i catch");
            matchingDateBookings = new HashSet<>();
        }
        System.out.println("Nå er vi forbi");
        return matchingDateBookings; // returns all bookings made for that specific date
    }
}
