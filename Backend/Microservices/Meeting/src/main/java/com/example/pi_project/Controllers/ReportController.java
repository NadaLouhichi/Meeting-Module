package com.example.pi_project.Controllers;
import com.example.pi_project.Entities.*;
import com.example.pi_project.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/reports")
public class ReportController {
    @Autowired
    private ReportService reportService;

    @PostMapping
    public ResponseEntity<Report> createReport(@RequestBody Report report) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reportService.createReport(report));
    }

    @GetMapping("/meeting/{meetingId}")
    public ResponseEntity<List<Report>> getReportsByMeeting(@PathVariable Long meetingId) {
        return ResponseEntity.ok(reportService.getReportsByMeeting(meetingId));
    }
}
