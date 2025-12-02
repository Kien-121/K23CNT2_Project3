package com.giadungmart.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String fullName;

    private String username;

    @Column(unique = true)
    private String email;

    private String password;

    private String phone;

    private String address;

    private String role; // ADMIN / USER
}
