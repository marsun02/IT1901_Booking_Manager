package server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Class with the sole purpose of running the server app.
 */
@SpringBootApplication
public class ServerApp {
    
    /**
     * Method starting server app.

     * @param args parameter needed in java main methods.
     */
    public static void main(String[] args) {
        SpringApplication.run(ServerApp.class, args);
    }
}