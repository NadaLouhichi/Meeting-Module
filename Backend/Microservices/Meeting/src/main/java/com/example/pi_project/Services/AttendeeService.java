package com.example.pi_project.Services;
import com.example.pi_project.Entities.*;
import com.example.pi_project.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


    @Service
    public class AttendeeService {

        private final AttendeeRepository attendeeRepository;
        private final MeetingRepository meetingRepository;

        public AttendeeService(AttendeeRepository attendeeRepository, MeetingRepository meetingRepository) {
            this.attendeeRepository = attendeeRepository;
            this.meetingRepository = meetingRepository;
        }

        public Attendee addAttendee(String meetingTitle, Attendee attendee) {
            // Log the meeting title being searched
            System.out.println("Searching for meeting with title: " + meetingTitle);

            // Find the meeting by title
            List<Meeting> meetings = meetingRepository.findByTitle(meetingTitle);

            // Log the results
            System.out.println("Found meetings: " + meetings);

            if (meetings.isEmpty()) {
                throw new RuntimeException("Meeting not found with title: " + meetingTitle);
            } else if (meetings.size() > 1) {
                throw new RuntimeException("Multiple meetings found with title: " + meetingTitle);
            }

            Meeting meeting = meetings.get(0); // Get the first meeting

            // Associate the attendee with the meeting
            attendee.setMeeting(meeting);

            // Save the attendee
            return attendeeRepository.save(attendee);
        }

        public List<Attendee> getAttendeesByMeetingTitle(String meetingTitle) {
            // Find the meeting by title
            List<Meeting> meetings = meetingRepository.findByTitle(meetingTitle);

            if (meetings.isEmpty()) {
                throw new RuntimeException("Meeting not found with title: " + meetingTitle);
            } else if (meetings.size() > 1) {
                throw new RuntimeException("Multiple meetings found with title: " + meetingTitle);
            }

            Meeting meeting = meetings.get(0); // Get the first meeting

            // Fetch attendees by meeting ID
            return attendeeRepository.findByMeetingId(meeting.getId());
        }
        // Méthode pour supprimer un participant par nom


        public Attendee updateAttendeeByName(String name, Attendee updatedAttendee) {
            Attendee existingAttendee = attendeeRepository.findByName(name)
                    .orElseThrow(() -> new RuntimeException("Attendee not found with name: " + name));

            // Mettre à jour les champs de l'attendee existant
            existingAttendee.setName(updatedAttendee.getName());
            existingAttendee.setEmail(updatedAttendee.getEmail());

            // Sauvegarder les modifications
            return attendeeRepository.save(existingAttendee);
        }

        public void deleteAttendeeByName(String name) {
            // Check if the attendee exists
            Attendee attendee = attendeeRepository.findByNameIgnoreCase(name)
                    .orElseThrow(() -> new RuntimeException("Attendee not found with name: " + name));

            // Delete the attendee
            attendeeRepository.delete(attendee);
        }
    }
