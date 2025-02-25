package com.example.pi_project.Entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Meeting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private LocalDateTime date;
    private String location;
    private String description;

    @OneToMany(mappedBy = "meeting", cascade = CascadeType.ALL)
    private List<Attendee> attendees;

    @OneToMany(mappedBy = "meeting", cascade = CascadeType.ALL)
    private List<Report> reports;

    @OneToMany(mappedBy = "meeting", cascade = CascadeType.ALL)
    private List<Agenda> agendas;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description; // This should return description, not location
    }

    public void setDescription(String description) {
        this.description = description; // This should set description, not location
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;

    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public Long getId() {
        return id; // Ensure this method exists
    }

    public void setId(Long id) {
        this.id = id;
    }
}


