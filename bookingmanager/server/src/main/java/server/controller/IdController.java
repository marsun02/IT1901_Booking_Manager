package server.controller;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import server.persistence.FileManagement;

/**
 * Controller to manage the api call regarding id data file.
 */
@RestController
@RequestMapping("/api/ids")
public class IdController {
    final Path path = Paths.get("../server/src/main/resources/ids.json");
    final File idsDataFile = path.toFile();

    /**
     * Api call updating the given id.

     * @param idType string determining which id to be updated.
     * @return the updated id.
     */
    @GetMapping
    public int updateId(@RequestParam("idType") String idType) {
        return FileManagement.updateId(idsDataFile, idType);
    }
}
