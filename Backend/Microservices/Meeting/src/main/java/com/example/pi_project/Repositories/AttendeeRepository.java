package com.example.pi_project.Repositories;
import com.example.pi_project.Entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface AttendeeRepository extends JpaRepository<Attendee, Long> {
    List<Attendee> findByMeetingId(Long meetingId);
}
