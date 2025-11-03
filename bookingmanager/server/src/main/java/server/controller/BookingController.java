package server.controller;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    final Path PATH = Paths.get("../server/src/main/resources/bookings.json");
    final File BOOKING_DATA_FILE = PATH.toFile();
}
