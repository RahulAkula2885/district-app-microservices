package com.microservices.event_service.repository;

import com.microservices.event_service.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IEventRepository extends JpaRepository<Event, Long> {

    @Query("SELECT o from Event o where o.deleted = false order by o.createdTime desc")
    List<Event> findAllByDeletedFalse();
}
