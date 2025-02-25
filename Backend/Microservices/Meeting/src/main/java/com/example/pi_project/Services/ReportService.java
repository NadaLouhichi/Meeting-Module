package com.example.pi_project.Services;
import com.example.pi_project.Entities.*;
import com.example.pi_project.Repositories.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class ReportService {
    @Autowired
    private ReportRepository reportRepository;

    public Report createReport(Report report) {
        return reportRepository.save(report);
    }

    public List<Report> getReportsByMeeting(Long meetingId) {
        return reportRepository.findByMeetingId(meetingId);
    }
}
