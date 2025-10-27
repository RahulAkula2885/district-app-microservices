package com.microservices.event_service.service.cache;

import com.microservices.event_service.entity.Event;
import com.microservices.event_service.entity.EventRegisteredUsers;
import com.microservices.event_service.repository.IEventRegisteredUsersRepository;
import com.microservices.event_service.repository.IEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventCache {

    private final IEventRepository iEventRepository;
    private final IEventRegisteredUsersRepository iEventRegisteredUsersRepository;

    @CacheEvict(value = "#event", allEntries = true)
    public void saveEvent(Event event) {
        iEventRepository.save(event);
    }

    public void registerForEvent(EventRegisteredUsers eventRegisteredUsers) {
        iEventRegisteredUsersRepository.save(eventRegisteredUsers);
    }
}
