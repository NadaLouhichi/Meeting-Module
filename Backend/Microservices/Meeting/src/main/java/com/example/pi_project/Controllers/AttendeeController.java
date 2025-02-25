package com.example.pi_project.Controllers;

import com.example.pi_project.Entities.*;
import com.example.pi_project.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/attendees")
public class AttendeeController {
    @Autowired
    private AttendeeService attendeeService;

    @PostMapping("/{meetingTitle}")
    public ResponseEntity<Attendee> addAttendee(
            @PathVariable String meetingTitle, // Pass meetingTitle as a path variable
            @RequestBody Attendee attendee) { // Pass attendee as the request body
        Attendee savedAttendee = attendeeService.addAttendee(meetingTitle, attendee);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAttendee);
    }

    @GetMapping("/meeting/{meetingTitle}")
    public ResponseEntity<List<Attendee>> getAttendeesByMeetingTitle(
            @PathVariable String meetingTitle) {
        List<Attendee> attendees = attendeeService.getAttendeesByMeetingTitle(meetingTitle);
        return ResponseEntity.ok(attendees);
    }
}
