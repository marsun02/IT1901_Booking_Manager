package server.controller;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import model.Restaurant;
import server.persistence.FileManagement;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {
    final Path PATH = Paths.get("../server/src/main/resources/restaurants.json");
    final File RESTAURANT_DATA_FILE = PATH.toFile();

    @GetMapping
    public Collection<Restaurant> getAllRestaurants(){
        return FileManagement.loadRestaurants(RESTAURANT_DATA_FILE);
    }
}
