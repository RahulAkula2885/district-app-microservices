package com.microservices.user_service.entity;


import com.microservices.user_service.model.enums.RoleType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "userName", nullable = false)
    private String userName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "about", columnDefinition = "TEXT")
    private String about;

    @Column(name = "countryCode")
    private int countryCode;

    @Column(name = "mobileNumber")
    private String mobileNumber;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<RoleType> roles = new HashSet<>();

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "deleted")
    private boolean deleted;

    @Column(name = "createdTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Instant createdTime;

    @Column(name = "modifiedTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Instant modifiedTime;

}
