package core;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collection;
import java.util.HashSet;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import model.Restaurant;
import model.User;

public class ServerCommunication {
    ObjectMapper objectMapper = new ObjectMapper();
    HttpClient client = HttpClient.newHttpClient();

    public Collection<Restaurant> getAllRestaurants(){
        try {
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/api/restaurants")).GET().build();
    
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            
            Collection<Restaurant> restaurants = objectMapper.readValue(response.body(), new TypeReference<HashSet<Restaurant>>(){});
    
            return restaurants;
        } catch (Exception e) {
            // TODO: Make proper catch
            e.printStackTrace();
            return null;
        }
    }

    public void saveAllRestaurants(Collection<Restaurant> restaurants){
        try {
            String jsonContents = objectMapper.writeValueAsString(restaurants);

            HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/api/restaurants")).header("content-type", "application/json").POST(HttpRequest.BodyPublishers.ofString(jsonContents)).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200){
                System.out.println("All okay!");
            } else{
                System.out.println("Posting failed!");
            }
        } catch (Exception e) {
            // TODO: Make proper catch
            e.printStackTrace();
        }
    }

    // public int getId(String idType){
    //     try {
            
    //     } catch (Exception e) {
    //         // TODO: handle exception
    //     }
    // }

    public Collection<User> getUsers(){
        try {
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/api/users")).GET().build();
    
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            
            Collection<User> users = objectMapper.readValue(response.body(), new TypeReference<HashSet<User>>(){});
    
            return users;
        } catch (Exception e) {
            // TODO: Make proper catch
            e.printStackTrace();
            return null;
        }
    }

    public void saveUser(User user){
        try {
            String jsonContents = objectMapper.writeValueAsString(user);

            HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/api/users")).header("content-type", "application/json").POST(HttpRequest.BodyPublishers.ofString(jsonContents)).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200){
                System.out.println("All okay!");
            } else{
                System.out.println("Posting failed!");
            }
        } catch (Exception e) {
            // TODO: Make proper catch
            e.printStackTrace();
        }
    }
}
