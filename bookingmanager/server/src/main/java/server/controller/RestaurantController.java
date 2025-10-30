package server.controller;

import java.util.Collection;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import model.Restaurant;
import server.service.RestaurantService;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {
    private RestaurantService restaurantService;

    public RestaurantController(RestaurantService service) {
        this.restaurantService = service;
    }
    
    @GetMapping
    public Collection<Restaurant> getAllRestaurants(){
        return restaurantService.getAllRestaurants();
    }
}
