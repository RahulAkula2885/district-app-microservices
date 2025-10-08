package com.microservices.movie_service.repository;

import com.microservices.movie_service.entity.MovieDetails;
import com.microservices.movie_service.entity.Theaters;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITheatersRepository extends JpaRepository<Theaters,Long> {
}
