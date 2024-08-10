package com.rungroup.web.dto;

import com.rungroup.web.models.Club;
import com.rungroup.web.models.EventType;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Builder
@Getter
@Setter
public class EventDto {
    private Long id;
    @NotEmpty(message = "Name cannot be empty")
    private String name;
    @NotNull(message = "Start time cannot be empty")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @FutureOrPresent
    private LocalDateTime startTime;
    @NotNull(message = "End time cannot be empty")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @FutureOrPresent(message = "Date must be today or later.")
    private LocalDateTime endTime;
    private EventType type;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private Club club;
}
