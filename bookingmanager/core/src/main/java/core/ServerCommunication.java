package core;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collection;
import java.util.HashSet;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import model.Restaurant;

public class ServerCommunication {
    ObjectMapper objectMapper = new ObjectMapper();

    public Collection<Restaurant> getAllRestaurants() throws IOException, InterruptedException{
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/api/restaurants")).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        
        Collection<Restaurant> restaurants = objectMapper.readValue(response.body(), new TypeReference<HashSet<Restaurant>>(){});

        return restaurants;
    }
}
