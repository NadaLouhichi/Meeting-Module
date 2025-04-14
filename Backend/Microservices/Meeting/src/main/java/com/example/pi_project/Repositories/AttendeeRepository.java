package com.example.pi_project.Repositories;
import com.example.pi_project.Entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface AttendeeRepository extends JpaRepository<Attendee, Long> {
    List<Attendee> findByMeetingId(Long meetingId);

    @Query("SELECT a FROM Attendee a WHERE LOWER(REPLACE(a.name, ' ', '')) = LOWER(REPLACE(:name, ' ', ''))")
    Optional<Attendee> findByName(String name);

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM Attendee a WHERE LOWER(REPLACE(a.name, ' ', '')) = LOWER(REPLACE(:name, ' ', ''))")
    boolean existsByName(String name);

    @Modifying
    @Query("DELETE FROM Attendee a WHERE LOWER(a.name) = LOWER(:name)")
    void deleteByName(String name);

    Optional<Attendee> findByNameIgnoreCase(String name);
}