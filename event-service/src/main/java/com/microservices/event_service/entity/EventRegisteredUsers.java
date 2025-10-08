package com.microservices.event_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Entity
@Table(name = "event_registered_users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventRegisteredUsers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "eventId")
    private Long eventId;

    @Column(name = "userId")
    private Long userId;

    @Column(name = "registered")
    private boolean registered;

    @Column(name = "createdTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Instant createdTime;

    @Column(name = "modifiedTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Instant modifiedTime;
}
