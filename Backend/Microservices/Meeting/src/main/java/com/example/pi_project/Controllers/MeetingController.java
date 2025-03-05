package com.example.pi_project.Controllers;
import com.example.pi_project.Entities.*;
import com.example.pi_project.Exceptions.ResourceNotFoundException;
import com.example.pi_project.Services.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.*;
import org.springframework.web.bind.annotation.*;
import com.example.pi_project.Repositories.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.*;
import java.util.List;
import com.itextpdf.text.*;
import java.io.*;
import org.springframework.http.*;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/meetings")
public class MeetingController {

    private final MeetingService meetingService;
    private final ExportService exportService;

    @Autowired
    public MeetingController(MeetingService meetingService, ExportService exportService) {
        this.meetingService = meetingService;
        this.exportService = exportService;
    }

    @PostMapping
    public ResponseEntity<Meeting> createMeeting( @Valid @RequestBody Meeting meeting) {
        return ResponseEntity.status(HttpStatus.CREATED).body(meetingService.createMeeting(meeting));
    }



    @GetMapping
    public ResponseEntity<List<Meeting>> getAllMeetings() {
        return ResponseEntity.ok(meetingService.getAllMeetings());
    }

    /*
        @GetMapping("/date")
        public ResponseEntity<List<Meeting>> getMeetingsByDate(@RequestParam("date") String dateStr) {
            // Define the date format
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            // Parse the date string into a LocalDate
            LocalDate date = LocalDate.parse(dateStr, formatter);

            // Call the service method to get the meetings
            List<Meeting> meetings = meetingService.getMeetingsByDate(date);

            return ResponseEntity.ok(meetings);
        }

        @GetMapping("/location")
        public ResponseEntity<List<Meeting>> getMeetingsByLocation(@RequestParam String location) {
            List<Meeting> meetings = meetingService.getMeetingsByLocation(location);

            if (meetings.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No content
            }

            return new ResponseEntity<>(meetings, HttpStatus.OK); // 200 OK with meetings data
        }

        @PutMapping("/byTitleAndDate")
        public ResponseEntity<Meeting> updateMeetingByTitleAndDate(
                @RequestParam String title,
                @RequestParam String date, // Accept date as a string in "dd-MM-yyyy" format
                @Valid
                @RequestBody Meeting meetingDetails) {
            try {
                // Parse the date string into LocalDate
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate localDate = LocalDate.parse(date, dateFormatter);

                // Call the service method
                Meeting updatedMeeting = meetingService.updateMeetingByTitleAndDate(title, localDate, meetingDetails);
                return ResponseEntity.ok(updatedMeeting);
            } catch (DateTimeParseException e) {
                return ResponseEntity.badRequest().build(); // 400 Bad Request for invalid date format
            } catch (ResourceNotFoundException e) {
                return ResponseEntity.notFound().build(); // 404 Not Found if meeting doesn't exist
            }
        }

        @DeleteMapping("/byTitleAndDate")
        public ResponseEntity<Void> deleteMeetingByTitleAndDate(
                @RequestParam String title,
                @RequestParam String date) { // Accept date as a string in "dd-MM-yyyy" format
            try {
                // Parse the date string into LocalDate
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate localDate = LocalDate.parse(date, dateFormatter);

                // Call the service method
                meetingService.deleteMeetingByTitleAndDate(title, localDate);
                return ResponseEntity.noContent().build(); // 204 No Content
            } catch (DateTimeParseException e) {
                return ResponseEntity.badRequest().build(); // 400 Bad Request for invalid date format
            } catch (ResourceNotFoundException e) {
                return ResponseEntity.notFound().build(); // 404 Not Found if meeting doesn't exist
            }
        } */
    @PutMapping("/{id}")
    public ResponseEntity<Meeting> updateMeetingById(
            @PathVariable Long id,
            @Valid @RequestBody Meeting meetingDetails) {
        try {
            Meeting updatedMeeting = meetingService.updateMeetingById(id, meetingDetails);
            return ResponseEntity.ok(updatedMeeting);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMeetingById(@PathVariable Long id) {
        try {
            meetingService.deleteMeetingById(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    // Export to Excel
    @GetMapping("/export/excel")
    public ResponseEntity<byte[]> exportToExcel() throws IOException {
        ByteArrayOutputStream outputStream = exportService.exportMeetingsToExcel();
        byte[] bytes = outputStream.toByteArray();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "meetings.xlsx");

        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }

    // Export to PDF
    @GetMapping("/export/pdf")
    public ResponseEntity<byte[]> exportToPdf() throws IOException, DocumentException {
        ByteArrayOutputStream outputStream = exportService.exportMeetingsToPdf();
        byte[] bytes = outputStream.toByteArray();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "meetings.pdf");

        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }
}
