package server.persistence;

import model.CustomDateObject;
import model.Restaurant;
import model.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class FileManagement {
    public static void saveRestaurants(File restaurantDataFile, Collection<Restaurant> restaurants) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // enables pretty printing to json file

        try {
            objectMapper.writeValue(restaurantDataFile, restaurants);
        } catch (Exception e) {
            // TODO: make proper catch
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

        Collection<CustomDateObject> allBookings = new HashSet<>();
        try {
            if (allBookingsFile.exists() && allBookingsFile.length() > 0){
                allBookings = objectMapper.readValue(allBookingsFile, new TypeReference<HashSet<CustomDateObject>>() {});
            }
        } catch (IOException e) {
            // TODO: make proper catch
            e.printStackTrace();
        }

        allBookings.add(bookedDate);

        try {
            objectMapper.writeValue(allBookingsFile, allBookings);
        } catch (Exception e) {
            // TODO: make proper catch
            e.printStackTrace();
        }
    }

    public static void deleteBooking(File allBookingsFile, CustomDateObject dateToDelete) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // enables pretty printing to json file

        Collection<CustomDateObject> allBookings = new HashSet<>();
        try {
            if (allBookingsFile.exists() && allBookingsFile.length() > 0){
                allBookings = objectMapper.readValue(allBookingsFile, new TypeReference<HashSet<CustomDateObject>>() {});
            }
        } catch (IOException e) {
            // TODO: make proper catch
            e.printStackTrace();
        }

        CustomDateObject bookingToRemove = new CustomDateObject();
        for (CustomDateObject booking : allBookings) {
            // TODO: Implement unique ID for bookings (and should for users too)
            if (booking.getDate().equals(dateToDelete.getDate()) && booking.getTime().equals(dateToDelete.getTime()) && booking.getRestaurantName().equals(dateToDelete.getRestaurantName())){
                bookingToRemove = booking;
            }
        }
        allBookings.remove(bookingToRemove);

        try {
            objectMapper.writeValue(allBookingsFile, allBookings);
        } catch (Exception e) {
            // TODO: make proper catch
            e.printStackTrace();
        }
    }

    public static Collection<CustomDateObject> loadBookingsForSpecificDate(File allBookingsFile, String date, String restaurantName) {
        ObjectMapper objectMapper = new ObjectMapper();
        Collection<CustomDateObject> allBookings;
        Collection<CustomDateObject> matchingDateBookings = new HashSet<>();
        try {
            allBookings = objectMapper.readValue(allBookingsFile, new TypeReference<HashSet<CustomDateObject>>() {
            });
            for (CustomDateObject customDateObject : allBookings) {
                if (customDateObject.getDate().equals(date) && customDateObject.getRestaurantName().equals(restaurantName)){
                    matchingDateBookings.add(customDateObject);
                }
        }
        } catch (Exception e) {
            matchingDateBookings = new HashSet<>();
        }
        return matchingDateBookings; // returns all bookings made for that specific date
    }

    public static List<CustomDateObject> loadBookingsForSpecificUser(File allBookingsFile, String user) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<CustomDateObject> allBookings;
        List<CustomDateObject> matchingDateBookings = new ArrayList<>();
        try {
            allBookings = objectMapper.readValue(allBookingsFile, new TypeReference<ArrayList<CustomDateObject>>() {
            });
            for (CustomDateObject customDateObject : allBookings) {
                if (customDateObject.getUser().equals(user)){
                    matchingDateBookings.add(customDateObject);
                }
        }
        } catch (Exception e) {
            matchingDateBookings = new ArrayList<>();
        }
        return matchingDateBookings; // returns all bookings made for that specific user
    }

    public static Collection<CustomDateObject> loadAllBookings(File allBookingsFile){
        ObjectMapper objectMapper = new ObjectMapper();
        Collection<CustomDateObject> bookings;
        try {
            bookings = objectMapper.readValue(allBookingsFile, new TypeReference<HashSet<CustomDateObject>>() {
            });
        } catch (Exception e) {
            bookings = new HashSet<>();
        }
        return bookings;
    }

    public static CustomDateObject loadSpecificBooking(File allBookingsFile, String stringId) {
        int id = Integer.parseInt(stringId);
        ObjectMapper objectMapper = new ObjectMapper();
        Collection<CustomDateObject> allBookings;
        CustomDateObject matchingBooking = null;
        try {
            allBookings = objectMapper.readValue(allBookingsFile, new TypeReference<HashSet<CustomDateObject>>() {
            });
            for (CustomDateObject customDateObject : allBookings) {
                if (customDateObject.getId().equals(id)){
                    matchingBooking = customDateObject;
                }
        }
        } catch (Exception e) {
            // TODO: make proper exception
            e.printStackTrace();
        }
        return matchingBooking; // returns specific booking
    }





    public static Collection<User> loadUsers(File userDataFile) {
        ObjectMapper objectMapper = new ObjectMapper();
        Collection<User> users;
        try {
            users = objectMapper.readValue(userDataFile, new TypeReference<HashSet<User>>() {});
        } catch (Exception e) {
            users = new HashSet<>();
        }
        return users;
    }

    public static void saveUser(File usersFile, User newUser) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // enables pretty printing to json file

        Collection<User> allUsers = new HashSet<>();
        try {
            if (usersFile.exists() && usersFile.length() > 0){
                allUsers = objectMapper.readValue(usersFile, new TypeReference<HashSet<User>>() {});
            }
        } catch (IOException e) {
            // TODO: make proper catch
            e.printStackTrace();
        }

        allUsers.add(newUser);

        try {
            objectMapper.writeValue(usersFile, allUsers);
        } catch (Exception e) {
            // TODO: make proper catch
            e.printStackTrace();
        }
    }

    public static int updateId(File idsFile, String idType){ // This function gets and updates/increments the given ID
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // enables pretty printing to json file
        Map<String, Integer> ids = new HashMap<>();
        try {
            ids = objectMapper.readValue(idsFile, new TypeReference<Map<String, Integer>>() {});
        } catch (Exception e) {
            // TODO: Implement proper catch
            e.printStackTrace();
        }

        int nextId = ids.getOrDefault(idType, 0) + 1;
        ids.put(idType, nextId);

        try {
            objectMapper.writeValue(idsFile, ids);
        } catch (Exception e) {
            // TODO: Make proper catch
            e.printStackTrace();
        }

        return nextId;

    }
}
