package com.rungroup.web.mapper;

import com.rungroup.web.dto.EventDto;
import com.rungroup.web.models.Event;

public class EventMapper {
    public static Event mapToEvent(EventDto dto) {
        return Event.builder()
                .id(dto.getId())
                .name(dto.getName())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .createdOn(dto.getCreatedOn())
                .type(dto.getType())
                .updatedOn(dto.getUpdatedOn())
                .club(dto.getClub())
                .build();
    }

    public static EventDto mapToEventDto(Event e) {
        return EventDto.builder()
                .id(e.getId())
                .name(e.getName())
                .startTime(e.getStartTime())
                .endTime(e.getEndTime())
                .createdOn(e.getCreatedOn())
                .type(e.getType())
                .updatedOn(e.getUpdatedOn())
                .club(e.getClub())
                .build();
    }
}
