package com.example.pi_project.Repositories;
import com.example.pi_project.Entities.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    @Query("SELECT m FROM Meeting m WHERE DATE(m.date) = :date")
    List<Meeting> findByDate(@Param("date") LocalDate date);
    List<Meeting> findByLocationIgnoreCase(String location);
    Optional<Meeting> findByTitleAndDate(String title, LocalDateTime date);

  List<Meeting> findByTitle(String meetingTitle);
}
