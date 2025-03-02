package com.example.pi_project.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
public class Attendee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Le nom ne peut pas être vide")
    @Size(max = 25, message = "Le nom ne peut pas dépasser 50 caractères")
    private String name;
    /*@NotBlank(message = "Le rôle ne peut pas être vide")
    @Size(max = 30, message = "Le rôle ne peut pas dépasser 50 caractères")
    private String role;*/
    @NotBlank(message = "L'email ne peut pas être vide")
    @Email(message = "L'email doit être valide")
    @Size(max = 40, message = "L'email ne peut pas dépasser 35 caractères")
    private String email;
    @ManyToOne


    @JoinColumn(name = "meeting_id")
    private Meeting meeting;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Meeting getMeeting() {
        return meeting;
    }

    public void setMeeting(Meeting meeting) {
        this.meeting = meeting;
    }
}
