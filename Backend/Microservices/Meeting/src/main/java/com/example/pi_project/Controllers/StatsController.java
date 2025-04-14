package com.example.pi_project.Controllers;

import com.example.pi_project.Entities.Meeting;
import com.example.pi_project.Services.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/stats")
@CrossOrigin(origins = "http://localhost:4200")
public class StatsController {

    @Autowired
    private StatsService statsService;

    @GetMapping("/full-report")
    public ResponseEntity<Map<String, Object>> getFullReport() {
        return ResponseEntity.ok(statsService.getFullStatisticsReport());
    }
/// To redo or verify
    @GetMapping("/meetings-per-month")
    public ResponseEntity<Map<String, Long>> getMeetingsPerMonth() {
        return ResponseEntity.ok(statsService.getMeetingsPerMonth());
    }

    @GetMapping("/meeting-types")
    public ResponseEntity<Map<Meeting.MeetingType, Long>> getMeetingTypes() {
        return ResponseEntity.ok(statsService.getMeetingsByType());
    }

    @GetMapping("/attendance-stats")
    public ResponseEntity<Map<String, Long>> getAttendanceStats() {
        return ResponseEntity.ok(statsService.getAttendanceStats());
    }
/// weird result
    @GetMapping("/meeting-effectiveness/{meetingId}")
    public ResponseEntity<Map<String, Object>> getMeetingEffectiveness(@PathVariable Long meetingId) {
        return ResponseEntity.ok(statsService.getMeetingEffectiveness(meetingId));
    }

    @GetMapping("/busiest-hours")
    public ResponseEntity<Map<String, Long>> getBusiestHours() {
        return ResponseEntity.ok(statsService.getBusiestHours());
    }
// a little weird
    @GetMapping("/participant-engagement")
    public ResponseEntity<Map<String, Map<String, Object>>> getParticipantEngagement() {
        return ResponseEntity.ok(statsService.getParticipantEngagement());
    }

    @GetMapping("/extended-stats")
    public ResponseEntity<Map<String, Object>> getExtendedStatistics() {
        return ResponseEntity.ok(statsService.getExtendedStatistics());
    }
}