package com.carportal.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // auto-increment id
    private Long id;

    @Column(name = "username", nullable = false, length = 250)
    private String username;

    @Column(name = "email_id", nullable = false, unique = true, length = 150)
    private String emailId;

    @Column(name = "mobile", nullable = false, unique = true, length = 10)
    private String mobile;

    @Column(name = "password", nullable = false, unique = true)
    private String password;
    @Column(name = "role", nullable = false)
    private String role;

}