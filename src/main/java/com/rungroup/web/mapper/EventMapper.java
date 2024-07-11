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
                .type(dto.getType())
                .photoUrl(dto.getPhotoUrl())
                .createdOn(dto.getCreatedOn())
                .updatedOn(dto.getUpdatedOn())
                .build();
    }

    public static EventDto mapToEventDto(Event e) {
        return EventDto.builder()
                .id(e.getId())
                .name(e.getName())
                .startTime(e.getStartTime())
                .endTime(e.getEndTime())
                .type(e.getType())
                .photoUrl(e.getPhotoUrl())
                .createdOn(e.getCreatedOn())
                .updatedOn(e.getUpdatedOn())
                .club(e.getClub())
                .build();
    }
}
