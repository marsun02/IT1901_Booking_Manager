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

@RestController
@RequestMapping("/api/users")
public class UserController {
    final Path PATH = Paths.get("../server/src/main/resources/users.json");
    final File USERS_DATA_FILE = PATH.toFile();

    @GetMapping
    public Collection<User> getAllUsers() {
        return FileManagement.loadUsers(USERS_DATA_FILE);
    }

    @PostMapping
    public void saveUser(@RequestBody User user) {
        FileManagement.saveUser(USERS_DATA_FILE, user);
    }

}
