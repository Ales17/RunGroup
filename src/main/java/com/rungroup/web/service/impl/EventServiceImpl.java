package com.rungroup.web.service.impl;

import com.rungroup.web.dto.EventDto;
import com.rungroup.web.models.Club;
import com.rungroup.web.models.Event;
import com.rungroup.web.models.EventType;
import com.rungroup.web.repository.ClubRepository;
import com.rungroup.web.repository.EventRepository;
import com.rungroup.web.repository.EventTypeRepository;
import com.rungroup.web.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.rungroup.web.mapper.EventMapper.mapToEvent;
import static com.rungroup.web.mapper.EventMapper.mapToEventDto;

@Service
public class EventServiceImpl implements EventService {
    private final EventTypeRepository eventTypeRepository;
    EventRepository eventRepository;
    EventTypeRepository typeRepository;
    ClubRepository clubRepository;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository, ClubRepository clubRepository, EventTypeRepository typeRepository, EventTypeRepository eventTypeRepository) {
        this.eventRepository = eventRepository;
        this.clubRepository = clubRepository;
        this.typeRepository = typeRepository;
        this.eventTypeRepository = eventTypeRepository;
    }

    @Override
    public void createEvent(Long clubId, EventDto eventDto, Long type) {
        Event e = mapToEvent(eventDto);
        Club club = clubRepository.findById(clubId).get();
        e.setClub(club);
        EventType t = eventTypeRepository.findById(type).get();
        e.setType(t);
        eventRepository.save(e);
    }

    @Override
    public void deleteEvent(Long eventId) {
        eventRepository.deleteById(eventId);
    }

    @Override
    public void updateEvent(EventDto eventDto, Long type) {
        Event event = mapToEvent(eventDto);
        EventType eventType = eventTypeRepository.findById(type).get();
        event.setType(eventType);
        eventRepository.save(event);
    }

    @Override
    public EventDto findByEventId(Long eventId) {
        Event event = eventRepository.findById(eventId).get();
        return mapToEventDto(event);
    }

    @Override
    public List<EventDto> findAllEvents() {
        List<Event> events = eventRepository.findAll();
        return events.stream().map((e) -> mapToEventDto(e)).collect(Collectors.toList());
    }

    @Override
    public List<EventType> findAllEventTypes() {
        return typeRepository.findAll();
    }
}
