package com.example.pi_project.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
    @NotBlank(message = "Le titre ne peut pas être vide")
    @Size(max = 35, message = "Le titre ne peut pas dépasser 100 caractères")
    @Column(unique = true)
    private String title;
    @NotNull(message = "La date ne peut pas être nulle")
    @FutureOrPresent(message = "La date doit être aujourd'hui ou dans le futur")
    private LocalDateTime date;
    @NotBlank(message = "L'emplacement ne peut pas être vide")
    @Size(max = 35, message = "L'emplacement ne peut pas dépasser 100 caractères")
    private String location;
    @Size(max = 65, message = "La description ne peut pas dépasser 500 caractères")
    private String description;

    @OneToMany(mappedBy = "meeting", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Attendee> attendees;

    @OneToMany(mappedBy = "meeting", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Report> reports;

    @OneToMany(mappedBy = "meeting", cascade = CascadeType.ALL)
    @JsonIgnore
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


