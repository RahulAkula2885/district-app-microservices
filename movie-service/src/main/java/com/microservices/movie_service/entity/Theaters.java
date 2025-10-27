package com.microservices.movie_service.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "theaters")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Theaters {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    //@OneToMany(mappedBy = "theaterDetails", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @OneToMany(mappedBy = "theaterDetails",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<TheaterSlots> theaterSlots;

    @Column(name = "description")
    private String description;

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
