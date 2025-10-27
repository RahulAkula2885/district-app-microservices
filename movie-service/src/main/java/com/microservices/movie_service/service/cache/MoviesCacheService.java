package com.microservices.movie_service.service.cache;

import com.microservices.movie_service.entity.MovieDetails;
import com.microservices.movie_service.entity.Theaters;
import com.microservices.movie_service.repository.IMovieDetailsRepository;
import com.microservices.movie_service.repository.ITheatersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MoviesCacheService {

    private final IMovieDetailsRepository iMovieDetailsRepository;
    private final ITheatersRepository iTheatersRepository;

    @CacheEvict(value = "#movies", allEntries = true)
    public void saveMovie(MovieDetails movieDetails) {
        iMovieDetailsRepository.save(movieDetails);
    }

    public Theaters createTheater(Theaters request) {
        return iTheatersRepository.save(request);
    }
}
