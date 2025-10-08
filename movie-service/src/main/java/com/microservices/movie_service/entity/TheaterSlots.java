package com.microservices.movie_service.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.List;

@Data
@Entity
@Table(name = "theater_slots")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TheaterSlots {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalTime time;
    private Long slots;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private List<String> slotIds;

    // Many CastAndCrew can belong to one Movie
    @ManyToOne
    @JoinColumn(name = "theater_id")
    private Theaters theaterDetails;
}
