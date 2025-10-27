package com.microservices.movie_service.repository;

import com.microservices.movie_service.entity.MovieDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMovieDetailsRepository extends JpaRepository<MovieDetails,Long> {
}
