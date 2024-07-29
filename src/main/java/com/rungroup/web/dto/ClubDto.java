package com.rungroup.web.dto;

import com.rungroup.web.models.UserEntity;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ClubDto {
    private Long id;
    //@NotEmpty(message = "Title cannot be empty")
    @Size(min = 3, max = 50, message = "Title size must be between 3 and 50.")
    private String title;
    //@NotEmpty(message = "Photo link cannot be empty")
    @Size(min = 3, max = 255, message = "Photo link size must be between 3 and 255.")
    private String photoUrl;
    //@NotEmpty(message = "Content cannot be empty")
    @Size(min = 3, max = 255, message = "Content size must be between 3 and 255.")
    private String content;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private UserEntity createdBy;
    private List<EventDto> events;
}
