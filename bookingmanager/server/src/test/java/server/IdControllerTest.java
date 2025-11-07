package server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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

import io.micrometer.core.instrument.Meter.Id;
// import model.Resta;
import server.controller.IdController;
import server.controller.RestaurantController;

public class IdControllerTest {
    private final static Path ORIGINAL = Paths.get("../server/src/main/resources/ids.json");
    private static Path tempBackupFile;
    IdController controller = new IdController();

    @BeforeAll
    private static void createTempBackupFile() throws IOException{
        try {
            tempBackupFile = Files.createTempFile("idsBackup", ".json");
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
    void saveIdTest(){
        assertEquals(controller.updateId("userId"), 2);
        
    }







    
}
