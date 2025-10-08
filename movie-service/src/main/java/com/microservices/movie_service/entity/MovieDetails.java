package com.microservices.movie_service.entity;

import com.microservices.movie_service.model.request.CastAndCrew;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "movies")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "title")
    public String title;

    @Column(name = "shortDescription")
    public String shortDescription;

    @Column(name = "description")
    public String description;

   /* @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)*/
    @Column(name = "castAndCrews")
    private String castAndCrews;

    @Column(name = "slots")
    private Long slots;
    // One Movie can have many CastAndCrew members
   /* @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Theaters> theaters = new ArrayList<>();*/


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
