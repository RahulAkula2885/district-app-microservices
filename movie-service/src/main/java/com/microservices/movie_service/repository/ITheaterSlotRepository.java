package com.microservices.movie_service.repository;

import com.microservices.movie_service.entity.TheaterSlots;
import com.microservices.movie_service.entity.Theaters;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITheaterSlotRepository extends JpaRepository<TheaterSlots,Long> {
}
