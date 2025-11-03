package server.controller;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import server.persistence.FileManagement;

@RestController
@RequestMapping("/api/ids")
public class IdController {
    final Path PATH = Paths.get("../server/src/main/resources/ids.json");
    final File IDS_DATA_FILE = PATH.toFile();

    @GetMapping
    public int getId(@RequestBody String idType){
        return FileManagement.getId(IDS_DATA_FILE, idType);
    }
}
