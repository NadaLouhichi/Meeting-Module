package com.example.pi_project.Entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
public class User {
    @Id @GeneratedValue
    private Long id;
    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    // Getters and setters
}


