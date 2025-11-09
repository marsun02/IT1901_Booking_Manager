package server.persistence;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import model.CustomDateObject;
import model.Restaurant;
import model.User;

/**
 * Class used to directly manipulate data files.
 * The methods in this class are the backend-methods that are to be called by the api.
 */
public class FileManagement {

    /**
     * Saves the given restauratns to the given file.

     * @param restaurantDataFile File in which to write to.
     * @param restaurants Restauranst that are to be written.
     */
    public static void saveRestaurants(File restaurantDataFile, Collection<Restaurant> restaurants) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // enables pretty printing to json file

        try {
            objectMapper.writeValue(restaurantDataFile, restaurants);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads all restaurants from given file.

     * @param restaurantDataFile File to be read from.
     * @return Collection of all restaurants stored in file.
     */
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

    /**
     * Adds a booking to saved bookings.

     * @param allBookingsFile File in which to write.
     * @param bookedDate Booking to be added.
     */
    public static void saveBooking(File allBookingsFile, CustomDateObject bookedDate) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // enables pretty printing to json file

        Collection<CustomDateObject> allBookings = new HashSet<>();
        try {
            if (allBookingsFile.exists() && allBookingsFile.length() > 0) {
                allBookings = objectMapper.readValue(allBookingsFile, new TypeReference<HashSet<CustomDateObject>>() {
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        allBookings.add(bookedDate);

        try {
            objectMapper.writeValue(allBookingsFile, allBookings);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes a booking from given file.

     * @param allBookingsFile File to be manipulated.
     * @param dateToDelete Object representing booking to remove.
     */
    public static void deleteBooking(File allBookingsFile, CustomDateObject dateToDelete) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // enables pretty printing to json file

        Collection<CustomDateObject> allBookings = new HashSet<>();
        try {
            if (allBookingsFile.exists() && allBookingsFile.length() > 0) {
                allBookings = objectMapper.readValue(allBookingsFile, new TypeReference<HashSet<CustomDateObject>>() {
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        CustomDateObject bookingToRemove = new CustomDateObject();
        for (CustomDateObject booking : allBookings) {
            if (booking.getDate().equals(dateToDelete.getDate()) && booking.getTime().equals(dateToDelete.getTime())
                    && booking.getRestaurantName().equals(dateToDelete.getRestaurantName())) {
                bookingToRemove = booking;
            }
        }
        allBookings.remove(bookingToRemove);

        try {
            objectMapper.writeValue(allBookingsFile, allBookings);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads bookings matching given date and restaurant name.

     * @param allBookingsFile file to be read from.
     * @param date date to compare with in search.
     * @param restaurantName restaurant name to compare with in search.
     * @return Collections of all bookings matching restaurant name and date.
     */
    public static Collection<CustomDateObject> loadBookingsForSpecificDate(File allBookingsFile, String date,
            String restaurantName) {
        ObjectMapper objectMapper = new ObjectMapper();
        Collection<CustomDateObject> allBookings;
        Collection<CustomDateObject> matchingDateBookings = new HashSet<>();
        try {
            allBookings = objectMapper.readValue(allBookingsFile, new TypeReference<HashSet<CustomDateObject>>() {
            });
            for (CustomDateObject customDateObject : allBookings) {
                if (customDateObject.getDate().equals(date)
                        && customDateObject.getRestaurantName().equals(restaurantName)) {
                    matchingDateBookings.add(customDateObject);
                }
            }
        } catch (IOException e) {
            matchingDateBookings = new HashSet<>();
        }
        return matchingDateBookings; // returns all bookings made for that specific date
    }

    /**
     * Retrieves all bookings linked to given user.

     * @param allBookingsFile file to be read from.
     * @param user User to compare with in search.
     * @return All bookings made by given user.
     */
    public static List<CustomDateObject> loadBookingsForSpecificUser(File allBookingsFile, String user) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<CustomDateObject> allBookings;
        List<CustomDateObject> matchingDateBookings = new ArrayList<>();
        try {
            allBookings = objectMapper.readValue(allBookingsFile, new TypeReference<ArrayList<CustomDateObject>>() {
            });
            for (CustomDateObject customDateObject : allBookings) {
                if (customDateObject.getUser().equals(user)) {
                    matchingDateBookings.add(customDateObject);
                }
            }
        } catch (IOException e) {
            matchingDateBookings = new ArrayList<>();
        }
        return matchingDateBookings; // returns all bookings made for that specific user
    }

    /**
     * Retrieves all saved booknigs.

     * @param allBookingsFile file to be read from.
     * @return all saved bookings.
     */
    public static Collection<CustomDateObject> loadAllBookings(File allBookingsFile) {
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

    /**
     * Retrieves a singular booking with id matching the given id.

     * @param allBookingsFile file to be read from.
     * @param stringId Id in string format to compare with in search.
     * @return booking with matching id.
     */
    public static CustomDateObject loadSpecificBooking(File allBookingsFile, String stringId) {
        int id = Integer.parseInt(stringId);
        ObjectMapper objectMapper = new ObjectMapper();
        Collection<CustomDateObject> allBookings;
        CustomDateObject matchingBooking = null;
        try {
            allBookings = objectMapper.readValue(allBookingsFile, new TypeReference<HashSet<CustomDateObject>>() {
            });
            for (CustomDateObject customDateObject : allBookings) {
                if (customDateObject.getId().equals(id)) {
                    matchingBooking = customDateObject;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return matchingBooking; // returns specific booking
    }

    /**
     * Retrieves all saved users.

     * @param userDataFile file to be read from.
     * @return all saved users.
     */
    public static Collection<User> loadUsers(File userDataFile) {
        ObjectMapper objectMapper = new ObjectMapper();
        Collection<User> users;
        try {
            users = objectMapper.readValue(userDataFile, new TypeReference<HashSet<User>>() {
            });
        } catch (Exception e) {
            users = new HashSet<>();
        }
        return users;
    }

    /**
     * add one user to saved users.

     * @param usersFile file to be manipulated.
     * @param newUser user to be added.
     */
    public static void saveUser(File usersFile, User newUser) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // enables pretty printing to json file

        Collection<User> allUsers = new HashSet<>();
        try {
            if (usersFile.exists() && usersFile.length() > 0) {
                allUsers = objectMapper.readValue(usersFile, new TypeReference<HashSet<User>>() {
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        allUsers.add(newUser);

        try {
            objectMapper.writeValue(usersFile, allUsers);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * uses given string to determine which id to update.
     * updates the corresponding id by reading, incrementing by one and returning and writing updated id to file.

     * @param idsFile File to be manipulated.
     * @param idType string determining which of the saved ids to update.
     * @return updated id of correct type.
     */
    public static int updateId(File idsFile, String idType) { // This function gets and updates/increments the given ID
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // enables pretty printing to json file
        Map<String, Integer> ids = new HashMap<>();
        try {
            ids = objectMapper.readValue(idsFile, new TypeReference<Map<String, Integer>>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        int nextId = ids.getOrDefault(idType, 0) + 1;
        ids.put(idType, nextId);

        try {
            objectMapper.writeValue(idsFile, ids);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return nextId;
    }
}
