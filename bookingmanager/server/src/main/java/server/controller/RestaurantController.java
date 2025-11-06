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

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {
    final Path PATH = Paths.get("../server/src/main/resources/restaurants.json");
    final File RESTAURANT_DATA_FILE = PATH.toFile();

    @GetMapping
    public Collection<Restaurant> getAllRestaurants() {
        return FileManagement.loadRestaurants(RESTAURANT_DATA_FILE);
    }

    @PostMapping
    public void saveAllRestaurants(@RequestBody Collection<Restaurant> restaurants) {
        FileManagement.saveRestaurants(RESTAURANT_DATA_FILE, restaurants);
    }
}
