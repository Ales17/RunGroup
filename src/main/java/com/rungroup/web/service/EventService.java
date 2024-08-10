package com.rungroup.web.service;

import com.rungroup.web.dto.EventDto;
import com.rungroup.web.models.EventType;

import java.util.List;

public interface EventService {
    void createEvent(Long clubId, EventDto eventDto, Long type);

    void deleteEvent(Long eventId);

    void updateEvent(EventDto eventDto, Long type);

    EventDto findByEventId(Long eventId);

    List<EventDto> findAllEvents();

    List<EventType> findAllEventTypes();
}
