package com.rungroup.web.dto;

import com.rungroup.web.models.UserEntity;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
public class PostDto {
    private Long id;
    private String content;
    private UserEntity createdBy;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private Set<UserEntity> likedBy;
}
