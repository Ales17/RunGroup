package com.rungroup.web.service;

import com.rungroup.web.dto.EventDto;

import java.util.List;

public interface EventService {
    void createEvent(Long clubId, EventDto eventDto);

    void deleteEvent(Long eventId);
    void updateEvent(EventDto eventDto);
    EventDto findByEventId(Long eventId);
    List<EventDto> findAllEvents();
}
