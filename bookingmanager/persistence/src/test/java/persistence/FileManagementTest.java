package persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Restaurant;

public class FileManagementTest {
    Collection<Restaurant> restaurants;
    File testFile;
    
    @BeforeEach
    void setUp() {
        restaurants = new ArrayList<>();
        restaurants.add(new Restaurant("Testaurant1", 1));
        restaurants.add(new Restaurant("Testaurant2", 2));
        testFile = new File("test_restaurants.txt");
    }

    @Test
    void testSaveRestaurants() {
        FileManagement.saveRestaurants(testFile, restaurants);
        // Checks that file exists
        assertTrue(testFile.exists());
        // Checks that file contains data
        assertTrue(testFile.length() > 0);

        testFile.delete();
    }

    @Test
    void testLoadRestaurants() {
        FileManagement.saveRestaurants(testFile, restaurants);
        Collection<Restaurant> loadedRestaurants = FileManagement.loadRestaurants(testFile);
        // Checks that loaded HashSet includes the same amount of restaurants as the original saved one
        assertTrue(loadedRestaurants.size() == restaurants.size());
        
        // Code block created with the help of github copilot
        for (Restaurant r : loadedRestaurants) {
            // Checks that the loaded restaurant is in original HashSet for each loaded restaurant
            assertTrue(restaurants.stream().anyMatch(orig -> orig.getName().equals(r.getName()) && orig.getId() == r.getId()));
        }
        // End of code block created with the help of github copilot

        testFile.delete();
    }

    @Test
    void testLoadFromEmptyFile() {
        // Trying to load from testFile before saving any data --> loading from empty file
        Collection<Restaurant> loadedRestaurants = FileManagement.loadRestaurants(testFile);
        assertEquals(loadedRestaurants, new HashSet<>());
    }
}
