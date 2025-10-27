package com.microservices.event_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "event")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "eventName", nullable = false)
    private String eventName;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "fromTime")
    private LocalTime fromTime;

    @Column(name = "toTime")
    private LocalTime toTime;

    @Column(name = "url")
    private String url;

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
