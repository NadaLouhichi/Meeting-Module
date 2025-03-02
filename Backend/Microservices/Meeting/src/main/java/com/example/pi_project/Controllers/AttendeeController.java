package com.example.pi_project.Controllers;

import com.example.pi_project.Entities.*;
import com.example.pi_project.Services.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/attendees")
@CrossOrigin(origins = "http://localhost:4200")
public class AttendeeController {
    @Autowired
    private AttendeeService attendeeService;

    @PostMapping("/{meetingTitle}")
    public ResponseEntity<Attendee> addAttendee(
            @PathVariable String meetingTitle, // Pass meetingTitle as a path variable
            @Valid @RequestBody Attendee attendee) { // Pass attendee as the request body
        Attendee savedAttendee = attendeeService.addAttendee(meetingTitle, attendee);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAttendee);
    }

    @GetMapping("/meeting/{meetingTitle}")
    public ResponseEntity<List<Attendee>> getAttendeesByMeetingTitle(
            @PathVariable String meetingTitle) {
        List<Attendee> attendees = attendeeService.getAttendeesByMeetingTitle(meetingTitle);
        return ResponseEntity.ok(attendees);
    }

    @DeleteMapping("/name/{name}")
    public ResponseEntity<Void> deleteAttendeeByName(@PathVariable String name) {
        attendeeService.deleteAttendeeByName(name);
        return ResponseEntity.noContent().build(); // Retourne un statut 204 No Content
    }

    @PutMapping("/name/{name}")
    public ResponseEntity<Attendee> updateAttendeeByName(
            @PathVariable String name,
            @Valid @RequestBody Attendee updatedAttendee) {
        Attendee attendee = attendeeService.updateAttendeeByName(name, updatedAttendee);
        return ResponseEntity.ok(attendee); // Retourne un statut 200 OK avec l'attendee mis à jour
    }
}
