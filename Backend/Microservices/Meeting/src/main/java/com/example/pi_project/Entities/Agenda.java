package com.example.pi_project.Entities;

import jakarta.persistence.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Agenda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String item;
    private String description;
    private LocalDateTime scheduledTime;

    @ManyToOne
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;

    // getters and setters
}
