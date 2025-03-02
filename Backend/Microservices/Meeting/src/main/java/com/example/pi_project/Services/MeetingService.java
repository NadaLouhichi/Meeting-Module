package com.example.pi_project.Services;
import com.example.pi_project.Entities.*;
import com.example.pi_project.Exceptions.ResourceNotFoundException;
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
        // Normalize the location parameter
        String normalizedLocation = normalizeLocation(location);

        // Query the repository with the normalized location
        return meetingRepository.findByLocationIgnoreCaseAndSpaces(normalizedLocation);
    }

    private String normalizeLocation(String location) {
        // Convert to lowercase and remove spaces
        return location.toLowerCase().replaceAll("\\s", "");
    }
    public List<Meeting> getMeetingsByDate(LocalDate date) {
        // Retrieve all meetings with the given date (ignores time part)
        return meetingRepository.findByDate(date);
    }



    public Meeting updateMeetingByTitleAndDate(String title, LocalDate date, Meeting meetingDetails) {
        Meeting meeting = meetingRepository.findByTitleAndDate(title, date)
                .orElseThrow(() -> new ResourceNotFoundException("Meeting not found with title: " + title + " and date: " + date));

        // Update the meeting details
        meeting.setTitle(meetingDetails.getTitle());
        meeting.setDate(meetingDetails.getDate());
        meeting.setLocation(meetingDetails.getLocation());
        meeting.setDescription(meetingDetails.getDescription());

        return meetingRepository.save(meeting);
    }

    public void deleteMeetingByTitleAndDate(String title, LocalDate date) {
        Meeting meeting = meetingRepository.findByTitleAndDate(title, date)
                .orElseThrow(() -> new ResourceNotFoundException("Meeting not found with title: " + title + " and date: " + date));
        meetingRepository.delete(meeting);
    }
    public Meeting updateMeetingById(Long id, Meeting meetingDetails) {
        Meeting meeting = meetingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Meeting not found with id: " + id));

        // Update the meeting details
        meeting.setTitle(meetingDetails.getTitle());
        meeting.setDate(meetingDetails.getDate());
        meeting.setLocation(meetingDetails.getLocation());
        meeting.setDescription(meetingDetails.getDescription());

        return meetingRepository.save(meeting);
    }

    public void deleteMeetingById(Long id) {
        Meeting meeting = meetingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Meeting not found with id: " + id));
        meetingRepository.delete(meeting);
    }
}