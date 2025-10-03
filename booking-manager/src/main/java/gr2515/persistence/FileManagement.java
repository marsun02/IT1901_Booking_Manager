package gr2515.persistence;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import gr2515.core.Restaurant;

public class FileManagement {
    public static void saveRestaurants(File restaurantDataFile, Collection<Restaurant> restaurants){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // enables pretty printing to json file

        try {
            objectMapper.writeValue(restaurantDataFile, restaurants);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Collection<Restaurant> loadRestaurants(File restaurantDataFile){
        ObjectMapper objectMapper = new ObjectMapper();
        Collection<Restaurant> restaurants;
        try {
            restaurants = objectMapper.readValue(restaurantDataFile, new TypeReference<HashSet<Restaurant>>() {});
        } catch (Exception e) {
            e.printStackTrace();
            restaurants = new HashSet<>();
        }
        return restaurants;
    }
}

