package server.controller;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.persistence.FileManagement;

/**
 * Controller for managing api calls regarding user data file.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {
    final Path path = Paths.get("../server/src/main/resources/users.json");
    final File usersDataFile = path.toFile();

    /**
     * api call for getting all saved users.

     * @return all saved users.
     */
    @GetMapping
    public Collection<User> getAllUsers() {
        return FileManagement.loadUsers(usersDataFile);
    }

    /**
     * api call to add one user to the saved users.

     * @param user user to be saved.
     */
    @PostMapping
    public void saveUser(@RequestBody User user) {
        FileManagement.saveUser(usersDataFile, user);
    }
}
