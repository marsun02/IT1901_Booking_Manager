package server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collection;
import java.util.HashSet;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import model.Restaurant;
import server.controller.RestaurantController;
import server.persistence.FileManagement;

import model.User;
import server.controller.UserController;

public class UserControllerTest {
    private final static Path ORIGINAL = Paths.get("../server/src/main/resources/users.json");
    private static Path tempBackupFile;
    UserController controller = new UserController();

    @BeforeAll
    private static void createTempBackupFile() throws IOException{
        try {
            tempBackupFile = Files.createTempFile("UsersBackup", ".json");
            Files.copy(ORIGINAL, tempBackupFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println("Could not create backup!");
            throw new IOException("Aborting tests");
        }
    }

    @AfterAll
    private static void restoreOriginalFile() throws IOException{
        if (Files.exists(tempBackupFile)){
            Files.copy(tempBackupFile, ORIGINAL, StandardCopyOption.REPLACE_EXISTING);
            Files.deleteIfExists(tempBackupFile);
        } else{
            System.out.println("Error restoring original file - could not find backup");
        }
    }


    @Test
    void SaveAndGetUserTest(){
        Collection<User> usersOrginal = controller.getAllUsers();
        User user = new User(10, "tester", "test@gmail.com", "10101010", "123");
        controller.saveUser(user);
        Collection<User> userAddedToUsers = controller.getAllUsers();
        assertEquals(usersOrginal.size() + 1, userAddedToUsers.size());
    }





    
}
