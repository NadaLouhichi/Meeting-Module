package com.example.pi_project.Services;
import com.example.pi_project.Entities.*;
import com.example.pi_project.Repositories.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MeetingService {
    @Autowired
    private MeetingRepository meetingRepository;

    public Meeting createMeeting(Meeting meeting) {
        return meetingRepository.save(meeting);
    }


    public List<Meeting> getAllMeetings() {
        return meetingRepository.findAll();
    }



    public List<Meeting> getMeetingsByLocation(String location) {
        return meetingRepository.findByLocationIgnoreCase(location);
    }
    public List<Meeting> getMeetingsByDate(LocalDate date) {
        // Retrieve all meetings with the given date (ignores time part)
        return meetingRepository.findByDate(date);
    }


    public Meeting updateMeetingByTitleAndDate(String title, LocalDateTime date, Meeting meetingDetails) {
        Meeting meeting = meetingRepository.findByTitleAndDate(title, date)
                .orElseThrow(() -> new RuntimeException("Meeting not found with title: " + title + " and date: " + date));

        // Update the meeting details
        meeting.setTitle(meetingDetails.getTitle());
        meeting.setDate(meetingDetails.getDate());
        meeting.setLocation(meetingDetails.getLocation());
        meeting.setDescription(meetingDetails.getDescription());

        return meetingRepository.save(meeting);
    }

    public void deleteMeetingByTitleAndDate(String title, LocalDateTime date) {
        Meeting meeting = meetingRepository.findByTitleAndDate(title, date)
                .orElseThrow(() -> new RuntimeException("Meeting not found with title: " + title + " and date: " + date));
        meetingRepository.delete(meeting);
    }

}