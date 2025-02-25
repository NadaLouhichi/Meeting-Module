package com.example.pi_project.Controllers;
import com.example.pi_project.Entities.*;
import com.example.pi_project.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.pi_project.Repositories.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
@RestController
@RequestMapping("/meetings")
public class MeetingController {

    private final MeetingRepository meetingRepository;
    @Autowired
    private MeetingService meetingService;

    public MeetingController(MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    @PostMapping
    public ResponseEntity<Meeting> createMeeting(@RequestBody Meeting meeting) {
        return ResponseEntity.status(HttpStatus.CREATED).body(meetingService.createMeeting(meeting));
    }



    @GetMapping
    public ResponseEntity<List<Meeting>> getAllMeetings() {
        return ResponseEntity.ok(meetingService.getAllMeetings());
    }


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
            @RequestBody Meeting meetingDetails) {
        // Parse the date string into LocalDateTime
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(date, dateFormatter);
        LocalDateTime localDateTime = localDate.atStartOfDay(); // Convert to LocalDateTime at start of day

        // Call the service method
        Meeting updatedMeeting = meetingService.updateMeetingByTitleAndDate(title, localDateTime, meetingDetails);
        return ResponseEntity.ok(updatedMeeting);
    }

    @DeleteMapping("/byTitleAndDate")
    public ResponseEntity<Void> deleteMeetingByTitleAndDate(
            @RequestParam String title,
            @RequestParam String date) { // Accept date as a string in "dd-MM-yyyy" format
        // Parse the date string into LocalDateTime
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(date, dateFormatter);
        LocalDateTime localDateTime = localDate.atStartOfDay(); // Convert to LocalDateTime at start of day

        // Call the service method
        meetingService.deleteMeetingByTitleAndDate(title, localDateTime);
        return ResponseEntity.noContent().build();
    }
}
