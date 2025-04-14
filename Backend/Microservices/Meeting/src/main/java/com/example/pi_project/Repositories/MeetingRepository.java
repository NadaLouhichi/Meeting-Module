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

    @Query("SELECT m FROM Meeting m WHERE LOWER(REPLACE(m.location, ' ', '')) = LOWER(REPLACE(:location, ' ', ''))")
    List<Meeting> findByLocationIgnoreCaseAndSpaces(@Param("location") String location);

    @Query("SELECT m FROM Meeting m WHERE LOWER(REPLACE(m.title, ' ', '')) = LOWER(REPLACE(:title, ' ', '')) AND DATE(m.date) = :date")
    Optional<Meeting> findByTitleAndDate(@Param("title") String title, @Param("date") LocalDate date);

    @Query("SELECT m FROM Meeting m WHERE LOWER(REPLACE(m.title, ' ', '')) = LOWER(REPLACE(:title, ' ', ''))")
    List<Meeting> findByTitle(String title);
    List<Meeting> findAllByDateBetween(LocalDateTime start, LocalDateTime end);
    List<Meeting> findTop5ByOrderByDateDesc();

}