package com.microservices.event_service.repository;

import com.microservices.event_service.entity.Event;
import com.microservices.event_service.entity.EventRegisteredUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IEventRegisteredUsersRepository extends JpaRepository<EventRegisteredUsers, Long> {


    @Query("SELECT o from EventRegisteredUsers o where o.eventId=?1 and o.userId=?2")
    Optional<EventRegisteredUsers> findByEventIdAndUserId(Long eventId, Long userId);

    @Query("SELECT o from EventRegisteredUsers o where o.eventId=?1 ")
    List<EventRegisteredUsers> findByEventId(Long eventId);

    @Query("SELECT o.userId from EventRegisteredUsers o where o.eventId=?1 ")
    List<Long> findUserIdsByEventId(Long eventId);
}
