package server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collection;
import java.util.HashSet;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import model.Restaurant;
import server.controller.RestaurantController;

public class RestaurantControllerTest {
    private final static Path ORIGINAL = Paths.get("../server/src/main/resources/restaurants.json");
    private static Path tempBackupFile;
    RestaurantController controller = new RestaurantController();

    @BeforeAll
    private static void createTempBackupFile() throws IOException{
        try {
            tempBackupFile = Files.createTempFile("restaurantsBackup", ".json");
            Files.copy(ORIGINAL, tempBackupFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println("Could not create backup!");
            throw new IOException("Aborting tests");
        }
    }

    @AfterAll
    private static void restoreOriginalFile() throws IOException{
        if (Files.exists(tempBackupFile)){
            Files.copy(tempBackupFile, ORIGINAL, StandardCopyOption.REPLACE_EXISTING);
            Files.deleteIfExists(tempBackupFile);
        } else{
            System.out.println("Error restoring original file - could not find backup");
        }
    }

    @Test
    void SaveAndGetRestaurantTest(){
        Collection<Restaurant> actualRestaurants = new HashSet<>();
        actualRestaurants.add(new Restaurant());
        assertNotEquals(controller.getAllRestaurants().size(), 0); // Check that the file is not empty to begin with
        Collection<Restaurant> noRestaurants = new HashSet<>();
        controller.saveAllRestaurants(noRestaurants);
        assertEquals(controller.getAllRestaurants().size(), 0); // Check that it now is empty
    }
}
