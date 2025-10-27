package com.microservices.movie_service.service.validations;

import com.microservices.movie_service.commons.exceptions.CustomException;
import com.microservices.movie_service.model.request.MovieRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class MovieValidations {

    public void checkValidationsForCreateMovies(MovieRequest request) {

        if (!StringUtils.hasText(request.getTitle())) {
            throw new CustomException("Movie title must not be null or empty");
        }
        if (!StringUtils.hasText(request.getShortDescription())) {
            throw new CustomException("Short Description must not be null or empty");
        }
        if (!StringUtils.hasText(request.getDescription())) {
            throw new CustomException("Description must not be null or empty");
        }
        if (request.getSlots() == null || request.getSlots() == 0) {
            throw new CustomException("Slots must not be null or 0");
        }
        if (request.getCastAndCrews() != null && !request.getCastAndCrews().isEmpty()) {
            request.getCastAndCrews().forEach(a -> {
                if (!StringUtils.hasText(a.getName())) {
                    throw new CustomException("Cast name must not be null or empty");
                }
            });
        }
    }
}
