package server.service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

import org.springframework.stereotype.Service;

import model.Restaurant;
import persistence.FileManagement;

@Service
public class RestaurantService {
    final Path PATH = Paths.get("../server/src/main/resources/restaurants.json");
    final File RESTAURANT_DATA_FILE = PATH.toFile();

    public Collection<Restaurant> getAllRestaurants(){
        return FileManagement.loadRestaurants(RESTAURANT_DATA_FILE);
    }
}
