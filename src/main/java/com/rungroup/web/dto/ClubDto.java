package com.rungroup.web.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ClubDto {
    private Long id;
    @NotEmpty(message = "Title cannot be empty")
    private String title;
    @NotEmpty(message = "Photo link cannot be empty")
    private String photoUrl;
    @NotEmpty(message = "Content cannot be empty")
    private String content;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
}
