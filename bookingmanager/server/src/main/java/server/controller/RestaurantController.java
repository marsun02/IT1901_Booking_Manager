package server.controller;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import model.Restaurant;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.persistence.FileManagement;

/**
 * Controller managing all api calls regarding restaurants data file.
 */
@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {
    final Path path = Paths.get("../server/src/main/resources/restaurants.json");
    final File restaurantDataFile = path.toFile();

    /**
     * api call retrieving all saved restaurants.

     * @return all saved restaurants.
     */
    @GetMapping
    public Collection<Restaurant> getAllRestaurants() {
        return FileManagement.loadRestaurants(restaurantDataFile);
    }

    /**
     * api call saving given restaurants to file (and overriding the previously saved ones).

     * @param restaurants restaurants to be saved.
     */
    @PostMapping
    public void saveAllRestaurants(@RequestBody Collection<Restaurant> restaurants) {
        FileManagement.saveRestaurants(restaurantDataFile, restaurants);
    }
}
